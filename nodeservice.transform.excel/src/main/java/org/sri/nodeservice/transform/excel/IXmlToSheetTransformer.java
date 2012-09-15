/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   14 Jul 201111:32:49
 * Workfile::  IXmlToSheetTransformer.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.transform.excel;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.sri.nodeservice.transform.excel.Sheet;

/**
 * @author jkochhar
 *
 */
public interface IXmlToSheetTransformer {

	public abstract Sheet parseProcessingInstructionXml(String xmlAsString)
			throws JAXBException, IOException;

}