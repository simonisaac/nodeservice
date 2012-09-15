package org.sri.nodeservice.transform.processing;

import java.util.List;

import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallback;

public class ProcessingDef {

	// the node def to be applied to the loaded nodes.
	private String rootNodeDefId;

	private IProcessor processor;

	private IProcessorDefaultsLoader defaultsLoader;

	private NodeDefMgr nodeDefMgr;

	private List<ReportCallback> reportCallbackList;

	private String processingDefId;

	public ProcessingDef(String packId, NodeDefMgr nodeDefMgr, String rootNodeDefId, IProcessor processor,
			IProcessorDefaultsLoader defaultsLoader, List<ReportCallback> reportCallbackList) {
		this.processingDefId = packId;
		this.nodeDefMgr = nodeDefMgr;
		this.rootNodeDefId = rootNodeDefId;
		this.processor = processor;
		this.defaultsLoader = defaultsLoader;
		this.reportCallbackList = reportCallbackList;
	}

	public String getProcessingDefId() {
		return processingDefId;
	}

	public void setProcessingDefId(String packId) {
		this.processingDefId = packId;
	}

	public IProcessorDefaultsLoader getDefaultsLoader() {
		return defaultsLoader;
	}

	public void setDefaultsLoader(IProcessorDefaultsLoader defaultsLoader) {
		this.defaultsLoader = defaultsLoader;
	}

	public String getRootNodeDefId() {
		return rootNodeDefId;
	}

	public void setRootNodeDefId(String rootNodeDefId) {
		this.rootNodeDefId = rootNodeDefId;
	}

	public IProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(IProcessor processor) {
		this.processor = processor;
	}

	public NodeDefMgr getNodeDefMgr() {
		return nodeDefMgr;
	}

	public void setNodeDefMgr(NodeDefMgr nodeDefMgr) {
		this.nodeDefMgr = nodeDefMgr;
	}

	public List<ReportCallback> getReportCallbackList() {
		return reportCallbackList;
	}

	public void setReportCallbackList(List<ReportCallback> reportCallbackList) {
		this.reportCallbackList = reportCallbackList;
	}
}