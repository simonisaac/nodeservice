package org.sri.nodeservice.core.nodemgr.util.test;

import org.sri.nodeservice.core.nodemgr.defaultimpl.validation.DefaultValidationReport;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.util.NodeDefFirstIterator;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallbackAccumulator;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallbackNodeDefValidator;
import org.sri.nodeservice.core.nodemgr.validation.ReportNodeDefFirstIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;

public class FixUtils {

	public static IValidationReport validate(BaseTstFixture fix, INode valiationTarget, NodeDef simpleNodeDef) {
		NodeDefFirstIterator itr = new NodeDefFirstIterator(fix.getNodeDefMgr());
		INode reportNode = fix.getNodeStore().createRootNode("reportRootColl", "rootReportColl");
		INodeSet reportCollection = reportNode.createChildNodeSet("reportCollection");
		DefaultValidationReport rootReport = DefaultValidationReport.createRootReport(reportCollection, valiationTarget);
		ReportNodeDefFirstIterator callback = new ReportNodeDefFirstIterator(rootReport);
		callback.addReportCallback(new ReportCallbackNodeDefValidator());
		callback.addReportCallback(new ReportCallbackAccumulator());
		itr.iterate(valiationTarget, simpleNodeDef, callback);
		IValidationReport report = callback.getRootReport();
		return report;
	}
}
