/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   14 Jul 201111:30:38
 * Workfile::  XmlToSheetTransformer.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.transform.excel;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.sri.nodeservice.transform.excel.Sheet;
import org.sri.nodeservice.transform.processing.ProcessingException;
import org.sri.nodeservice.transform.util.IXmlValidator;
import org.xml.sax.SAXException;

/**
 * @author jkochhar
 *
 */
public class XmlToSheetTransformer implements IXmlToSheetTransformer {
	
	private IXmlValidator validator;
	
	/* (non-Javadoc)
	 * @see org.sri.nodeservice.transform.util.IXmlToSheetTransformer#parseProcessingInstructionXml(java.lang.String)
	 */
	@Override
	public Sheet parseProcessingInstructionXml(String xmlAsString) throws JAXBException,
			IOException {

		try {
			validator.validate(xmlAsString);
		} catch (SAXException e) {
			throw new ProcessingException(
					"The XML processing instruction Failed the XML schema validation ",
					e);
		}

		JAXBContext jaxbContext;
		Sheet sheet = null;
		jaxbContext = JAXBContext
				.newInstance("org.sri.nodeservice.transform.excel");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		JAXBElement<Sheet> element = (JAXBElement<Sheet>) unmarshaller
				.unmarshal(new StreamSource(new StringReader(xmlAsString)));
		sheet = element.getValue();
		return sheet;
	}

	public void setValidator(IXmlValidator validator) {
		this.validator = validator;
	}

}
