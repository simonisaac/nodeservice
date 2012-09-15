package org.sri.nodeservice.transform.processing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodemgr.defaultimpl.validation.DefaultValidationReport;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;

public class ProcessingResult {

	private INode jobNode;

	public static String NODE_DEF_ID = "processingJobRootType";

	public static String VALID = "valid";

	public static String STATE = "jobState";

	public ProcessingResult(INode jobNode) {
		this.jobNode = jobNode;
	}

	private ProcessingResult(ProcessingDef pack, INodeSet jobsContainer) {
		// create the root node for the processing job and add meta data
		Date processingStartTime = new Date();

		this.jobNode = jobsContainer.createChildNode("job_" + processingStartTime.getTime(), NODE_DEF_ID);

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String dateString = df.format(processingStartTime);
		this.jobNode.createAttr("jobStartTime", dateString);
		this.jobNode.createAttr("processingPackId", pack.getProcessingDefId());
		this.jobNode.createAttr(VALID, "false");
		this.jobNode.createAttr(STATE, "new");
	}

	public static ProcessingResult createProcessingResult(ProcessingDef pack, INodeSet jobsContainer) {
		ProcessingResult rJob = new ProcessingResult(pack, jobsContainer);
		return rJob;
	}

	public INodeSet getResultsNodeSet() {
		INodeSet rNodeSet = INodeUtils.getOrCreateNodeSet(this.jobNode, "resultsNodes");
		return rNodeSet;
	}

	public INodeIterator getRootResultsNodeIterator() {
		INodeSet rNodeSet = INodeUtils.getOrCreateNodeSet(this.jobNode, "resultsNodes");
		return rNodeSet.getINodeIterator();
	}

	public INode getRootResultNodeAt(int index) {
		INode rNode = null;

		int count = 0;
		for (INode node : this.getRootResultsNodeIterator()) {
			rNode = node;
			if (index == count++) {
				break;
			}
			rNode = null;
		}

		return rNode;
	}

	public IValidationReport createRootReport(INode rootTargetNode) {
		INodeSet reportNodeSet = INodeUtils.getOrCreateNodeSet(this.jobNode, "reportNodes");
		DefaultValidationReport rReport = DefaultValidationReport.createRootReport(reportNodeSet, rootTargetNode);
		return rReport;
	}

	public DefaultValidationReport getReportRoot() {
		DefaultValidationReport rReport = null;
		INodeSet reportNodes = this.jobNode.getChildNodeSet("reportNodes");
		INodeIterator childItr = reportNodes.getINodeIterator();
		if (childItr.hasNext()) {
			INode reportRootNode = childItr.next();
			rReport = new DefaultValidationReport(reportRootNode);
			assert !childItr.hasNext() : "There should be only one root report.";
		}
		return rReport;
	}

	public DefaultValidationReport getReportForNodeUID(String uid) {
		DefaultValidationReport rReport = null;

		Node query = new Node(null, null, DefaultValidationReport.VALIDATION_REPORT_TYPE);
		query.getFields().put(DefaultValidationReport.TARGET_NODE_UID, uid);
		INodeIterator itr = this.getJobNode().getNodeStore().find(query);
		if (itr.hasNext()) {
			rReport = new DefaultValidationReport(itr.next());
			assert itr.hasNext() == false;
		}

		return rReport;
	}

	public void setState(String state) {
		this.jobNode.getAttr(STATE).setValue(state);
	}

	public void setValid(boolean bool) {
		this.jobNode.getAttr(VALID).setValue(String.valueOf(bool));
	}

	public boolean isValid() {
		return INodeUtils.getBooleanAttrValue(this.jobNode, VALID, false);
	}

	public INode getJobNode() {
		return jobNode;
	}
}
