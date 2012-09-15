package org.sri.nodeservice.core.nodemgr.mongo.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.sri.nodeservice.core.nodemgr.mongo.util.MongoTstFixture;
import org.sri.nodeservice.core.nodemgr.mongo.util.TstFixtureMongo;
import org.sri.nodeservice.core.nodemgr.mongo.util.TstLanguageDefReader;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public class MongoNodeTest {

	@Before
	public void setUp() throws Exception {
	}

	@org.junit.Test
	public void TestMongo() {
		TstFixtureMongo mongoFixture = new TstFixtureMongo();

		BasicDBObject mo = new BasicDBObject();
		mongoFixture.getColl().insert(mo);

		BasicDBObject query = new BasicDBObject();
		//query.put("parentNodeSet", "childSet1");
		//query.put("parentNode", mNode.getMongoObject().get("_id"));
		query.put("we", "we");

		BasicDBObject mo2 = new BasicDBObject();
		mo2.put("we", "we");
		mongoFixture.getColl().insert(mo2);
		mo2.put("test", "test");
		mongoFixture.getColl().update(query, mo2);
		mo2.remove("test");
		mongoFixture.getColl().update(query, mo2);

		mo.put("mo2", mo2);

		System.out.println(mo.get("_id"));
		System.out.println(mo2.get("_id"));

		//BasicDBObject query = new BasicDBObject();
		//query.put("parentNodeSet", "childSet1");
		//query.put("parentNode", mNode.getMongoObject().get("_id"));
		query.put("we", "we");
		DBCursor results = mongoFixture.getColl().find(query);
		System.out.println(results.count());
		BasicDBObject obj = (BasicDBObject) results.next();
		System.out.println(obj);
	}

	@org.junit.Test
	public void TestMongoOID() {
		TstFixtureMongo mongoFixture = new TstFixtureMongo();

		{
			BasicDBObject mo = new BasicDBObject();
			mo.put("id", "123");
			mongoFixture.getColl().insert(mo);

			BasicDBObject mo2 = new BasicDBObject();
			mo2.put("parentId", mo.get("_id"));
			mongoFixture.getColl().insert(mo2);
		}
		{
			BasicDBObject query1 = new BasicDBObject();
			query1.put("id", "123");
			DBCursor results = mongoFixture.getColl().find(query1);
			System.out.println(results.count());
			BasicDBObject mo = (BasicDBObject) results.next();
			System.out.println(mo);
			
			BasicDBObject query2 = new BasicDBObject();
			query2.put("parentId", mo.get("_id"));
			DBCursor results2 = mongoFixture.getColl().find(query2);
			System.out.println(results2.count());
			BasicDBObject obj = (BasicDBObject) results2.next();
			System.out.println(obj);
		}
	}

	@org.junit.Test
	public void TestSimpleNode() {

		TstFixtureMongo mongoFixture = new TstFixtureMongo();
		MongoNodeFactory nodeFactory = new MongoNodeFactory(mongoFixture.getColl());

		{
			MongoNode mNode = (MongoNode) nodeFactory.createNode("123", new NodeDef("aNodetype"));
			MongoNode childMNode = (MongoNode) nodeFactory.createNode("456", new NodeDef("childType"));
			MongoNodeSet childSet1 = (MongoNodeSet) mNode.getChildNodeSet("childSet1");
			childSet1.addChildNode(childMNode);
		}
		{
			// retrieve the parent
			BasicDBObject query = new BasicDBObject();
			query.put("nodeId", "123");
			DBCursor cursor = mongoFixture.getColl().find(query);
			BasicDBObject parent = (BasicDBObject) cursor.next();
			assertNotNull(parent);
			MongoNode mongoParent = new MongoNode(mongoFixture.getColl(), parent);
			assertEquals(mongoParent.getNodeId(), "123");
			assertEquals(mongoParent.getNodeDefId(), "aNodetype");

			// retrive the childset
			MongoNodeSet childSet = (MongoNodeSet) mongoParent.getChildNodeSet("childSet1");
			assertNotNull(childSet);
			INodeIterator childItr = childSet.getINodeIterator();
			MongoNode childNode = (MongoNode) childItr.next();
			assertNotNull(childNode);
			assertEquals(childNode.getNodeId(), "456");
			assertEquals(childNode.getNodeDefId(), "childType");
		}
	}

	@org.junit.Test
	public void TestSimpleLanguage() {

		TstFixtureMongo mongoFixture = new TstFixtureMongo();
		{
			BaseTstFixture fix = new MongoTstFixture();
			fix.init(TstLanguageDefReader.SIMPLE_NODE_DEF);
			//TstHierachyNodeDefHelper helper = new TstHierachyNodeDefHelper();
			//helper.TestDefaultValid(fix);
			//ITstFixture fix = new MongoTstFixture(TstLanguageDefReader.SIMPLE_NODE_DEF);
			NodeDef simpleNodeDef = fix.getNodeDef();
			MongoNode simpleNode = (MongoNode) simpleNodeDef.getDefaultINode(new MongoNodeFactory(mongoFixture.getColl()), "1", true);
		}
		{
			// retrieve the node
			BasicDBObject query = new BasicDBObject();
			query.put("nodeId", "1");
			DBCursor cursor = mongoFixture.getColl().find(query);
			BasicDBObject parent = (BasicDBObject) cursor.next();
			assertNotNull(parent);
			MongoNode simpleNode = new MongoNode(mongoFixture.getColl(), parent);

			INodeAttr attr = simpleNode.getAttr("string");
			assertEquals("A String Value", attr.getValue());

			attr = simpleNode.getAttr("stringMin10");
			assertEquals("0123456789", attr.getValue());

			attr = simpleNode.getAttr("stringMax20");
			assertEquals("01234567890123456789", attr.getValue());

			attr = simpleNode.getAttr("stringExMin5Max10");
			assertEquals("012345678", attr.getValue());
		}
	}

	@org.junit.Test
	public void TestHierachicalLanguage() {

		TstFixtureMongo mongoFixture = new TstFixtureMongo();
		{
			BaseTstFixture fix = new MongoTstFixture();
			fix.init(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
			//ITstFixture fix = new MongoTstFixture(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
			NodeDef simpleNodeDef = fix.getNodeDef();
			MongoNode simpleNode = (MongoNode) simpleNodeDef.getDefaultINode(new MongoNodeFactory(mongoFixture.getColl()), "123", true);
		}
		{
			// retrieve the parent
			BasicDBObject query = new BasicDBObject();
			query.put("nodeId", "123");
			DBCursor cursor = mongoFixture.getColl().find(query);
			BasicDBObject parent = (BasicDBObject) cursor.next();
			assertNotNull(parent);
			MongoNode mongoParent = new MongoNode(mongoFixture.getColl(), parent);

			// check parent
			assertEquals(mongoParent.getNodeId(), "123");
			assertEquals(mongoParent.getNodeDefId(), "simpleHierachyDef");
			INodeAttr attr = mongoParent.getAttr("string");
			assertEquals("A String Value", attr.getValue());

			// do some checks
			BasicDBObject checkquery = new BasicDBObject();
			query.put("parentId", mongoParent.getMongoObject().get("_id"));
			query.put("parentChildSet", "childnodes");
			DBCursor checkcursor = mongoFixture.getColl().find(checkquery);
			BasicDBObject check1 = (BasicDBObject) checkcursor.next();
			System.out.println(checkcursor.count());
			System.out.println(check1);
			assertNotNull(check1);
			//MongoNode mongoParent = new MongoNode(mongoFixture.getColl(), parent);
			
			// get the child
			MongoNodeSet childSet = (MongoNodeSet) mongoParent.getChildNodeSet("childnodes");
			MongoNodeIterator childItr = (MongoNodeIterator) childSet.getINodeIterator();
			assertTrue(childItr.hasNext());
			MongoNode childNode = (MongoNode) childItr.next();

			attr = childNode.getAttr("string");
			assertEquals("A String Value", attr.getValue());
		}
	}
}
