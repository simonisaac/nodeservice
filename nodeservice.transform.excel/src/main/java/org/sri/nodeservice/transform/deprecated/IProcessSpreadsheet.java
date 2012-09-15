package org.sri.nodeservice.transform.deprecated;

import java.io.File;
import java.io.InputStream;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.transform.excel.Sheet;
import org.sri.nodeservice.transform.processing.ProcessingException;

public interface IProcessSpreadsheet {
	public Node process(File spreadSheet, File processingInstructionXml)
			throws ProcessingException;

	public Node process(InputStream spreadSheet,
			InputStream processingInstructionXml)
			throws ProcessingException;

	public Node process(InputStream spreadSheet, Sheet processingInstSheet)
			throws ProcessingException;
}
