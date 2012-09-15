package org.sri.nodeservice.core.nodemgr.mongo.util;

import org.sri.nodeservice.core.nodemgr.mongo.node.MongoNode;
import org.sri.nodeservice.core.nodemgr.mongo.node.MongoNodeFactory;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeFactory;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReportFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public class MongoTstFixture extends BaseTstFixture {

	private TstFixtureMongo mongoTstFix = new TstFixtureMongo();

	@Override
	public INodeFactory getNodeFactory() {
		return new MongoNodeFactory(this.mongoTstFix.getColl());
	}

	@Override
	public INode retrieveNode() {
		// retrieve the node
		BasicDBObject query = new BasicDBObject();
		query.put("nodeId", "1");
		DBCursor cursor = this.mongoTstFix.getColl().find(query);
		BasicDBObject parent = (BasicDBObject) cursor.next();
		assert parent != null;
		MongoNode simpleNode = new MongoNode(this.mongoTstFix.getColl(), parent);
		return simpleNode;
	}

	@Override
	public INode generateDefaultNode() {
		MongoNode simpleNode = (MongoNode) this.getNodeDef().getDefaultINode(new MongoNodeFactory(this.mongoTstFix.getColl()), "1", true);
		return simpleNode;
	}

	@Override
	public IValidationReportFactory getValidationReportFactory() {
		// TODO Auto-generated method stub
		return null;
	}
}
