package org.sri.nodeservice.core.nodemgr.node.nodeif;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;

public interface INodeStore {

	INode getNodeByGUID(String guidID);

	INode getRootNodeById(String id);

	INode createRootNode(String nodeId, String nodeDefId);

	INodeIterator find(Node query);

	//INode getNode(String jobId);

}
