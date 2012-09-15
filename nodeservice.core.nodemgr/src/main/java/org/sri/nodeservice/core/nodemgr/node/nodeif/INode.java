package org.sri.nodeservice.core.nodemgr.node.nodeif;

public interface INode {

	public String getUID();
	
	public String getNodeId();

	public String getNodeDefId();

	//public String getParentNodeId();

	//@Deprecated
	//String getFieldByName(String id);
	
	public INodeStore getNodeStore ();

	public INodeAttr getAttr(String attrId);

	public INodeAttrIterator getAttrIterator();

	public INodeAttr createAttr(String attrId, String value);

	//public void setAttr(INodeAttr attr);

	public INodeSet getChildNodeSet(String childNodeSetId);

	public INodeSet createChildNodeSet(String childNodeSetId);

	public INodeAttr removeAttr(String attrId);

	public INode getParentNode();

	public void remove();

	// removed.  not sure that you should be able to set node def after creation?
	//public void setNodeDefId(String nodeDefId);

}
