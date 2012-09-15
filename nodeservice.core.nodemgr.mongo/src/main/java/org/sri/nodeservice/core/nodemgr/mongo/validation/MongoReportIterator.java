package org.sri.nodeservice.core.nodemgr.mongo.validation;

import java.util.Iterator;

import org.apache.commons.lang.NotImplementedException;
import org.sri.nodeservice.core.nodemgr.validation.report.IReportIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class MongoReportIterator implements IReportIterator {

	private DBCollection coll;

	private DBCursor cursor;

	public MongoReportIterator(DBCollection coll, DBCursor cursor) {
		this.coll = coll;
		this.cursor = cursor;
	}

	@Override
	public Iterator<IValidationReport> iterator() {
		return new MongoReportIterator(coll, cursor);
	}

	@Override
	public boolean hasNext() {
		return this.cursor.hasNext();
	}

	@Override
	public IValidationReport next() {
		MongoValidationReport rReport = new MongoValidationReport(coll, (BasicDBObject) this.cursor.next());
		return rReport;
	}

	@Override
	public void remove() {
		throw new NotImplementedException();
	}
}
