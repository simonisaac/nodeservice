package org.sri.nodeservice.transform.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

public class XmlValidator implements IXmlValidator {

	private String xsdClasspath = "xsd/nodeExcelSchema.xsd";
	private Schema schema;

	// Use spring to initialise this class with this method
	public void init() {
		SchemaFactory factory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		try {
			this.schema = factory.newSchema(new ClassPathResource(xsdClasspath)
					.getFile());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sri.nodeservice.transform.util.IXmlValidator#validate(java.io.InputStream
	 * )
	 */
	@Override
	public boolean validate(String xmlString) throws SAXException, IOException {
		Validator validator = schema.newValidator();
		Source source = new StreamSource(new StringReader(xmlString));
		validator.validate(source);
		return true;
	}

	public String getXsdClasspath() {
		return xsdClasspath;
	}

	public void setXsdClasspath(String xsdClasspath) {
		this.xsdClasspath = xsdClasspath;
	}
}
