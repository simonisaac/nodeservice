package org.sri.nodeservice.transform.excel.testutil;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.sri.nodeservice.core.nodemgr.defaultimpl.node.DefaultNode;
import org.sri.nodeservice.core.nodemgr.defaultimpl.node.DefaultNodeStore;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.util.transform.XMLNodeDefLoader;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallback;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallbackAccumulator;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallbackNodeDefValidator;
import org.sri.nodeservice.transform.excel.IXmlToSheetTransformer;
import org.sri.nodeservice.transform.excel.Sheet;
import org.sri.nodeservice.transform.excel.XmlToSheetTransformer;
import org.sri.nodeservice.transform.excel.processorimpls.ProcessorImplExcelSimpleRows;
import org.sri.nodeservice.transform.processing.IProcessor;
import org.sri.nodeservice.transform.processing.IProcessorDefaultsLoader;
import org.sri.nodeservice.transform.processing.ProcessingDef;
import org.sri.nodeservice.transform.processing.ProcessingException;
import org.sri.nodeservice.transform.processing.ProcessingManager;
import org.sri.nodeservice.transform.util.CommonUtils;
import org.sri.nodeservice.transform.util.XmlValidator;

public class ExcelProcessor_Simple_ShortValid_TstFixtureMgr {

	public ProcessingManager loadProcessor(INodeStore nodeStore, String languageDef, String processingInstructions,
			boolean loadDefaults) {
		ProcessingManager processor = new ProcessingManager(nodeStore);

		// Load language and create nodedef manager
		XMLNodeDefLoader xmlNodeDefLoader = new XMLNodeDefLoader();
		xmlNodeDefLoader.loadNodeDefsFromClasspathResource(languageDef);
		NodeDefMgr nodeDefMgr = xmlNodeDefLoader.getNodeDefMgr();

		// load processing instructions
		IProcessor excelProcessor = this.loadSpreadsheetProcessorFromClasspathResource(processingInstructions);

		// set up the validation callback
		List<ReportCallback> reportCallbackList = new ArrayList<ReportCallback>();
		reportCallbackList.add(new ReportCallbackNodeDefValidator());
		reportCallbackList.add(new ReportCallbackAccumulator());

		DefaultNodeStore defaultsNodeStore = new DefaultNodeStore();
		if (loadDefaults) {
			DefaultNode defaultRow = (DefaultNode) defaultsNodeStore.createRootNode("DummyId", "test3SheetType");
			defaultRow.createAttr("A - String", "DefaultAString");
		}
		IProcessorDefaultsLoader loader = new IProcessorDefaultsLoader();
		loader.setDefaultsNodeStore(defaultsNodeStore);

		ProcessingDef pack = new ProcessingDef("packId-testExcel", nodeDefMgr, "testExcel", excelProcessor, loader,
				reportCallbackList);
		processor.addProcessingDef(pack);

		return processor;
	}

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
