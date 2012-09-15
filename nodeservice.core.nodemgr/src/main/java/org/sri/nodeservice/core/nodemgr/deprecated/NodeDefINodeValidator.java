package org.sri.nodeservice.core.nodemgr.deprecated;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

@Deprecated
public class NodeDefINodeValidator {

	private NodeDefMgr nodeDefMgr;

	public NodeDefINodeValidator(NodeDefMgr nodeDefMgr) {
		super();
		this.nodeDefMgr = nodeDefMgr;
	}

	public void validate(INode node, NodeDef nodeDef, IValidationCallback builder) {
		builder.beginNodeValidation(nodeDef, node);

		// check the type!!
		if (node.getNodeDefId() == null || !node.getNodeDefId().equals(nodeDef.getNodeDefId())) {
			String message = "Expected node type [" + nodeDef.getNodeDefId() + "] found [" + node.getNodeDefId() + "]";
			builder.addProblem(node, "NODE_STRUCTURE", ProblemSeverity.ERROR, message, message);

			// invalid node type - give up.
			return;
		}

		// validate this node's attributes.
		for (AttrDef curAttrDef : nodeDef.getAttrDefList()) {
			builder.beginAttrValidation(curAttrDef);

			//String stringAttrValue = node.getFieldByName(curAttrDef.getId());
			INodeAttr curAttr = node.getAttr(curAttrDef.getId());
			String stringAttrValue = null;
			if (curAttr != null) {
				stringAttrValue = curAttr.getValue();
			}

			if (stringAttrValue != null) {
				//curAttrDef.validateStringValue(stringAttrValue, builder);
			} else if (curAttrDef.isMandatory()) {
				String message = "Mandatory attribute [" + curAttrDef.getId() + "] not present";
				builder.addProblem(node, "MISSING_ATTR", ProblemSeverity.ERROR, message, message);
			} else {
				String message = "Attribute [" + curAttrDef.getId() + "] not present";
				builder.addProblem(node, "MISSING_ATTR", ProblemSeverity.INFO, message, message);
			}

			builder.endAttrValidation(curAttrDef);
		}

		// validate child sets
		for (NodeSetDef curChildSetDef : nodeDef.getChildSetCollection()) {
			builder.beginNodeSetValidation(curChildSetDef);
			int childCounter = 0;

			// create and add the rich object.
			//RichNodeSet richNodeSet = new RichNodeSet(curChildSetDef);
			//builder.pushRichObject(richNodeSet);

			INodeSet curChildSet = node.getChildNodeSet(curChildSetDef.getId());
			if (curChildSet != null) {
				// got acceptable child set, get the def for the child type
				NodeDef childTypeDef = this.nodeDefMgr.getNodeDefinition(curChildSetDef.getNodeType());

				// check each child against the child node def.
				for (INode childNode : curChildSet.getINodeIterator()) {
					childCounter++;
					this.validate(childNode, childTypeDef, builder);
				}
			}

			// check that there are at least minimum occurances
			if (curChildSetDef.getMinOccurances() > 0 && childCounter < curChildSetDef.getMinOccurances()) {
				String message = "child set [" + curChildSetDef.getId() + "] has [" + childCounter + "] ocurrances, minimum is  "
						+ curChildSetDef.getMinOccurances() + " occurances.";
				builder.addProblem(node, "NODE_STRUCTURE", ProblemSeverity.ERROR, message, message);
			}
			// check that there are not more than max occurances
			if (curChildSetDef.getMaxOccurances() > 0 && childCounter > curChildSetDef.getMaxOccurances()) {
				String message = "child set [" + curChildSetDef.getId() + "] has [" + childCounter + "] ocurrances, maximum is  "
						+ curChildSetDef.getMaxOccurances() + " occurances.";
				builder.addProblem(node, "NODE_STRUCTURE", ProblemSeverity.ERROR, message, message);
			}

			builder.endNodeSetValidation(curChildSetDef);
		}

		builder.endNodeValidation(nodeDef, node);
	}
}
