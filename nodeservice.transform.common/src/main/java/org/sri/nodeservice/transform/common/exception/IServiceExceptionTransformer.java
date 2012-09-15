/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   17 Oct 201110:27:15
 * Workfile::  IServiceExceptionTransformer.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.transform.common.exception;

import org.sri.nodeservice.transform.common.util.TransformContext;


/**
 * @author jkochhar
 *
 */
public interface IServiceExceptionTransformer {
	
	/**
	 * 
	 * @param throwable
	 * @return
	 */
	public ServiceException transform(TransformContext context, Throwable throwable);

}
