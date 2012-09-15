package org.sri.nodeservice.core.nodemgr.mongo.node;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class MongoNodeSet implements INodeSet {

	private MongoNode parentNode;

	private DBCollection dbCollection;

	private BasicDBObject mongoObject;

	public MongoNodeSet(DBCollection dbCollection, BasicDBObject mongoObject, MongoNode nodeMongo) {
		this.dbCollection = dbCollection;
		this.mongoObject = mongoObject;
		this.parentNode = nodeMongo;
		assert this.mongoObject.get("childSetId") != null;
	}

	@Override
	public INodeIterator getINodeIterator() {
		BasicDBObject query = new BasicDBObject();
		query.put("parentNodeSet", this.getId());
		query.put("parentNodeId", this.parentNode.getMongoObject().get("_id"));
		DBCursor results = this.dbCollection.find(query);
		INodeIterator rItr = new MongoNodeIterator(this.dbCollection, results);
		return rItr;
	}

	@Override
	public void addChildNode(INode childNode) {
		MongoNode mongoChildNode = (MongoNode) childNode;

		// sort out the parent markers.
		mongoChildNode.setParentNode(this.parentNode);
		mongoChildNode.setNodeSet(this);

		// add the actual mongo node to the node set.
		this.getNodeSetDBObject().put(mongoChildNode.getNodeId(), mongoChildNode.getMongoObject());
		this.parentNode.updateToMongo();
	}

	@Override
	public INode getNodeForNodeId(String nodeId) {
		INode rNode = null;

		BasicDBObject query = new BasicDBObject();
		query.put("parentNodeSet", this.getId());
		query.put("nodeId", nodeId);
		DBCursor results = this.dbCollection.find(query);
		assert results.count() < 2; // id should be unique within childset.

		if (results.hasNext()) {
			rNode = new MongoNode(this.dbCollection, (BasicDBObject)results.next());
		}

		return rNode;
	}

	@Override
	public INode removeChildNode(String string) {
		INode deletedNode = null;
		
		MongoNode nodeToDelete = (MongoNode)this.getNodeForNodeId(string);
		if (nodeToDelete != null) {
			this.dbCollection.remove(nodeToDelete.getMongoObject());
			deletedNode = nodeToDelete;
		}
		
		return deletedNode;
	}

	BasicDBObject getNodeSetDBObject() {
		return this.mongoObject;
	}

	@Override
	public String getId() {
		return this.mongoObject.getString("childSetId");
	}
}
