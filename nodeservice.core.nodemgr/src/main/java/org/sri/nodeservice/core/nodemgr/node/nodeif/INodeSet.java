package org.sri.nodeservice.core.nodemgr.node.nodeif;

public interface INodeSet {

	public String getId();

	public INodeIterator getINodeIterator();

	@Deprecated
	public void addChildNode(INode childNode);

	public INode removeChildNode(String string);

	public abstract INode getNodeForNodeId(String nodeId);
	
	public INode createChildNode (String id, String nodeDefId);

	public int childCount();

	public INode getChildNodeAt(int index);
}
