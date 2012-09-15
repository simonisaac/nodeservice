package org.sri.nodeservice.core.nodemgr.defaultimpl.node;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;

/**
 * @author sisaac
 *
 */
public class DefaultNodeSet implements INodeSet {

	private String nodeSetDefId;

	private DefaultNode parentNode;

	private DefaultNodeStore nodeStore;

	private LinkedHashMap<String, INode> nodes = new LinkedHashMap<String, INode>();

	private List<INode> nodeList = new ArrayList<INode>();

	//-------------------------------------------------------------------------
	//
	//

	public DefaultNodeSet(DefaultNodeStore nodeStore, DefaultNode parentNode, String nodeSetDefId) {
		this.nodeStore = nodeStore;
		this.parentNode = parentNode;
		this.nodeSetDefId = nodeSetDefId;
	}

	//-------------------------------------------------------------------------
	//
	//

	@Override
	public String getId() {
		return this.nodeSetDefId;
	}

	@Override
	public INodeIterator getINodeIterator() {
		return new DefaultNodeIterator(this.nodeList.iterator());
	}

	@Override
	public void addChildNode(INode childNode) {
		this.nodes.put(childNode.getNodeId(), childNode);
		this.nodeList.add(childNode);
	}

	@Override
	public INode removeChildNode(String string) {
		INode rNode = this.nodes.remove(string);
		this.nodeStore.removeNode(rNode);
		this.nodeList.remove(rNode);
		return rNode;
	}

	@Override
	public INode getNodeForNodeId(String nodeId) {
		return this.nodes.get(nodeId);
	}

	@Override
	public int childCount() {
		return this.nodes.size();
	}

	@Override
	public INode createChildNode(String id, String nodeDefId) {
		INode childNode = new DefaultNode(this.nodeStore, this.parentNode, this, this.nodeStore.getNextUID(), id, nodeDefId);
		this.nodes.put(id, childNode);
		this.nodeList.add(childNode);
		return childNode;
	}

	@Override
	public INode getChildNodeAt(int index) {
		return this.nodeList.get(index);
	}
}
