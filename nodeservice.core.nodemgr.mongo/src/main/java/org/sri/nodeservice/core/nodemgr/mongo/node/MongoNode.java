package org.sri.nodeservice.core.nodemgr.mongo.node;

import org.apache.commons.lang.NotImplementedException;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

public class MongoNode implements INode {

	private BasicDBObject mongoObject;

	private DBCollection dbCollection;

	MongoNode(DBCollection dbCollection, String nodeId, String nodeType) {
		this.dbCollection = dbCollection;
		this.mongoObject = new BasicDBObject();
		this.mongoObject.put("nodeId", nodeId);
		this.setNodeDefId(nodeType);
		this.dbCollection.insert(this.getMongoObject());
	}

	public MongoNode(DBCollection dbCollection, BasicDBObject mongoObject) {
		this.dbCollection = dbCollection;
		this.mongoObject = mongoObject;
		/*Object mId = this.mongoObject.get("_id");
		String id = this.getId();
		Object type = this.getType();*/
		if (this.mongoObject.get("_id") == null || this.getNodeId() == null || this.getNodeDefId() == null) {
			throw new RuntimeException("Mongo node needs to have id and type set");
		}
	}

	@Override
	public String getNodeId() {
		return this.getMongoObject().getString("nodeId");
	}

	@Override
	public String getNodeDefId() {
		return this.getMongoObject().getString("nodeType");
	}

	@Deprecated
	@Override
	public String getFieldByName(String id) {
		throw new NotImplementedException();
	}

	@Override
	public INodeSet getChildNodeSet(String childSetId) {
		return new MongoNodeSet(this.dbCollection, this.getChildSetMongoObject(childSetId), this);
	}

	public void updateToMongo() {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", this.getMongoObject().get("_id"));
		WriteResult result = this.dbCollection.update(query, this.getMongoObject());
	}

	public BasicDBObject getMongoObject() {
		if (this.mongoObject == null) {
			this.mongoObject = new BasicDBObject();
		}
		return this.mongoObject;
	}

	@Override
	public INodeAttr removeAttr(String id) {
		INodeAttr rAttr = null;
		String removedValue = (String) this.getAttrDBObject().removeField(id);
		this.updateToMongo();
		if (removedValue != null) {
			rAttr = new MongoAttr(id, removedValue);
		}
		return rAttr;
	}

	@Override
	public INodeAttr getAttr(String attrId) {
		String value = this.getAttrDBObject().getString(attrId);
		return new MongoAttr(attrId, value);
	}

	@Override
	public void setAttr(INodeAttr attr) {
		this.getAttrDBObject().put(attr.getId(), attr.getValue());
		this.updateToMongo();
	}

	BasicDBObject getAttrDBObject() {
		BasicDBObject rObject = (BasicDBObject) this.getMongoObject().get("attributes");
		if (rObject == null) {
			rObject = new BasicDBObject();
			this.getMongoObject().put("attributes", rObject);
			this.updateToMongo();
		}
		return rObject;
	}

	private BasicDBObject getChildSetsMongoObject() {
		BasicDBObject rObject = (BasicDBObject) this.getMongoObject().get("childSets");
		if (rObject == null) {
			rObject = new BasicDBObject();
			this.getMongoObject().put("childSets", rObject);
			this.updateToMongo();
		}
		return rObject;
	}

	private BasicDBObject getChildSetMongoObject(String childSetId) {
		BasicDBObject rObject = (BasicDBObject) this.getChildSetsMongoObject().get(childSetId);
		if (rObject == null) {
			rObject = new BasicDBObject();
			rObject.put("childSetId", childSetId);
			this.getMongoObject().put(childSetId, rObject);
			this.updateToMongo();
		}
		return rObject;
	}

	public void setNodeSet(MongoNodeSet mongoNodeSet) {
		this.mongoObject.put("parentNodeSet", mongoNodeSet.getId());
		this.updateToMongo();
	}

	public void setParentNode(MongoNode parentNode) {
		this.mongoObject.put("parentNodeId", parentNode.getMongoObject().get("_id"));
		this.updateToMongo();
	}

	public String getParentNodeId() {
		return this.mongoObject.getString("parentNodeId");
	}

	private void setNodeDefId(String nodeType) {
		this.mongoObject.put("nodeType", nodeType);
		this.updateToMongo();
	}
}
