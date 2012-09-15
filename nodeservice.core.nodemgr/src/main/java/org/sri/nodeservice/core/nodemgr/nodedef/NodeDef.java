package org.sri.nodeservice.core.nodemgr.nodedef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeDef;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationProblemReporter;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

/**
 * 
 * @author sisaac
 * 
 */
public class NodeDef {

	private String id;

	private Map<String, AttrDef> attrDefMap = new HashMap<String, AttrDef>();

	private List<AttrDef> attrDefs = new ArrayList<AttrDef>();

	private Map<String, NodeSetDef> childDefMap = new HashMap<String, NodeSetDef>();

	//private List<NodeRule> nodeRules = new ArrayList<NodeRule>();

	private NodeDefMgr nodeDefMgr;

	public static final String NODE_REPORT = "NODE_REPORT";
	public static final String ATTR_REPORT = "ATTR_REPORT";
	public static final String NODESET_REPORT = "NODESET_REPORT";

	//-------------------------------------------------------------------------
	//
	//

	public NodeDef(String type) {
		if (type == null) {
			throw new RuntimeException("Node type must be non-null");
		}
		this.id = type;
	}

	//-------------------------------------------------------------------------
	//
	//

	/*public void validate(Node node, IValidationCallback builder) {
		builder.beginValidation(NODE_REPORT, node.getId(), node);

		// create and add the rich object.
		RichNode richNode = new RichNode(node.getId(), this);
		builder.pushRichObject(richNode);
		
		// check the type!!
		if (node.getType() == null || !node.getType().equals(this.id)) {
			String message = "Expected node type [" + this.id + "] found [" + node.getType() + "]";
			builder.addProblem("NODE_STRUCTURE", ProblemSeverity.ERROR, message, message);

			return;
		}

		// validate this node's attributes.
		for (AttrDef curAttrDef : this.attrDefs) {
			builder.beginValidation(ATTR_REPORT, curAttrDef.getId(), curAttrDef);

			String stringAttrValue = node.getFields().get(curAttrDef.getId());
			if (stringAttrValue != null) {
				curAttrDef.validateStringValue(stringAttrValue, builder);
			} else if (curAttrDef.isMandatory()) {
				String message = "Mandatory attribute [" + curAttrDef.getId() + "] not present";
				builder.addProblem("MISSING_ATTR", ProblemSeverity.ERROR, message, message);
			} else {
				String message = "Attribute [" + curAttrDef.getId() + "] not present";
				builder.addProblem("MISSING_ATTR", ProblemSeverity.INFO, message, message);
			}

			builder.endValidation(ATTR_REPORT, curAttrDef.getId());
		}

		// validate child sets
		for (NodeSetDef curChildSetDef : this.childDefMap.values()) {
			builder.beginValidation(NODESET_REPORT, curChildSetDef.getId(), curChildSetDef);
			
			// create and add the rich object.
			RichNodeSet richNodeSet = new RichNodeSet(curChildSetDef);
			builder.pushRichObject(richNodeSet);

			NodeSet curChildSet = node.getChildNodeSet(curChildSetDef.getId());
			if (curChildSet == null) {
				// there is no child set, this is an error if min occurrences is more than 0.
				if (curChildSetDef.getMinOccurances() > 0) {
					String message = "child set [" + curChildSetDef.getId() + "] must have at least " + curChildSetDef.getMinOccurances()
							+ " occurances.";
					builder.addProblem("NODE_STRUCTURE", ProblemSeverity.ERROR, message, message);
				}
			} else {
				if (curChildSetDef.getMinOccurances() > curChildSet.getNodes().size()) {
					String message = "child set [" + curChildSetDef.getId() + "] has [" + curChildSet.getNodes().size()
							+ "] ocurrances, minimum is  " + curChildSetDef.getMinOccurances() + " occurances.";
					builder.addProblem("NODE_STRUCTURE", ProblemSeverity.ERROR, message, message);
				}

				// got acceptable child set, get the def for the child type
				NodeDef childTypeDef = this.nodeDefMgr.getNodeDefinition(curChildSetDef.getNodeType());

				// check each child against the child node def.
				for (Node childNode : curChildSet.getNodes().values()) {
					childTypeDef.validate(childNode, builder);
				}
			}

			builder.endValidation(NODESET_REPORT, curChildSetDef.getId());
		}

		// now apply any node level rules.
		for (NodeRule curRule : this.nodeRules) {
			curRule.validate(richNode, builder);
		}
		
		builder.endValidation(NODE_REPORT, node.getId());
	}*/

	//public Node getDefaultNode(String id) {
	//	return this.getDefaultNode(id, true);
	//}

	public Object getObjectForAttrValue(INode node, String attrId) throws ValidationException {
		Object rObject = null;

		// if Attr is null then just return null
		final INodeAttr attr = node.getAttr(attrId);
		if (attr == null || attr.getValue() == null) {
			return null;
		}

		AttrDef attrDef = this.getAttrDefById(attrId);
		if (attrDef != null) {
			TypeDef typeDef = attrDef.getType();
			try {
				rObject = typeDef.validateStringValue(attr.getValue(), new IValidationProblemReporter() {
					@Override
					public void registerProblem(ProblemSeverity error, String string, String message, String detail) {
						throw new RuntimeException("Invalid value for Attr [" + attr.getId() + "] " + message);
					}
				});
			} catch (RuntimeException re) {
				throw new ValidationException(re);
			}
		} else {
			// throw exception, no AttrDef
			throw new ValidationException("No attribute definition for id [" + attrId + "]");
		}

		return rObject;
	}

	public INode getDefaultINode(INodeSet nodeFactory, String id, boolean createChildren) {
		INode rNode = nodeFactory.createChildNode(id, this.getNodeDefId());//Node(id, this);
		for (AttrDef curAttrDef : this.attrDefs) {
			rNode.createAttr(curAttrDef.getId(), curAttrDef.getDefaultValue());
			//rNode.setAttr(newAttr);
		}
		for (NodeSetDef curChildSet : this.childDefMap.values()) {
			// create and add the child node set
			//NodeSet childNodeSet = new NodeSet(curChildSet.getId());
			//rNode.addChildNodeSet(childNodeSet);

			INodeSet childNodeSet = INodeUtils.getOrCreateNodeSet(rNode, curChildSet.getId());//rNode.getChildNodeSet(curChildSet.getId());

			// get the def for the child type.
			NodeDef childNodeDef = this.nodeDefMgr.getNodeDefinition(curChildSet.getNodeType());

			// create the minimum number of children
			int numToCreate = curChildSet.getMinOccurances() > 0 ? curChildSet.getMinOccurances() : 1;
			for (int i = 0; i < numToCreate; i++) {
				//childNodeSet.createChildNode(id + "_" + i, nodeDefId)
				//INode childNode = childNodeDef.getDefaultINode(nodeFactory, id + "_" + i, createChildren);
				//childNodeSet.addChildNode(childNode);
				childNodeDef.getDefaultINode(childNodeSet, id + "_" + i, createChildren);
			}
		}
		return rNode;
	}

	/*@Deprecated
	public Node getDefaultNode(String id, boolean createChildren) {
		Node rNode = new Node(id, this.getId());
		for (AttrDef curAttrDef : this.attrDefs) {
			rNode.setField(curAttrDef.getId(), curAttrDef.getDefaultValue());
		}
		for (NodeSetDef curChildSet : this.childDefMap.values()) {
			// create and add the child node set
			NodeSet childNodeSet = new NodeSet(curChildSet.getId());
			rNode.addChildNodeSet(childNodeSet);

			// get the def for the child type.
			NodeDef childNodeDef = this.nodeDefMgr.getNodeDefinition(curChildSet.getNodeType());

			// create the minimum number of children
			int numToCreate = curChildSet.getMinOccurances() > 0 ? curChildSet.getMinOccurances() : 1;
			for (int i = 0; i < numToCreate; i++) {
				Node childNode = childNodeDef.getDefaultNode(id + "_" + i);
				childNodeSet.addNode(childNode);
			}
		}
		return rNode;
	}

	/*public RichNode getDefaultRichNode(String id) {
		throw new RuntimeException("not implemented");
	}*/

	public void addChildSetDef(NodeSetDef nodeSetDef) {
		this.childDefMap.put(nodeSetDef.getId(), nodeSetDef);
	}

	public void addAttrDef(AttrDef attrDef) {
		this.attrDefMap.put(attrDef.getId(), attrDef);
		this.attrDefs.add(attrDef);
	}

	public List<AttrDef> getAttrDefList() {
		return Collections.unmodifiableList(this.attrDefs);
	}

	public Collection<NodeSetDef> getChildSetCollection() {
		return Collections.unmodifiableCollection(this.childDefMap.values());
	}

	public String getNodeDefId() {
		return id;
	}

	public AttrDef getAttrDefById(String key) {
		return this.attrDefMap.get(key);
	}

	public NodeDefMgr getNodeDefMgr() {
		return nodeDefMgr;
	}

	public void setNodeDefMgr(NodeDefMgr nodeDefMgr) {
		this.nodeDefMgr = nodeDefMgr;
	}

	public NodeSetDef getChildNodeSetDef(String id2) {
		return this.childDefMap.get(id2);
	}
}
