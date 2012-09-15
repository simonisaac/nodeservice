package org.sri.nodeservice.web.nodedef.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.sri.nodeservice.core.nodedef.service.model.NodeDefTO;
import org.sri.nodeservice.core.nodedef.service.rsrq.GetNodeDefRs;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgrDelegate;
import org.sri.nodeservice.web.util.HttpUtils;

import flexjson.JSONDeserializer;

public class HttpJSONDelegate implements NodeDefMgrDelegate {
	
	private String jsonUrl;
	
	public HttpJSONDelegate(String jsonUrl){
		this.jsonUrl = jsonUrl;
	}

	@Override
	public NodeDefTO getNodeDefinitionTO(String nodeType) {
		NodeDefTO to = null;
		try {
			Map<String, String[]> paramMap = new HashMap<String, String[]>();
			paramMap.put("method", new String[] { "getNodeDef" });
			paramMap.put("nodeType", new String[] { nodeType });

			String urlString = HttpUtils.getURLString(paramMap, this.jsonUrl);

			// create request
			HttpPost httppost = new HttpPost(urlString);

			// remote call
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse remoteResponse = httpClient.execute(httppost);
			
			HttpEntity rEntity = remoteResponse.getEntity();
			String jsonString = EntityUtils.toString(rEntity);
			GetNodeDefRs rs = new JSONDeserializer<GetNodeDefRs>().deserialize(jsonString);
			to = rs.getNodeDefinition();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}

		return to;
	}
}
