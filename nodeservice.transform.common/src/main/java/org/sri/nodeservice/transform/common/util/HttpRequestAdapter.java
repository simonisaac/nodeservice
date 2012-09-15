package org.sri.nodeservice.transform.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author sisaac
 *
 */
public class HttpRequestAdapter {

	private QueryString qs;
	
	public HttpRequestAdapter(String muleQueryString) {

		this.qs = new QueryString();
		
		String[] parts = muleQueryString.split("\\?");
		if (parts.length > 1) {
			String decodedParameterMap;
			try {
				decodedParameterMap = URLDecoder.decode(parts[1], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			this.qs.setString(decodedParameterMap);
		}
	}

	public QueryString getQueryString() {
		return qs;
	}

	public List<String> getParameterAsList(String parameterName) {
		List<String> stringList = new ArrayList<String>();

		String requiredMeta = qs.getParameter(parameterName);

		if (requiredMeta != null && !"".equals(requiredMeta)) {

			String[] metaArray = requiredMeta.split(",");
			for (int i = 0; i < metaArray.length; i++) {
				stringList.add(metaArray[i]);
			}
		}

		return stringList;
	}
}
