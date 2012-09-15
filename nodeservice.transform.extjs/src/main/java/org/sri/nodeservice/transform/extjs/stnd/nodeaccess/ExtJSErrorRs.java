package org.sri.nodeservice.transform.extjs.stnd.nodeaccess;

import java.util.HashMap;
import java.util.Map;

public class ExtJSErrorRs extends ExtJSRs {
	private boolean success = false;

	private Map<String, String> errors = new HashMap<String, String>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
