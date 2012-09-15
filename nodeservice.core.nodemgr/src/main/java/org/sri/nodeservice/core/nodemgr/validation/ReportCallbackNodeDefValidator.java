package org.sri.nodeservice.core.nodemgr.validation;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

/**
 * 
 * @author sisaac
 *
 */
public class ReportCallbackNodeDefValidator implements ReportCallback {

	@Override
	public void beginNode(IValidationReport report, NodeDef nodeDef, INode node) {
		// check the type!!
		if (node.getNodeDefId() == null || !node.getNodeDefId().equals(nodeDef.getNodeDefId())) {
			String message = "Expected node type [" + nodeDef.getNodeDefId() + "] found [" + node.getNodeDefId() + "]";
			report.registerProblem(ProblemSeverity.ERROR, "NODE_STRUCTURE", message, message);
			return;
		}
	}

	@Override
	public void endNode(IValidationReport report, NodeDef nodeDef, INode node) {
	}

	@Override
	public void endNodeSet(IValidationReport report, NodeSetDef curChildSetDef, INodeSet curChildSet) {
		// check that there are at least minimum occurances
		int childCounter = 0;
		if (curChildSet != null){
			childCounter = curChildSet.childCount();
		}
		if (curChildSetDef.getMinOccurances() > 0 && childCounter < curChildSetDef.getMinOccurances()) {
			String message = "child set [" + curChildSetDef.getId() + "] has [" + childCounter + "] ocurrances, minimum is  "
					+ curChildSetDef.getMinOccurances() + " occurances.";
			//Problem problem = new Problem(ProblemSeverity.ERROR, "NODE_STRUCTURE", message, message);
			report.registerProblem(ProblemSeverity.ERROR, "NODE_STRUCTURE", message, message);
		}
		// check that there are not more than max occurances
		if (curChildSetDef.getMaxOccurances() > 0 && childCounter > curChildSetDef.getMaxOccurances()) {
			String message = "child set [" + curChildSetDef.getId() + "] has [" + childCounter + "] ocurrances, maximum is  "
					+ curChildSetDef.getMaxOccurances() + " occurances.";
			//Problem problem = new Problem(ProblemSeverity.ERROR, "NODE_STRUCTURE", message, message);
			report.registerProblem(ProblemSeverity.ERROR, "NODE_STRUCTURE", message, message);
		}
	}

	@Override
	public void beginNodeSet(IValidationReport report, NodeSetDef curChildSetDef, INodeSet curChildSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endAttr(IValidationReport report, AttrDef curAttrDef, INodeAttr curAttr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginAttr(IValidationReport report, AttrDef curAttrDef, INodeAttr curAttr) {
		String stringAttrValue = null;
		if (curAttr != null) {
			stringAttrValue = curAttr.getValue();
		}

		if (stringAttrValue != null && !"".equals(stringAttrValue)) {
			// first check the underlying type.
			Object objectValue = curAttrDef.getType().validateStringValue(stringAttrValue, report);

			// now check this attributes constraints.
			for (TypeConstraintDef curConstraint : curAttrDef.getTypeConstraintList()) {
				curConstraint.apply(objectValue, report);
			}

			//curAttrDef.validateStringValue(stringAttrValue, this);
		} else if (curAttrDef.isMandatory()) {
			String message = "Mandatory attribute [" + curAttrDef.getId() + "] not present";
			//Problem problem = new Problem(ProblemSeverity.ERROR, "MISSING_ATTR", message, message);
			report.registerProblem(ProblemSeverity.ERROR, "MISSING_ATTR", message, message);

			//tmp.addProblem("MISSING_ATTR", ProblemSeverity.ERROR, message, message);
		} else {
			String message = "Attribute [" + curAttrDef.getId() + "] not present";
			//tmp.addProblem("MISSING_ATTR", ProblemSeverity.INFO, message, message);
			//Problem problem = new Problem(ProblemSeverity.INFO, "MISSING_ATTR", message, message);
			report.registerProblem(ProblemSeverity.INFO, "MISSING_ATTR", message, message);
		}

	}

}
