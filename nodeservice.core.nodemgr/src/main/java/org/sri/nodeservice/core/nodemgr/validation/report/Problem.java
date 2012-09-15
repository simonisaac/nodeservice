package org.sri.nodeservice.core.nodemgr.validation.report;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;

/**
 * @author sisaac
 *
 */
public class Problem {
	
	public static final String NODE_STRUCTURE = "NODE_STRUCTURE";
	public static final String ATTR_TYPE = "ATTR_TYPE";
	public static final String CONSTRAINT = "CONSTRAINT";

	private INode problemNode;
	
	public static final String ID = "problemId";
	
	public static final String PROBLEM_SEVERITY = "severity";
	
	public static final String MESSAGE = "message";
	
	public static final String DETAIL = "detail";
	
	//private ProblemSeverity severity;

	//private String id;

	//private String message;

	//private String detail;

	public Problem(INode node) {
		this.problemNode = node;
	}
	
	/*public Problem(ProblemSeverity severity, String id, String message, String detail) {
		this.severity = severity;
		this.id = id;
		this.message = message;
		this.detail = detail;
	}*/

	public String getId() {
		return this.problemNode.getAttr(ID).getValue();
	}

	public void setId(String attrId) {
		INodeUtils.getAttr(this.problemNode, ID, true).setValue(attrId);
	}

	public String getMessage() {
		return this.problemNode.getAttr(MESSAGE).getValue();
	}

	public void setMessage(String message) {
		INodeUtils.getAttr(this.problemNode, MESSAGE, true).setValue(message);
	}

	public String getDetail() {
		return this.problemNode.getAttr(DETAIL).getValue();
	}

	public void setDetail(String detail) {
		INodeUtils.getAttr(this.problemNode, DETAIL, true).setValue(detail);
	}

	public void setSeverity (ProblemSeverity severity) {
		INodeUtils.setIntAttrValue(this.problemNode, PROBLEM_SEVERITY, severity.getLevel(), true);
	}
	
	public ProblemSeverity getSeverity() {
		int level =  INodeUtils.getIntAttrValue(this.problemNode, PROBLEM_SEVERITY, false);
		ProblemSeverity rSeverity = ProblemSeverity.fromLevel(level);
		return rSeverity;
	}

	public static Problem createProblem(INode problemNode,ProblemSeverity severity, String id, String message, String detail) {
		Problem rProblem = new Problem(problemNode);
		rProblem.setId(id);
		rProblem.setSeverity(severity);
		rProblem.setMessage(message);
		rProblem.setDetail(detail);
		return rProblem;
	}
}
