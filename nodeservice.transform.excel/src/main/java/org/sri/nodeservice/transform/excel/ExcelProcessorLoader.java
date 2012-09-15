package org.sri.nodeservice.transform.excel;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.sri.nodeservice.transform.excel.processorimpls.ProcessorImplExcelSimpleRows;
import org.sri.nodeservice.transform.processing.IProcessor;
import org.sri.nodeservice.transform.processing.ProcessingException;
import org.sri.nodeservice.transform.util.CommonUtils;
import org.sri.nodeservice.transform.util.XmlValidator;

public class ExcelProcessorLoader {

	/*public ProcessingManager loadProcessor(INodeStore nodeStore, String languageDef, String processingInstructions) {
		ProcessingManager processor = new ProcessingManager(nodeStore);

		// Load language and create nodedef manager
		XMLNodeDefLoader xmlNodeDefLoader = new XMLNodeDefLoader();
		xmlNodeDefLoader.loadNodeDefsFromClasspathResource("spreadsheettests/simple/simpleLanguageDef.xml");
		NodeDefMgr nodeDefMgr = xmlNodeDefLoader.getNodeDefMgr();

		// load processing instructions
		IProcessor excelProcessor = this.loadSpreadsheetProcessorFromClasspathResource("spreadsheettests/simple/simpleProcessingInstructions.xml");

		// set up the validation callback
		List<ReportCallback> reportCallbackList = new ArrayList<ReportCallback>();
		reportCallbackList.add(new ReportCallbackNodeDefValidator());
		reportCallbackList.add(new ReportCallbackAccumulator());

		ProcessingDef pack = new ProcessingDef("packId-testExcel", nodeDefMgr, "testExcel", /*containerNodeSet, rootReportContainerNodeSet,
				excelProcessor, reportCallbackList);
		processor.addProcessingPack(pack);
		
		return processor;
	}*/
	
	public IProcessor loadSpreadsheetProcessorFromClasspathResource(String path) {
		try {
			Resource resource = new ClassPathResource(path);
			return this.loadSpreadsheetProcessorFromFile(resource.getFile());
		} catch (Throwable e) {
			throw new ProcessingException(e);
		}
	}

	public IProcessor loadSpreadsheetProcessorFromFile(File processingInstructions) {
		try {
			FileInputStream processingInstructionXmlInputStream = new FileInputStream(processingInstructions);
			Sheet processingInstSheet = this.getTransformer().parseProcessingInstructionXml(
					CommonUtils.convertStreamToString(processingInstructionXmlInputStream));
			IProcessor processor = new ProcessorImplExcelSimpleRows(processingInstSheet);
			return processor;
		} catch (Throwable e) {
			throw new ProcessingException(e);
		}
	}

	private IXmlToSheetTransformer getTransformer() {
		XmlValidator xmlValidator = new XmlValidator();
		xmlValidator.setXsdClasspath("xsd/nodeExcelSchema.xsd");
		xmlValidator.init();

		XmlToSheetTransformer transformer = new XmlToSheetTransformer();
		transformer.setValidator(xmlValidator);
		return transformer;
	}
}
