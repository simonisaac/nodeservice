package org.sri.nodeservice.core.nodeaccess.service.rsrq;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author sisaac
 *
 */
public class BaseNodeRs {
	
	private String sessionToken;
	
	private boolean success = true;
	
	private Errors errors;
	
	/**
	 * Additional Params other than Nodes can be sent back using this.
	 */
	private Map<String, String> otherParams = new HashMap<String, String>();
	
	/**
	 * @return the otherParams
	 */
	public Map<String, String> getOtherParams() {
		return otherParams;
	}
	
	/**
	 * @param otherParams the otherParams to set
	 */
	public void setOtherParams(Map<String, String> otherParams) {
		this.otherParams = otherParams;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}
	
	public void addError(ErrorDetail errorDetail){
		if(errors == null){
			errors = new Errors();
		}
		errors.addErrorDetail(errorDetail);
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
}
