/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   17 Oct 201110:39:14
 * Workfile::  ServiceException.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.transform.common.exception;

/**
 * @author jkochhar
 *
 */
public class ServiceException {

	private String code;
	
	private String message;
	
	/**
	 * @param message
	 */
	public ServiceException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}
}
