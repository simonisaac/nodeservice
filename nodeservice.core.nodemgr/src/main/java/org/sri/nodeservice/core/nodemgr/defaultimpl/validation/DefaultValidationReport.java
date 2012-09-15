package org.sri.nodeservice.core.nodemgr.defaultimpl.validation;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;
import org.sri.nodeservice.core.nodemgr.validation.report.IProblemIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IReportIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

/**
 * @author sisaac
 * 
 */
public class DefaultValidationReport implements IValidationReport {

	private INode reportNode;

	public final static String TARGET_NODE_ID = "targetNodeId";
	
	public final static String TARGET_NODE_UID = "targetNodeUID";

	public final static String PROBLEM_COUNT = "problemCount";

	public final static String CHILD_ERROR_COUNT = "childErrorCount";

	public final static String PROBLEMS_SET = "problems";

	public final static String CHILD_REPORTS_SET = "childReports";

	public final static String CONTEXT_NODE_SET = "contextNodes";

	public final static String WORST_PROBLEM_SEVERITY = "worstProblemSeverity";

	public final static String IS_ROOT = "isRoot";

	public final static String VALIDATION_REPORT_TYPE = "validationReportType";

	// -------------------------------------------------------------------------
	//
	// Construct

	private DefaultValidationReport(INode reportNode, INode targetNode) {
		this.reportNode = reportNode;
		this.reportNode.createAttr(TARGET_NODE_ID, targetNode.getNodeId());
		this.reportNode.createAttr(TARGET_NODE_UID, targetNode.getUID());
		this.reportNode.createAttr(PROBLEM_COUNT, "0");
		this.reportNode.createAttr(CHILD_ERROR_COUNT, "0");
		this.reportNode.createAttr(WORST_PROBLEM_SEVERITY, String.valueOf(ProblemSeverity.INFO.getLevel()));
		this.setWorstProblem(ProblemSeverity.INFO);
	}

	public DefaultValidationReport(INode reportNode) {
		this.reportNode = reportNode;
	}

	public static DefaultValidationReport createRootReport(INodeSet reportContainer, INode targetNode) {
		INode rootReportNode = reportContainer.createChildNode("report_" + targetNode.getNodeId(),
				VALIDATION_REPORT_TYPE);
		DefaultValidationReport rReport = new DefaultValidationReport(rootReportNode, targetNode);

		rootReportNode.createAttr(IS_ROOT, "true");

		return rReport;
	}

	// -------------------------------------------------------------------------
	//
	//

	@Override
	public IValidationReport createChildReport(INode targetNode) {
		INode childReportNode = INodeUtils.getOrCreateChildNode(this.reportNode, CHILD_REPORTS_SET, "report_"
				+ targetNode.getNodeId(), VALIDATION_REPORT_TYPE);
		IValidationReport rReport = new DefaultValidationReport(childReportNode, targetNode);
		return rReport;
	}

	// -------------------------------------------------------------------------
	//
	//

	/**
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationReport#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean rBool = true;
		if (this.getWorstProblemSeverity().isFatal()) {
			rBool = false;
		}
		return rBool;
	}

	@Override
	public void registerProblem(ProblemSeverity severity, String id, String message, String detail) {
		INodeSet problemSet = INodeUtils.getOrCreateNodeSet(this.reportNode, PROBLEMS_SET);
		INode problemNode = problemSet.createChildNode(this.reportNode.getNodeId() + "_prb_" + this.getProblemCount()
				+ 1, "ReportProblem");
		Problem problem = Problem.createProblem(problemNode, severity, id, message, detail);

		INodeUtils.incrementIntAttr(this.reportNode, PROBLEM_COUNT, 1, false);

		// set worst problem if required
		if (problem.getSeverity().worseThan(this.getWorstProblemSeverity())) {
			this.setWorstProblem(problem.getSeverity());
		}

		// Permeate up the tree if required.
		IValidationReport parentReport = this.getParentReport();
		if (parentReport != null) {
			parentReport.registerChildProblem(problem);
		}
	}

	/**
	 * @see org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport#registerChildProblem(org.sri.nodeservice.core.nodemgr.validation.report.Problem)
	 */
	@Override
	public void registerChildProblem(Problem problem) {
		// increment child problem cound
		if (problem.getSeverity().equals(ProblemSeverity.ERROR)) {
			INodeUtils.incrementIntAttr(this.reportNode, CHILD_ERROR_COUNT, 1, false);
		}
		// TODO need to add counts for the rest of the problem levels.

		// TODO - not sure if there should be a separeate 'worst child problem attribute'
		if (problem.getSeverity().worseThan(this.getWorstProblemSeverity())) {
			this.setWorstProblem(problem.getSeverity());
		}

		IValidationReport parentReport = this.getParentReport();
		if (parentReport != null) {
			parentReport.registerChildProblem(problem);
		}
	}

	/**
	 * @see org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport#getRootReport()
	 */
	@Override
	public IValidationReport getRootReport() {
		IValidationReport rRoot = this;
		IValidationReport parentReport = this;
		while (parentReport != null) {
			parentReport = parentReport.getParentReport();
			if (parentReport != null) {
				rRoot = parentReport;
			}
		}
		return rRoot;
	}

	/**
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationReport#getParentReport()
	 */
	@Override
	public IValidationReport getParentReport() {
		IValidationReport rReport = null;
		INode parentNode = this.reportNode.getParentNode();
		if (parentNode.getNodeDefId().equals(VALIDATION_REPORT_TYPE)) {
			rReport = new DefaultValidationReport(parentNode);
		}
		return rReport;
	}

	/**
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationReport#getWorstProblem()
	 */
	@Override
	public ProblemSeverity getWorstProblemSeverity() {
		int worstProblem = INodeUtils.getIntAttrValue(this.reportNode, WORST_PROBLEM_SEVERITY, false);
		return ProblemSeverity.fromLevel(worstProblem);
	}

	/**
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationReport#getProblemCount()
	 */
	@Override
	public int getProblemCount() {
		return INodeUtils.getIntAttrValue(this.reportNode, PROBLEM_COUNT, false);
	}

	@Override
	public int getProblemCount(ProblemSeverity severity) {
		int counter = 0;
		for (Problem p : this.getProblemIterator()) {
			if (p.getSeverity().equals(severity)) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationReport#getChildErrorCount()
	 */
	@Override
	public int getChildErrorCount() {
		return INodeUtils.getIntAttrValue(this.reportNode, CHILD_ERROR_COUNT, false);
	}

	@Override
	public String getNodeId() {
		return this.reportNode.getAttr(TARGET_NODE_ID).getValue();
	}

	@Override
	public IProblemIterator getProblemIterator() {
		INodeSet problemSet = INodeUtils.getOrCreateNodeSet(this.reportNode, PROBLEMS_SET);
		return new DefaultProblemIterator(problemSet);
	}

	@Override
	public IReportIterator getChildReportIterator() {
		INodeSet childReportSet = INodeUtils.getOrCreateNodeSet(this.reportNode, CHILD_REPORTS_SET);
		return new DefaultReportIterator(childReportSet);
	}

	@Override
	public IProblemIterator getProblemIterator(ProblemSeverity severity) {
		INodeSet problemSet = INodeUtils.getOrCreateNodeSet(this.reportNode, PROBLEMS_SET);
		DefaultProblemIterator rItr = new DefaultProblemIterator(problemSet);
		return rItr;
	}

	@Override
	public INode getContextNode(String id) {
		INode contextNode = INodeUtils.getOrCreateChildNode(this.reportNode, CONTEXT_NODE_SET, id, "ContextNode");
		return contextNode;
	}

	private void setWorstProblem(ProblemSeverity severity) {
		INodeUtils.setIntAttrValue(this.reportNode, WORST_PROBLEM_SEVERITY, severity.getLevel(), true);
	}

	public boolean isRootReport() {
		boolean rBool = false;
		if (this.reportNode.getAttr(IS_ROOT) != null) {
			rBool = true;
		}
		return rBool;
	}
}
