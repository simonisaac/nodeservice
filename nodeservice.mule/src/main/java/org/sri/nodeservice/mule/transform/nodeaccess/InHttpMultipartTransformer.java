/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   22 Jul 201111:34:12
 * Workfile::  InHttpMultipartTransformer.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.mule.transform.nodeaccess;

import org.mule.api.MuleMessage;
import org.sri.nodeservice.core.nodeaccess.service.model.Node;

/**
 * @author jkochhar
 *
 */
public interface InHttpMultipartTransformer {

	public Node transformMessage(MuleMessage message);
}
