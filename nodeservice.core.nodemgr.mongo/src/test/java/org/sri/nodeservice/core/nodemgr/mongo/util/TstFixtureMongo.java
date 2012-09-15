package org.sri.nodeservice.core.nodemgr.mongo.util;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class TstFixtureMongo {
	private Mongo mongo;

	private DB db;

	private DBCollection coll;

	public TstFixtureMongo() {
		try {
			this.mongo = new Mongo("localhost", 27017);

			this.db = this.mongo.getDB("mydb");

			this.coll = db.getCollection("testCollection");
			
			this.coll.drop();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public DBCollection getColl() {
		return coll;
	}

	public void setColl(DBCollection coll) {
		this.coll = coll;
	}
}
