package org.sri.nodeservice.core.nodemgr.mongo.node;

import java.util.Iterator;

import org.apache.commons.lang.NotImplementedException;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class MongoNodeIterator implements INodeIterator {

	private DBCursor cursor;

	private DBCollection dbCollection;

	public MongoNodeIterator(DBCollection dbCollection, DBCursor cursor) {
		this.dbCollection = dbCollection;
		this.cursor = cursor;
	}

	@Override
	public Iterator<INode> iterator() {
		return new MongoNodeIterator(this.dbCollection, cursor);
	}

	@Override
	public boolean hasNext() {
		return this.cursor.hasNext();
	}

	@Override
	public INode next() {
		MongoNode mongoNode = new MongoNode(this.dbCollection, (BasicDBObject) this.cursor.next());
		return mongoNode;
	}

	@Override
	public void remove() {
		throw new NotImplementedException();
	}
}
