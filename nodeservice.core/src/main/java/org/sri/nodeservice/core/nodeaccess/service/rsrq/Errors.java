package org.sri.nodeservice.core.nodeaccess.service.rsrq;

import java.util.ArrayList;
import java.util.List;

public class Errors {
	private List<ErrorDetail> errorDetails = new ArrayList<ErrorDetail>();

	public List<ErrorDetail> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(List<ErrorDetail> errorDetails) {
		this.errorDetails = errorDetails;
	}
	
	public void addErrorDetail(ErrorDetail errorDetail){
		errorDetails.add(errorDetail);
	}
}
