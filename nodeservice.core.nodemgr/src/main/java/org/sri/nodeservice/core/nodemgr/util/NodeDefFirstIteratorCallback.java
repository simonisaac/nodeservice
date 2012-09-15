package org.sri.nodeservice.core.nodemgr.util;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;

/**
 * 
 * @author sisaac
 *
 */
public interface NodeDefFirstIteratorCallback {

	void beginNode(NodeDef nodeDef, INode node);

	void endNode(NodeDef nodeDef, INode node);

	void endNodeSet(NodeSetDef curChildSetDef, INodeSet curChildSet);

	void beginNodeSet(NodeSetDef curChildSetDef, INodeSet curChildSet);

	void endAttr(AttrDef curAttrDef, INodeAttr curAttr);

	void beginAttr(AttrDef curAttrDef, INodeAttr curAttr);
}