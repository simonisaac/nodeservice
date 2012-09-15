package org.sri.nodeservice.core.nodeaccess.service.rsrq;

public class ErrorDetail {

	private int code;
	
	private String description;
	
	private String fieldName;
	
	private ErrorTypeEnum type;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public ErrorTypeEnum getType() {
		return type;
	}

	public void setType(ErrorTypeEnum type) {
		this.type = type;
	}
}
