package org.sri.nodeservice.core.nodemgr.validation.report;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;



public interface IValidationReport extends IValidationProblemReporter {

	public abstract boolean isValid();

	public abstract IProblemIterator getProblemIterator ();
	
	public abstract IProblemIterator getProblemIterator (ProblemSeverity severity);
	
	public abstract IValidationReport getParentReport();

	public abstract ProblemSeverity getWorstProblemSeverity();

	public abstract int getProblemCount();

	public abstract int getChildErrorCount();
	
	public abstract INode getContextNode (String id);
	
	public abstract void registerChildProblem(Problem problem);

	public IReportIterator getChildReportIterator();

	public abstract String getNodeId();

	public abstract IValidationReport getRootReport();

	public abstract IValidationReport createChildReport(INode node);

	int getProblemCount(ProblemSeverity severity);
}