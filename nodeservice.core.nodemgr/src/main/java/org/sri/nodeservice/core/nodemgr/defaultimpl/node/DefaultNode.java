package org.sri.nodeservice.core.nodemgr.defaultimpl.node;

import java.util.HashMap;
import java.util.Map;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttrIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;

/**
 * 
 * @author sisaac
 *
 */
public class DefaultNode implements INode {

	private String UID;

	private String id;

	private String nodeDefId;

	private DefaultNode parentNode;

	private DefaultNodeSet parentNodeSet;

	private DefaultNodeStore nodeStore;

	private Map<String, INodeAttr> attrs = new HashMap<String, INodeAttr>();

	private Map<String, INodeSet> childSets = new HashMap<String, INodeSet>();

	//-------------------------------------------------------------------------
	//
	//

	DefaultNode(DefaultNodeStore nodeStore, DefaultNode parentNode, DefaultNodeSet paretnNodeSet, String UID, String id, String nodeDefId) {
		if (id == null || "".equals(id)) {
			throw new RuntimeException("Invalid id [" + id + "]");
		}
		this.nodeStore = nodeStore;
		this.parentNode = parentNode;
		this.parentNodeSet = paretnNodeSet;
		this.UID = UID;
		this.id = id;
		this.nodeDefId = nodeDefId;
		this.nodeStore.addNode(this);
	}

	//-------------------------------------------------------------------------
	//
	// Implement INode

	@Override
	public String getNodeId() {
		return id;
	}

	@Override
	public String getNodeDefId() {
		return this.nodeDefId;
	}

	@Override
	public INodeAttr getAttr(String id) {
		return this.attrs.get(id);
	}

	@Override
	public INodeSet getChildNodeSet(String childNodeSetId) {
		INodeSet rNodeSet = this.childSets.get(childNodeSetId);
		/*if (rNodeSet == null) {
			NodeSetDef nodeSetDef = this.nodeDef.getChildNodeSetDef(childNodeSetId);
			assert nodeSetDef != null;
			rNodeSet = new RichNodeSet(this.nodeDef.getChildNodeSetDef(childNodeSetId));
			this.childSets.put(childNodeSetId, rNodeSet);
		}*/
		return rNodeSet;
	}

	@Override
	public INodeAttr removeAttr(String id) {
		return this.attrs.remove(id);
	}

	@Override
	public void remove() {
		this.parentNodeSet.removeChildNode(this.getNodeId());
	}

	@Override
	public INodeAttr createAttr(String attrId, String value) {
		INodeAttr newAttr = null;
		//AttrDef attrDef = null;
		//if (this.nodeDef != null) {
		//	attrDef = this.nodeDef.getAttrDefById(attrId);
		//	newAttr = new RichAttr(attrDef, value);
		//} else {
		newAttr = new DefaultNodeAttr(attrId, value);
		//}
		//this.setAttr(newAttr);
		this.attrs.put(newAttr.getId(), newAttr);
		return newAttr;
	}

	@Override
	public INodeSet createChildNodeSet(String childNodeSetId) {
		INodeSet rNodeSet = this.childSets.get(childNodeSetId);
		if (rNodeSet != null) {
			throw new RuntimeException("ChildNodeSet already created");
		}
		//rNodeSet = new RichNodeSet(this.nodeDef.getChildNodeSetDef(childNodeSetId));
		rNodeSet = new DefaultNodeSet(this.nodeStore, this, childNodeSetId);
		this.childSets.put(childNodeSetId, rNodeSet);

		return rNodeSet;
	}

	@Override
	public INodeAttrIterator getAttrIterator() {
		return new DefaultNodeAttrIterator(this.attrs.values().iterator());
	}

	@Override
	public INode getParentNode() {
		return this.parentNode;
	}

	@Override
	public String getUID() {
		return this.UID;
	}

	@Override
	public INodeStore getNodeStore() {
		return this.nodeStore;
	}
}