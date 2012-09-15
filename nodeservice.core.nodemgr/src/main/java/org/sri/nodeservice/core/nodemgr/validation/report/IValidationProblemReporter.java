package org.sri.nodeservice.core.nodemgr.validation.report;

public interface IValidationProblemReporter {
	
	public abstract void registerProblem(ProblemSeverity error, String string, String message, String detail);
	
}
