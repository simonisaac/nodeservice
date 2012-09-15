package org.sri.nodeservice.core.nodemgr.util.transform;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;

public class INodeToNodeTransformer {

	public Node transform (INode iNode, int childDepth) {
		Node rNode = new Node(iNode.getUID(), iNode.getNodeId(), iNode.getNodeDefId());
		
		assert childDepth == 0 : "childDepth of more than 0 not supported yet.";
		
		for (INodeAttr curAttr : iNode.getAttrIterator()) {
			rNode.setField(curAttr.getId(), curAttr.getValue());
		}
		
		return rNode;
	}
}
