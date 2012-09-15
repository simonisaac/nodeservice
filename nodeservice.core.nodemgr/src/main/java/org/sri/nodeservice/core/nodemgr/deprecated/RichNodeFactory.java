package org.sri.nodeservice.core.nodemgr.deprecated;

@Deprecated
public class RichNodeFactory implements INodeFactory {

	/*private NodeDefMgr nodeDefMgr;

	public RichNodeFactory(INodeStore nodeStore, NodeDefMgr nodeDefMgr) {
		super();
		this.nodeDefMgr = nodeDefMgr;
	}

	@Override
	public INode createNode(String nodeId, NodeDef nodeDef) {
		RichNode rNode = new RichNode(nodeId, nodeDef.getId());
		return rNode;
	}

	@Override
	public INodeAttr createAttr(AttrDef attrDef, String value) {
		RichAttr rAttr = new RichAttr(attrDef, value);
		return rAttr;
	}

	@Override
	public INode createNode(String nodeId, String nodeDefId) {
		//NodeDef nodeDef = this.nodeDefMgr.getNodeDefinition(nodeDefId);
		RichNode rNode = new RichNode(nodeId, nodeDefId);
		return rNode;
	}

	@Override
	public INodeAttr createAttr(String nodeDefId, String attrDefId, String value) {
		NodeDef nodeDef = this.nodeDefMgr.getNodeDefinition(nodeDefId);
		if (nodeDef == null) {
			throw new RuntimeException("Invalid nodeDefId [" + nodeDefId + "]");
		}
		AttrDef attrDef = nodeDef.getAttrDefById(attrDefId);
		if (attrDef == null) {
			throw new RuntimeException("Invalid attrDefId [" + nodeDefId + "/" + attrDefId + "]");
		}
		RichAttr rAttr = new RichAttr(attrDef, value);
		return rAttr;
	}

	public NodeDefMgr getNodeDefMgr() {
		return nodeDefMgr;
	}

	public void setNodeDefMgr(NodeDefMgr nodeDefMgr) {
		this.nodeDefMgr = nodeDefMgr;
	}*/
}
