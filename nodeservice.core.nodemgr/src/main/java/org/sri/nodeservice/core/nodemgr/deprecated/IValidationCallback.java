package org.sri.nodeservice.core.nodemgr.deprecated;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

@Deprecated
public interface IValidationCallback {

	public abstract void addProblem(String problemId, ProblemSeverity severity, String message, String detailMessage);

	//@Deprecated
	//public abstract void endValidation(String entityType, String entityId);

	//@Deprecated
	//public abstract void beginValidation(String entityType, String entityId,
	//		Object node);

	public abstract IValidationReport getRoot();

	//@Deprecated
	//public abstract void pushRichObject(RichObject richObject);

	public abstract void addProblem(INode node, String problemId, ProblemSeverity severity, String message, String detailMessage);

	public abstract void beginNodeValidation(NodeDef nodeDef, INode node);

	public abstract void endNodeValidation(NodeDef nodeDef, INode node);

	public abstract void beginAttrValidation(AttrDef curAttrDef);

	public abstract void endAttrValidation(AttrDef curAttrDef);

	public abstract void beginNodeSetValidation(NodeSetDef curChildSetDef);

	public abstract void endNodeSetValidation(NodeSetDef curChildSetDef);

	//public abstract void addProblem(String constraint, ProblemSeverity error, String message, String message2);

}