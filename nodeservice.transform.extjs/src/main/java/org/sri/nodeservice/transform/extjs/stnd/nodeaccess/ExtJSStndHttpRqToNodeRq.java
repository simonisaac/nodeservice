package org.sri.nodeservice.transform.extjs.stnd.nodeaccess;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.BaseNodeRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.CreateNodeRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.GetNodeRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.ListNodesRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.SearchFilter;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.TestRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.UpdateNodeRq;
import org.sri.nodeservice.transform.common.nodeaccess.InHttpRqToNodeRq;
import org.sri.nodeservice.transform.common.util.HttpRequestAdapter;
import org.sri.nodeservice.transform.common.util.QueryString;
import org.sri.nodeservice.transform.common.util.TransformContext;

/**
 * 
 * @author sisaac
 * 
 */
public class ExtJSStndHttpRqToNodeRq implements InHttpRqToNodeRq {

	public static String ID = "id";
	public static String TYPE = "nodeType";

	public ExtJSStndHttpRqToNodeRq() {}

	public BaseNodeRq transform(TransformContext context, HttpRequestAdapter httpRequest) {

		BaseNodeRq rq = null;

		if (httpRequest != null) {
			String method = httpRequest.getQueryString().getParameter("method");
			String sessionToken = httpRequest.getQueryString().getParameter("sessionToken");

			if ("testMethod".equals(method)) {
				rq = this.getTestRq(httpRequest);
			} else if ("retrieveNodes".equals(method)) {
				rq = this.getRetrieveNodesRq(httpRequest);
			} else if ("create".equals(method)) {
				rq = this.getCreateNodeRq(httpRequest);
			} else if ("update".equals(method)) {
				rq = this.getUpdateNodeRq(httpRequest);
			} else if ("load".equals(method)) {
				rq = this.getGetNodeRq(httpRequest);
			}else if ("get".equals(method)) {
				rq = this.getGetNodeRq(httpRequest);
			}

			rq.setMethod(method);
			rq.setSessionToken(sessionToken);

			context.setObject("rq", rq);
			return rq;
		}

		return null;
	}

	@Override
	public BaseNodeRq transform(TransformContext context, Node node) {
		BaseNodeRq rq = null;
		
		if(node != null){
			String method = node.getFields().get("method");
			String sessionToken = node.getFields().get("sessionToken");
			
			if ("create".equals(method)) {
				rq = getCreateNodeRq(node);
			} else if ("update".equals(method)) {
				//
			} else if ("load".equals(method)) {
				//
			}
			
			rq.setMethod(method);
			rq.setSessionToken(sessionToken);

			context.setObject("rq", rq);
			return rq;
		}
		
		return null;
	}
	
	private CreateNodeRq getCreateNodeRq(Node node){
		CreateNodeRq rq = new CreateNodeRq();
		rq.setNode(node);
		return rq;
	}


	private ListNodesRq getRetrieveNodesRq(HttpRequestAdapter request) {

		ListNodesRq result = new ListNodesRq();
		List<SearchFilter> filters = new ArrayList<SearchFilter>();

		result.setNodeType(request.getQueryString().getParameter("nodeType"));
		result.setStart(request.getQueryString()
				.getIntParameter("start", false));
		result.setPage(request.getQueryString()
				.getIntParameter("page", false));
		result.setLimit(request.getQueryString()
				.getIntParameter("limit", false));
		result.setSortField(request.getQueryString()
				.getParameter("sort", false));
		result.setSortDir(request.getQueryString().getParameter("dir", false));

		String filterString = request.getQueryString().getParameter(
				"searchString", false);
		
		if(filterString != null){
			try {
				filterString = URLDecoder.decode(filterString, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		
		if (filterString != null && !filterString.equals("")) {
			String[] pair = filterString.split(":");
			for (int i = 0; i < pair.length; i++) {
				String[] searchElements = pair[i].split("@");
				int opeartion = evaluateOperation(searchElements[1]);
				SearchFilter filter = new SearchFilter(searchElements[0],
						opeartion, searchElements[2]);
				filters.add(filter);
			}
		}
		result.setFilters(filters);

		return result;
	}

	private int evaluateOperation(String operation) {
		int value = SearchFilter.EQUAL;
		if ("EQUAL".equals(operation)) {
			value = SearchFilter.EQUAL;
		} else if ("NOT_EQUAL".equals(operation)) {
			value = SearchFilter.NOT_EQUAL;
		}
		// Extend this to add more operations.
		return value;
	}

	private CreateNodeRq getCreateNodeRq(HttpRequestAdapter request) {

		CreateNodeRq result = new CreateNodeRq();

		Node nodeToUpdate = this.getNode(request.getQueryString());
		result.setNode(nodeToUpdate);

		return result;
	}
	
	private UpdateNodeRq getUpdateNodeRq(HttpRequestAdapter request) {

		UpdateNodeRq result = new UpdateNodeRq();

		Node nodeToUpdate = this.getNode(request.getQueryString());
		result.setNode(nodeToUpdate);

		return result;
	}

	private Node getNode(QueryString qs) {

		Node rNode = new Node(qs.getParameter(ID), qs.getParameter(TYPE));

		// do the node attributes.
		for (Map.Entry<String, String[]> entry : qs.getParameterMap()
				.entrySet()) {
			if(ID.equals(entry.getKey()) || TYPE.equals(entry.getKey())){
				continue;
			}
			rNode.getFields().put(entry.getKey(), entry.getValue()[0]);
		}

		return rNode;
	}

	private GetNodeRq getGetNodeRq(HttpRequestAdapter request) {

		GetNodeRq result = new GetNodeRq();

		result.setId(request.getQueryString().getParameter(ID));
		result.setNodeType(request.getQueryString().getParameter(TYPE));

		return result;
	}
	
	private TestRq getTestRq(HttpRequestAdapter request) {
		TestRq result = new TestRq();
		return result;
	}

}