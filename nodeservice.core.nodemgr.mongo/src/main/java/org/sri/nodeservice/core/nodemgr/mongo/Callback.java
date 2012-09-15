package org.sri.nodeservice.core.nodemgr.mongo;
import java.util.Map;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Callback implements IProcessSpreadsheetSAXCallback {

	private Mongo mongo;

	private DB db;

	private DBCollection coll;

	public static void main(String[] args) {
		Callback c = new Callback();
		c.read();
	}

	public void init() {
		try {
			this.mongo = new Mongo("localhost", 27017);

			this.db = this.mongo.getDB("mydb");

			this.coll = db.getCollection("testCollection");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void init(DBCollection coll) {
		this.coll = coll;
	}

	public void createRootNode(Node sheetNode) {

		// create the node and add to the root of the collection.
		BasicDBObject node = this.createBasicDBObjectForNode(sheetNode);
		this.coll.insert(node);
	}

	public void createNode(String parentId, String childsetName, Node sheetNode) {

		// get parent node
		BasicDBObject query = new BasicDBObject();
		query.put("id", parentId);
		DBCursor cur = coll.find(query);
		DBObject parent = cur.next();

		// get childset
		BasicDBObject childSets = (BasicDBObject) parent.get("childSets");

		// get childsetName (create if not already present)
		BasicDBObject childSet1 = (BasicDBObject) childSets.get(childsetName);
		if (childSet1 == null) {
			childSet1 = new BasicDBObject();
			childSets.put(childsetName, childSet1);
		}

		// create the node and add to the childset
		BasicDBObject node = this.createBasicDBObjectForNode(sheetNode);
		childSet1.put(sheetNode.getId(), node);
	}

	private BasicDBObject createBasicDBObjectForNode(Node inNode) {
		BasicDBObject node = new BasicDBObject();
		node.put("id", inNode.getId());

		// create a container object for fields and add fields
		BasicDBObject fields = new BasicDBObject();
		node.put("fields", fields);
		for (Map.Entry<String, String> entry : inNode.getFields().entrySet()) {
			fields.put(entry.getKey(), entry.getValue());
		}

		// create and empty object ready for any childsets.
		BasicDBObject childSets = new BasicDBObject();
		node.put("childSets", childSets);

		return node;
	}

	public void read() {

		Mongo m = null;
		try {
			m = new Mongo("localhost", 27017);

			DB db = m.getDB("mydb");

			DBCollection coll = db.getCollection("testCollection");

			DBObject myDoc = coll.findOne();
			System.out.println(myDoc);

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			m.close();
		}

	}

	public void write() {

		Mongo m = null;
		try {
			m = new Mongo("localhost", 27017);

			DB db = m.getDB("mydb");

			DBCollection coll = db.getCollection("testCollection");

			BasicDBObject doc = new BasicDBObject();

			doc.put("name", "MongoDB");
			doc.put("type", "database");
			doc.put("count", 1);

			BasicDBObject info = new BasicDBObject();

			info.put("x", 203);
			info.put("y", 102);

			doc.put("info", info);

			coll.insert(doc);

			DBObject myDoc = coll.findOne();
			System.out.println(myDoc);

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			m.close();
		}

	}

}
