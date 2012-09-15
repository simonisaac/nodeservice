package org.sri.nodeservice.core.nodeaccess.service.rsrq;

/**
 * 
 * @author sisaac
 *
 */
public class BaseNodeRq {
	
	private String sessionToken;
	
	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
}
