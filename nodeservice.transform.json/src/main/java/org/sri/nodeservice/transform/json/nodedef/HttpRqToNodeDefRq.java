package org.sri.nodeservice.transform.json.nodedef;

import org.sri.nodeservice.core.nodedef.service.rsrq.BaseNodeDefRq;
import org.sri.nodeservice.core.nodedef.service.rsrq.GetNodeDefRq;
import org.sri.nodeservice.transform.common.nodedef.InHttpRqToNodeDefRq;
import org.sri.nodeservice.transform.common.util.HttpRequestAdapter;
import org.sri.nodeservice.transform.common.util.TransformContext;

/**
 * 
 * @author sisaac
 *
 */
public class HttpRqToNodeDefRq implements InHttpRqToNodeDefRq{

	public BaseNodeDefRq transform(TransformContext context, HttpRequestAdapter httpRequest) {

		BaseNodeDefRq rq = null;

		if (httpRequest != null) {
			String method = httpRequest.getQueryString().getParameter("method");

			if ("getNodeDef".equals(method)) {
				rq = this.getNodeMetadataRq(httpRequest);
			} 

			rq.setMethod(method);
			
			context.setObject("rq", rq);
			return rq;
		}

		return null;
	}


	public GetNodeDefRq getNodeMetadataRq(HttpRequestAdapter request) {

		GetNodeDefRq result = new GetNodeDefRq();

		result.setNodeType(request.getQueryString().getParameter("nodeType"));
		
		return result;
	}
}
