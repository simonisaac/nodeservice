package org.sri.nodeservice.core.nodemgr.mongo.validation;

import java.util.Iterator;

import org.apache.commons.lang.NotImplementedException;
import org.sri.nodeservice.core.nodemgr.validation.report.IProblemIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public class MongoProblemIterator implements IProblemIterator {

	private DBCursor cursor;

	public MongoProblemIterator(DBCursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public Iterator<Problem> iterator() {
		return new MongoProblemIterator(cursor);
	}

	@Override
	public boolean hasNext() {
		return this.cursor.hasNext();
	}

	@Override
	public Problem next() {
		MongoProblem problemMongo = new MongoProblem(
				(BasicDBObject) this.cursor.next());
		return problemMongo.getProblem();
	}

	@Override
	public void remove() {
		throw new NotImplementedException();
	}
}
