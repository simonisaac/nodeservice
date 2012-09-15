package org.sri.nodeservice.transform.extjs.stnd.nodeaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodeaccess.service.rsrq.BaseNodeRs;

/**
 * Simple response to make it easy to provide the JSON in the 
 * form that ExtJS wants.
 * 
 * @author sisaac
 */
public class ExtJSLoadNodeFormRs extends ExtJSRs {

	private boolean success = true;

	private Map<String, String> data = new HashMap<String, String>();
	
	private Map<String, List<Map<String, String>>> childNodeSets = new HashMap<String, List<Map<String, String>>>();
	
	//Persisted errors to display along the loaded form field
	private Map<String, String> errors = new HashMap<String, String>();
	
	/**
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	/**
	 * @return the childNodeSets
	 */
	public Map<String, List<Map<String, String>>> getChildNodeSets() {
		return childNodeSets;
	}
	
	/**
	 * @param childNodeSets the childNodeSets to set
	 */
	public void setChildNodeSets(
			Map<String, List<Map<String, String>>> childNodeSets) {
		this.childNodeSets = childNodeSets;
	}
}

/*
 * { "success":false, "errors":{
 * "lastName":"Last Name has to have at least 2 characters",
 * "email":"Domain blocked - it is known spam source" } }
 */