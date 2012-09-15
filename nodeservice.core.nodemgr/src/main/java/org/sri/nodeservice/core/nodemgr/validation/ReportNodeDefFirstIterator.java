package org.sri.nodeservice.core.nodemgr.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.util.NodeDefFirstIteratorCallback;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;

public class ReportNodeDefFirstIterator implements NodeDefFirstIteratorCallback {

	private IValidationReport rootReport;

	private List<ReportCallback> reportCallbackList = new ArrayList<ReportCallback>();

	private Stack<IValidationReport> stack = new Stack<IValidationReport>();

	public ReportNodeDefFirstIterator(IValidationReport rootReport) {
		this.rootReport = rootReport;
	}

	@Override
	public void beginAttr(AttrDef curAttrDef, INodeAttr curAttr) {

		for (ReportCallback rc : this.reportCallbackList) {
			rc.beginAttr(this.stack.peek(), curAttrDef, curAttr);
		}
	}

	@Override
	public void beginNode(NodeDef nodeDef, INode node) {

		IValidationReport currentReport = null;
		if (this.stack.size() == 0) {
			currentReport = this.rootReport;
		} else {
			IValidationReport parentReport = this.stack.peek();
			currentReport = parentReport.createChildReport(node);
		}
		this.stack.push(currentReport);

		for (ReportCallback rc : this.reportCallbackList) {
			rc.beginNode(this.stack.peek(), nodeDef, node);
		}
	}

	@Override
	public void beginNodeSet(NodeSetDef curChildSetDef, INodeSet curChildSet) {
		for (ReportCallback rc : this.reportCallbackList) {
			rc.beginNodeSet(this.stack.peek(), curChildSetDef, curChildSet);
		}
	}

	@Override
	public void endNode(NodeDef nodeDef, INode node) {
		// call endNode on all registered callbacks before 
		// popping the stack.
		for (ReportCallback rc : this.reportCallbackList) {
			rc.endNode(this.stack.peek(), nodeDef, node);
		}
		IValidationReport report = this.stack.pop();
		assert report.getNodeId().equals(node.getNodeId()) : "Assertion error, stack usage not valid.";
	}

	@Override
	public void endNodeSet(NodeSetDef curChildSetDef, INodeSet curChildSet) {
		for (ReportCallback rc : this.reportCallbackList) {
			rc.endNodeSet(this.stack.peek(), curChildSetDef, curChildSet);
		}
	}

	@Override
	public void endAttr(AttrDef curAttrDef, INodeAttr curAttr) {
		for (ReportCallback rc : this.reportCallbackList) {
			rc.endAttr(this.stack.peek(), curAttrDef, curAttr);
		}
	}

	public IValidationReport getRootReport() {
		return this.rootReport;
	}

	public void addReportCallback(ReportCallback rc) {
		this.reportCallbackList.add(rc);
	}
}
