package org.sri.nodeservice.transform.util;

import java.io.IOException;

import org.xml.sax.SAXException;

public interface IXmlValidator {

	public abstract boolean validate(String xmlString) throws SAXException,
			IOException;

}