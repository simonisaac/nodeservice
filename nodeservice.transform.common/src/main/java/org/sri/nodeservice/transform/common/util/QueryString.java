package org.sri.nodeservice.transform.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author sisaac
 *
 */
public class QueryString {

	private Map<String, List<String>> parameters = new TreeMap<String, List<String>>();

	public QueryString() {
		parameters = new TreeMap<String, List<String>>();
	}

	public QueryString(String qs) {
		this.setString(qs);
	}

	public void setString(String qs) {
		
		parameters = new TreeMap<String, List<String>>();
		
		/*try {
			String qsDecoded = URLDecoder.decode(qs, "UTF-8");
			qs = qsDecoded;
		}
		catch (UnsupportedEncodingException e) {
			// Not really possible, throw unchecked
			throw new IllegalStateException("No UTF-8");
		}*/

		// Parse query string
		
		String newQs = qs.replaceAll("&amp;", "#38;");
		String pairs[] = newQs.split("&");
		for (String pair : pairs) {
			String name;
			String value;
			String newValue;
			int pos = pair.indexOf('=');
			// for "n=", the value is "", for "n", the value is null
			if (pos == -1) {
				name = pair;
				newValue = null;
			}
			else {
				name = pair.substring(0, pos);
				value = pair.substring(pos + 1, pair.length());
				newValue = value.replaceAll("#38;", "&");
			}
			List<String> list = parameters.get(name);
			if (list == null) {
				list = new ArrayList<String>();
				parameters.put(name, list);
			}
			list.add(newValue);
		}
	}

	public String getParameter(String name) {
		return this.getParameter(name, true);
	}
	
	public String getParameter(String name, boolean mandatory) {
		String rString = null;
		
		List<String> values = parameters.get(name);		
		if (values != null && values.size() > 0) {
			rString = values.get(0);
		}

		if (rString == null && mandatory) {
			throw new RuntimeException("Mandatory parameter [" + name + "] not found");
		}

		return rString;
	}

	public boolean getBooleanParameter(String name) {
		String stringVersion = this.getParameter(name);
		return Boolean.parseBoolean(stringVersion);
	}

	public int getIntParameter(String name) {

		return this.getIntParameter(name, true);
	}
	
	public int getIntParameter(String name, boolean mandatory) {
		int rInt = -1;
		String stringVersion = this.getParameter(name, mandatory);
		if (stringVersion != null && !stringVersion.equals("")) {
			rInt = Integer.parseInt(stringVersion);
		}
		return rInt;
	}

	public String[] getParameterValues(String name) {
		List<String> values = parameters.get(name);
		if (values == null)
			return null;

		return (String[]) values.toArray(new String[values.size()]);
	}

	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(parameters.keySet());
	}

	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		for (Map.Entry<String, List<String>> entry : parameters.entrySet()) {
			List<String> list = entry.getValue();
			String[] values;
			if (list == null)
				values = null;
			else
				values = (String[]) list.toArray(new String[list.size()]);
			map.put(entry.getKey(), values);
		}
		return map;
	}
}
