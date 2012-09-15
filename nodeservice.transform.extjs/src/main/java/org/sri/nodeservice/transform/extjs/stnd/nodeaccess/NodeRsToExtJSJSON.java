package org.sri.nodeservice.transform.extjs.stnd.nodeaccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodeaccess.service.model.NodeSet;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.BaseNodeRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.BaseNodeRs;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.ErrorDetail;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.ErrorTypeEnum;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.GetNodeRs;
import org.sri.nodeservice.transform.common.nodeaccess.OutNodeRsToStringRs;
import org.sri.nodeservice.transform.common.util.FlatMapTransformer;
import org.sri.nodeservice.transform.common.util.TransformContext;

import flexjson.JSONSerializer;

/**
 * 
 * @author sisaac
 * 
 */
public class NodeRsToExtJSJSON implements OutNodeRsToStringRs {

	@Override
	public String transform(TransformContext context, BaseNodeRs nodeRs) {

		Object rs = null;
		JSONSerializer s = null;
		
		BaseNodeRq rq = (BaseNodeRq) context.getObject("rq");

		// if the operation failed we need to customise the response.
		if (!nodeRs.isSuccess()) {
			ExtJSErrorRs extJsRs = new ExtJSErrorRs();
			extJsRs.setSuccess(false);
			for (ErrorDetail curDetail : nodeRs.getErrors().getErrorDetails()) {
				if(ErrorTypeEnum.VALIDATION.equals(curDetail.getType())){
					extJsRs.getErrors().put(curDetail.getFieldName(), curDetail.getDescription());
				}else {
					extJsRs.getErrors().put("otherError", curDetail.getDescription());
				}
			}
			rs = extJsRs;
			s = new JSONSerializer();
		}

		// if method successful check for further custom requirements.
		if (rs == null) {

			// if this was a load request then we need to customise the get
			// to the special load response required by the extjs
			if (rq.getMethod().equals("load") && nodeRs.isSuccess()) {
				GetNodeRs getRs = (GetNodeRs) nodeRs;
				ExtJSLoadNodeFormRs loadRs = new ExtJSLoadNodeFormRs();
				loadRs.setSuccess(true);
				loadRs.getData().put("id", getRs.getNode().getId());
				loadRs.getData().putAll(getRs.getNode().getFields());
				// loadRs.getData().put("description",
				// getRs.getNode().getDescription());
				
				//Map errors to display if any
				if(getRs.getErrors() != null && getRs.getErrors().getErrorDetails().size() > 0){
					for (ErrorDetail curDetail : nodeRs.getErrors().getErrorDetails()) {
						if(ErrorTypeEnum.VALIDATION.equals(curDetail.getType())){
							loadRs.getErrors().put(curDetail.getFieldName(), curDetail.getDescription());
						}else {
							loadRs.getErrors().put("otherError", curDetail.getDescription());
						}
					}
				}
				
				//Current version of Node Service only supports one level of child Node Sets
				if(getRs.getNode().getChildSets().size() > 0){
					Set<String> nodeSetIds = getRs.getNode().getChildSets().keySet();
					for (String nodeSetId : nodeSetIds) {
						NodeSet nodeSet = getRs.getNode().getChildSets().get(nodeSetId);
						Collection<Node> nodes = nodeSet.getNodes().values();
						
						List<Map<String, String>> childNodes = new ArrayList<Map<String,String>>();
						for (Node node : nodes) {
							Map<String, String> fields = new HashMap<String, String>();
							fields.put("id", node.getId());
							fields.putAll(node.getFields());
							childNodes.add(fields);
						}
						//String array = s.deepSerialize(sb);
						//loadRs.getData().put(nodeSetId, array);
						loadRs.getChildNodeSets().put(nodeSetId, childNodes);
					}
				}
				
				rs = loadRs;
				s = new JSONSerializer();
			}
		}
		
		// if no response customisation has been 
		// done then simply use the NodeRs
		if (rs == null) {
			rs = nodeRs;
		}
		
		// if there has been no requirement for customisation of 
		// the serialisation create a default instance
		if (s == null) {
			s = new JSONSerializer().transform(new FlatMapTransformer(),
					Map.class);
		}

		// now attempt the transform.
		try {
			String string = s.deepSerialize(rs);
			return string;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.sri.nodeservice.transform.common.nodeaccess.OutNodeRsToStringRs#transform(java.lang.Throwable)
	 */
	@Override
	public String transform(TransformContext context, String message, String code) {
		ExtJSErrorRs rs = new ExtJSErrorRs();
		rs.setSuccess(false);
		rs.getErrors().put("busServiceExpMsg", message);
		rs.getErrors().put("busServiceExpCode", code);
		try {
			JSONSerializer s = new JSONSerializer();
			String string = s.deepSerialize(rs);
			return string;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}