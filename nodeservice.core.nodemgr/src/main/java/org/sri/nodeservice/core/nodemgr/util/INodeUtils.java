package org.sri.nodeservice.core.nodemgr.util;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;

/**
 * 
 * @author sisaac
 */
public class INodeUtils {

	public static int getIntAttrValue(INode node, String id, boolean create) {
		INodeAttr attr = node.getAttr(id);
		if (attr == null && create) {
			attr = node.createAttr(id, "0");
		}
		return Integer.valueOf(attr.getValue());
	}

	public static void setIntAttrValue(INode node, String attrId, int i, boolean create) {
		INodeAttr attr = node.getAttr(attrId);
		if (attr == null && create) {
			attr = node.createAttr(attrId, null);
		}
		attr.setValue(String.valueOf(i));
	}

	public static INodeAttr getAttr(INode node, String id, boolean create) {
		INodeAttr attr = node.getAttr(id);
		if (attr == null && create) {
			attr = node.createAttr(id, null);
		}
		return attr;
	}

	public static INodeSet getOrCreateNodeSet(INode parentNode, String nodeSetName) {
		INodeSet rNodeSet = parentNode.getChildNodeSet(nodeSetName);
		if (rNodeSet == null) {
			rNodeSet = parentNode.createChildNodeSet(nodeSetName);
		}
		return rNodeSet;
	}

	public static INode getOrCreateRootNode(INodeStore nodeStore, String nodeId, String nodeDefId) {
		INode rNode = null;

		rNode = nodeStore.getRootNodeById(nodeId);
		if (rNode == null) {
			rNode = nodeStore.createRootNode(nodeId, nodeDefId);
		} else if (!nodeDefId.equals(rNode.getNodeDefId())) {
			throw new RuntimeException("Expected nodeDefId [" + nodeDefId + "] got [" + rNode.getNodeDefId() + "]");
		}

		return rNode;
	}

	public static INode getOrCreateChildNode(INode parentNode, String childSetName, String childNodeId, String childNodeDefId) {
		INode rNode = null;

		INodeSet childNodeSet = INodeUtils.getOrCreateNodeSet(parentNode, childSetName);
		rNode = childNodeSet.getNodeForNodeId(childNodeId);
		if (rNode == null) {
			rNode = childNodeSet.createChildNode(childNodeId, childNodeDefId);
		}

		return rNode;
	}

	public static void incrementIntAttr(INode node, String attrId, int i, boolean create) {
		INodeAttr attr = node.getAttr(attrId);
		if (attr == null && create) {
			attr = node.createAttr(attrId, String.valueOf(0));
		} else if (attr == null) {
			throw new RuntimeException("Cannot increment non-existent attribute");
		}
		int currentValue = INodeUtils.getIntAttrValue(node, attrId, false);
		currentValue++;
		INodeUtils.setIntAttrValue(node, attrId, currentValue, false);
	}

	public static boolean getBooleanAttrValue(INode node, String id, boolean create) {
		INodeAttr attr = node.getAttr(id);
		if (attr == null && create) {
			attr = node.createAttr(id, "false");
		}
		return Boolean.valueOf(attr.getValue());
	}
}
