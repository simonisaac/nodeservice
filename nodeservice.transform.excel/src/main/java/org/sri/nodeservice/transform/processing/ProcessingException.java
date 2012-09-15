package org.sri.nodeservice.transform.processing;

public class ProcessingException extends RuntimeException {

	private static final long serialVersionUID = -1864999235988160792L;

	public ProcessingException(String message){
		super(message);
	}
	public ProcessingException(Throwable cause) {
		super(cause);
	}

	public ProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

}
