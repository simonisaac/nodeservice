package org.sri.nodeservice.core.nodemgr.util;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;

public class INodeStoreUtils {

	public static INode getFirstInstanceOfType(INodeStore nodeStore, String sheetNodeType) {
		INode rNode = null;
		Node query = new Node(null, null, sheetNodeType);
		INodeIterator itr = nodeStore.find(query);
		if (itr.hasNext()) {
			rNode = itr.next();
		}
		return rNode;
	}

}
