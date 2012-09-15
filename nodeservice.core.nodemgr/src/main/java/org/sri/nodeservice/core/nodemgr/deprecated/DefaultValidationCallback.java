package org.sri.nodeservice.core.nodemgr.deprecated;


/**
 * @author sisaac
 *
 */
@Deprecated
public class DefaultValidationCallback /*implements IValidationCallback */{

	//private IValidationReport rootReport;

	//private Stack<DefaultValidationReport> stack = new Stack<DefaultValidationReport>();

	/* (non-Javadoc)
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationCallback#addProblem(java.lang.String, org.sri.nodeservice.core.nodemgr.nodedef.problem.ProblemSeverity, java.lang.String, java.lang.String)
	 */
	/*@Deprecated
	@Override
	public void addProblem(String id, ProblemSeverity severity, String message, String detailMessage) {
		Problem prop = new Problem(severity, id, message, detailMessage);
		this.stack.peek().registerProblem(prop);
	}*/

	/* (non-Javadoc)
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationCallback#endValidation(java.lang.String, java.lang.String)
	 *
	@Override
	public void endValidation(String entityType, String entityId) {
		IValidationReport report = this.stack.pop();
		if (!report.getEntityType().equals(entityType) || !report.getEntityId().equals(entityId)) {
			throw new RuntimeException("Assertion error, stack usage not valid.");
		}
	}

	/* (non-Javadoc)
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationCallback#beginValidation(java.lang.String, java.lang.String, java.lang.Object)
	 */
	/*@Override
	@Deprecated
	public void beginValidation(String entityType, String entityId, Object node) {
		/*DefaultValidationReport parentReport = null;
		if (this.stack.size() > 0) {
			parentReport = this.stack.peek();
		}
		DefaultValidationReport report = new DefaultValidationReport(entityType, entityId, parentReport);
		this.stack.push(report);
		if (this.rootReport == null) {
			this.rootReport = report;
		}*/
	//}

	/* (non-Javadoc)
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationCallback#getRoot()
	 *
	@Override
	public DefaultValidationReport getRoot() {
		return this.rootReport;
	}*/

	/* (non-Javadoc)
	 * @see org.sri.nodeservice.core.nodemgr.nodedef.problem.IValidationCallback#pushRichObject(org.sri.nodeservice.core.nodemgr.node.RichObject)
	 */
	/*@Override
	public void pushRichObject(RichObject richObject) {
		this.stack.peek().pushRichObject(richObject);
	}*/

	/*@Override
	public void beginNodeValidation(NodeDef nodeDef, INode node) {
		/*DefaultValidationReport parentReport = null;
		if (this.stack.size() > 0) {
			parentReport = this.stack.peek();
		}
		DefaultValidationReport report = new DefaultValidationReport(node, parentReport);
		this.stack.push(report);
		if (this.rootReport == null) {
			this.rootReport = report;
		}*
	}*/
/*
	@Override
	public void beginAttrValidation(AttrDef curAttrDef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endAttrValidation(AttrDef curAttrDef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginNodeSetValidation(NodeSetDef curChildSetDef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endNodeSetValidation(NodeSetDef curChildSetDef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endNodeValidation(NodeDef nodeDef, INode node) {
		IValidationReport report = this.stack.pop();
		if (!report.getNodeId().equals(node.getNodeId())) {
			throw new RuntimeException("Assertion error, stack usage not valid.");
		}
	}

	@Override
	public void addProblem(INode node, String problemId, ProblemSeverity severity, String message, String detailMessage) {
		Problem prop = new Problem(severity, problemId, message, detailMessage);
		this.stack.peek().registerProblem(prop);
	}

	@Override
	public IValidationReport getRoot() {
		return this.rootReport;
	}

	//@Override
	public void addProblem(String problemId, ProblemSeverity severity, String message, String detailMessage) {
		Problem prop = new Problem(severity, problemId, message, detailMessage);
		this.stack.peek().registerProblem(prop);
	}*/
}
