package org.sri.nodeservice.transform.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author sisaac
 *
 */
public class TransformContext {
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Object getObject (String key) {
		return this.map.get(key);
	}
	
	public void setObject (String key, Object value) {
		this.map.put(key, value);
	}
}
