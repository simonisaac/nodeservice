package org.sri.nodeservice.core.nodemgr.util;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;

/**
 * 
 * @author sisaac
 *
 */
public class NodeDefFirstIterator {

	private NodeDefMgr nodeDefMgr;

	public NodeDefFirstIterator(NodeDefMgr nodeDefMgr) {
		super();
		this.nodeDefMgr = nodeDefMgr;
	}

	public void iterate(INode node, NodeDef nodeDef, NodeDefFirstIteratorCallback callback) {

		callback.beginNode(nodeDef, node);

		// iterate this node's attributes.
		for (AttrDef curAttrDef : nodeDef.getAttrDefList()) {

			//String stringAttrValue = node.getFieldByName(curAttrDef.getId());
			INodeAttr curAttr = node.getAttr(curAttrDef.getId());

			callback.beginAttr(curAttrDef, curAttr);

			callback.endAttr(curAttrDef, curAttr);
		}

		// iterate child sets
		for (NodeSetDef curChildSetDef : nodeDef.getChildSetCollection()) {

			INodeSet curChildSet = node.getChildNodeSet(curChildSetDef.getId());

			callback.beginNodeSet(curChildSetDef, curChildSet);

			if (curChildSet != null) {
				// got acceptable child set, get the def for the child type
				NodeDef childTypeDef = this.nodeDefMgr.getNodeDefinition(curChildSetDef.getNodeType());

				// check each child against the child node def.
				for (INode childNode : curChildSet.getINodeIterator()) {
					this.iterate(childNode, childTypeDef, callback);
				}
			}
			
			callback.endNodeSet(curChildSetDef, curChildSet);
		}

		callback.endNode(nodeDef, node);
	}
}
