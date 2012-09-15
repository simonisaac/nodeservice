package org.sri.nodeservice.transform.deprecated;


@Deprecated
public class ExcelProcessingManager {

	/*private ProcessingManager processor;

	public void init(INodeStore nodeStore) {
		this.processor = new ProcessingManager(nodeStore);

		// Load language and create nodedef manager
		XMLNodeDefLoader xmlNodeDefLoader = new XMLNodeDefLoader();
		xmlNodeDefLoader.loadNodeDefsFromClasspathResource("spreadsheettests/simple/simpleLanguageDef.xml");
		NodeDefMgr nodeDefMgr = xmlNodeDefLoader.getNodeDefMgr();

		// load processing instructions
		ExcelProcessorLoader excelProcessorLoader = new ExcelProcessorLoader();
		IProcessor excelProcessor = excelProcessorLoader
				.loadSpreadsheetProcessorFromClasspathResource("spreadsheettests/simple/simpleProcessingInstructions.xml");

		// set up the validation callback
		List<ReportCallback> reportCallbackList = new ArrayList<ReportCallback>();
		reportCallbackList.add(new ReportCallbackNodeDefValidator());
		reportCallbackList.add(new ReportCallbackAccumulator());

		ProcessingDef pack = new ProcessingDef("packId-testExcel", nodeDefMgr, "testExcel", /*containerNodeSet, rootReportContainerNodeSet,
				excelProcessor, reportCallbackList);
		this.processor.addProcessingPack(pack);
	}

	public ProcessingManager getProcessor() {
		return processor;
	}

	public void setProcessor(ProcessingManager processor) {
		this.processor = processor;
	}*/
}
