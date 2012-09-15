package org.sri.nodeservice.core.nodemgr.mongo.validation;

import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

import com.mongodb.BasicDBObject;

public class MongoProblem {

	private BasicDBObject dbObject;
	
	public MongoProblem(BasicDBObject dbObject) {
		this.dbObject = dbObject;
	}

	public Problem getProblem() {
		ProblemSeverity severity = ProblemSeverity.fromLevel(this.dbObject.getInt("severity"));
		String problemId = this.dbObject.getString("problemType");
		String message = this.dbObject.getString("message");
		String detail = this.dbObject.getString("detail");
		Problem rProblem = new Problem(severity, problemId, message, detail);
		return rProblem;
	}

}
