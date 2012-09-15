/**
 * 
 */
package org.sri.nodeservice.core.nodemgr.mongo.node;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeFactory;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;

import com.mongodb.DBCollection;

/**
 * @author sisaac
 * 
 */
public class MongoNodeFactory implements INodeFactory {

	private DBCollection dbCollection;

	public MongoNodeFactory(DBCollection dbCollection) {
		super();
		this.dbCollection = dbCollection;
	}

	@Override
	public INode createNode(String nodeId, NodeDef nodeDef) {
		MongoNode rNode = new MongoNode(this.dbCollection, nodeId, nodeDef.getNodeDefId());
		//this.dbCollection.insert(rNode.getMongoObject());
		return rNode;
	}

	@Override
	public INodeAttr createAttr(AttrDef attrDef, String value) {
		MongoAttr newAttr = new MongoAttr(attrDef.getId(), value);
		return newAttr;
	}

	@Override
	public INode createNode(String nodeId, String nodeDefId) {
		return this.createNode(nodeId, nodeDefId);
	}

	@Override
	public INodeAttr createAttr(String nodeDefId, String attrDefId, String value) {
		MongoAttr newAttr = new MongoAttr(attrDefId, value);
		return newAttr;
	}
}
