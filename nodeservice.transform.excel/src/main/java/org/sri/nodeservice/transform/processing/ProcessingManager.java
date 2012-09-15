package org.sri.nodeservice.transform.processing;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;
import org.sri.nodeservice.core.nodemgr.util.NodeDefFirstIterator;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallback;
import org.sri.nodeservice.core.nodemgr.validation.ReportNodeDefFirstIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;

public class ProcessingManager {

	private INodeStore nodeStore;

	private INode processorRootNode;

	private Map<String, ProcessingDef> processingDefMap = new HashMap<String, ProcessingDef>();

	public ProcessingManager(INodeStore nodeStore) {
		assert nodeStore != null;
		this.nodeStore = nodeStore;
		this.processorRootNode = INodeUtils.getOrCreateRootNode(this.nodeStore, "processorRoot", "processorRootType");
	}

	public ProcessingResult process(File spreadsheetFile, String spreadsheetType) {

		ProcessingDef processingDef = this.processingDefMap.get(spreadsheetType);

		// get the container fore jobs of this type and create a new job result.
		INodeSet jobsContainer = INodeUtils.getOrCreateNodeSet(this.processorRootNode, spreadsheetType);
		ProcessingResult rJob = ProcessingResult.createProcessingResult(processingDef, jobsContainer);

		// load INodes containing default values if there are any.
		IProcessorDefaultsLoader defaultsLoader = processingDef.getDefaultsLoader();
		INodeStore defaultsNodeStore = defaultsLoader.loadDefaults();

		// run the job
		INode rootNode = processingDef.getProcessor().process(rJob.getResultsNodeSet(), spreadsheetFile,
				defaultsNodeStore);

		// Run the Validation
		NodeDefFirstIterator vitr = new NodeDefFirstIterator(processingDef.getNodeDefMgr());
		ReportNodeDefFirstIterator validationCallback = new ReportNodeDefFirstIterator(
				rJob.createRootReport(rootNode));
		for (ReportCallback callback : processingDef.getReportCallbackList()) {
			validationCallback.addReportCallback(callback);
		}
		NodeDef rootNodeDef = processingDef.getNodeDefMgr().getNodeDefinition(processingDef.getRootNodeDefId());
		vitr.iterate(rootNode, rootNodeDef, validationCallback);
		IValidationReport report = validationCallback.getRootReport();

		if (report.isValid()) {
			rJob.setValid(true);
		} else {
			rJob.setValid(false);
			// TODO: write the error report to returned spreadsheet.
		}

		return rJob;
	}

	public ProcessingResult getNextValidUnloadedJob() {
		ProcessingResult rJob = null;

		Node query = new Node(null, null, ProcessingResult.NODE_DEF_ID);
		query.setField(ProcessingResult.VALID, "true");
		query.setField(ProcessingResult.STATE, "new");
		INodeIterator results = nodeStore.find(query);

		if (results.hasNext()) {
			INode root = results.next();
			rJob = new ProcessingResult(root);
			rJob.setState("inprogress");
		}

		return rJob;
	}
	
	public ProcessingDef getProcessingDef (String id) {
		return this.processingDefMap.get(id);
	}

	public void addProcessingDef(ProcessingDef pack) {
		this.processingDefMap.put(pack.getProcessingDefId(), pack);
	}
}
