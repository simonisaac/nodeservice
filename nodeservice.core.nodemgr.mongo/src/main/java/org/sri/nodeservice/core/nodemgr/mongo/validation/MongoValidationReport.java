package org.sri.nodeservice.core.nodemgr.mongo.validation;

import org.sri.nodeservice.core.nodemgr.mongo.node.MongoNode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.validation.report.IProblemIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IReportIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;

/**
 * @author sisaac
 * 
 */
public class MongoValidationReport implements IValidationReport {

	private DBCollection mongoCollection;

	//private BasicDBObject hostObject;

	private BasicDBObject mongoObject;

	// private BasicDBObject validationReportObject;

	// -------------------------------------------------------------------------
	//
	//

	public MongoValidationReport(DBCollection collection, MongoNode mongoNode) {
		this.mongoCollection = collection;

		// create and insert the mongo object
		this.mongoObject = new BasicDBObject();

		this.setTargetId(mongoNode.getMongoObject().getString("_id"));
		this.setParentTargetId(mongoNode.getParentNodeId());
		this.setChildProblemCount(0);
		this.setProblemCount(0);

		this.mongoCollection.insert(this.mongoObject);
	}

	public MongoValidationReport(DBCollection mongoCollection, BasicDBObject mongoObject) {
		this.mongoCollection = mongoCollection;
		this.mongoObject = mongoObject;
	}

	// -------------------------------------------------------------------------
	//
	// Implement IValidationReport

	@Override
	public boolean isValid() {
		boolean rBool = true;
		if (this.getWorstProblemSeverity().isFatal()) {
			rBool = false;
		}
		return rBool;
	}

	@Override
	public void registerProblem(Problem problem) {
		// create the new problem and add to the "problems" object
		BasicDBObject newProblemDBO = new BasicDBObject();
		newProblemDBO.put("_type", "problem");
		newProblemDBO.put("problemTarget", this.getTargetId());
		newProblemDBO.put("problemType", problem.getId());
		newProblemDBO.put("severity", problem.getSeverity().getLevel());
		newProblemDBO.put("message", problem.getMessage());
		newProblemDBO.put("detail", problem.getDetail());
		this.mongoCollection.insert(newProblemDBO);
		int problemCount = this.getChildErrorCount();
		//this.getProblemsDBObject().put(String.valueOf(problemCount), newProblemDBO);
		this.setProblemCount(++problemCount);

		if (problem.getSeverity().worseThan(this.getWorstProblemSeverity())) {
			this.setWorstProblem(problem.getSeverity());
		}

		// Permeate up the tree if required.
		if (this.getParentReport() != null) {
			this.getParentReport().registerChildProblem(problem);
		}
	}

	@Override
	public void registerChildProblem(Problem problem) {
		if (problem.getSeverity().worseThan(this.getChildWorstProblem())) {
			this.setChildWorstProblem(problem.getSeverity());
		}
		int childProblemCount = this.getChildErrorCount();
		this.setProblemCount(++childProblemCount);

		// Permeate up the tree if required.
		if (this.getParentReport() != null) {
			this.getParentReport().registerChildProblem(problem);
		}
	}
	
	@Override
	public ProblemSeverity getWorstProblemSeverity() {
		ProblemSeverity rSeverity = null;
		int worstSeverity = this.getMongoObject().getInt("worstSeverity", 0);
		rSeverity = ProblemSeverity.fromLevel(worstSeverity);
		return rSeverity;
	}
	
	@Override
	public int getProblemCount() {
		return this.getMongoObject().getInt("problemCount");
	}
	
	@Override
	public IProblemIterator getProblemIterator(ProblemSeverity severity) {
		BasicDBObject query = new BasicDBObject();
		query.put("_type", "problem");
		query.put("problemTarget", this.getTargetId());
		query.put("severity", severity.getLevel());
		DBCursor results = this.mongoCollection.find(query);
		MongoProblemIterator rItr = new MongoProblemIterator(results);
		return rItr;
	}

	@Override
	public INode getContextNode(String id) {
		INode rNode = this.contextNodeMap.get(id);
		if (rNode == null) {
			rNode = this.nodeFactory.createNode(id, "ContextNode");
			this.contextNodeMap.put(id, rNode);
		}
		return rNode;
	}

	// -------------------------------------------------------------------------
	//
	// Mongo Object Helpers

	/*public BasicDBObject getHostDBObject() {
		return null;
	}*/

	private BasicDBObject getMongoObject() {
		return this.mongoObject;
	}

	/*private BasicDBObject getProblemsDBObject() {
		BasicDBObject problems = (BasicDBObject) this.getMongoObject().get("problems");
		if (problems == null) {
			problems = new BasicDBObject();
			this.getMongoObject().put("problems", problems);
			this.updateToMongo();
		}
		return problems;
	}*/

	public static MongoValidationReport getMongoValidationObjectForTargetId(DBCollection coll, String validationTargetId) {
		MongoValidationReport rReport = null;
		BasicDBObject query = new BasicDBObject();
		query.put("targetId", validationTargetId);
		DBCursor cursor = coll.find(query);
		assert cursor.count() < 2; // there should only be one report per node
		if (cursor.hasNext()) {
			BasicDBObject mongoObj = (BasicDBObject) cursor.next();
			rReport = new MongoValidationReport(coll, mongoObj);
		}
		return rReport;
	}

	public void updateToMongo() {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", this.mongoObject.get("_id"));
		WriteResult result = this.mongoCollection.update(query, this.mongoObject);
	}
	
	

	// -------------------------------------------------------------------------
	//
	//

	public String getTargetId() {
		return this.mongoObject.getString("targetId");
	}

	public String getParentTargetId() {
		return this.mongoObject.getString("parentTargetId");
	}

	public void setTargetId(String id) {
		this.mongoObject.put("targetId", id);
	}

	public void setParentTargetId(String id) {
		this.mongoObject.put("parentTargetId", id);
	}

	

	private void setWorstProblem(ProblemSeverity severity) {
		this.getMongoObject().put("worstSeverity", severity.getLevel());
		this.updateToMongo();
	}

	private void setChildWorstProblem(ProblemSeverity severity) {
		this.getMongoObject().put("worstChildSeverity", severity.getLevel());
		this.updateToMongo();
	}

	private ProblemSeverity getChildWorstProblem() {
		ProblemSeverity rSeverity = null;
		int worstSeverity = this.getMongoObject().getInt("worstChildSeverity", 0);
		rSeverity = ProblemSeverity.fromLevel(worstSeverity);
		return rSeverity;
	}

	

	private void setProblemCount(int count) {
		this.getMongoObject().put("problemCount", count);
		this.updateToMongo();
	}

	public int getChildErrorCount() {
		return this.getMongoObject().getInt("childProblemCount");
	}

	public void setChildProblemCount(int count) {
		this.getMongoObject().put("childProblemCount", count);
		this.updateToMongo();
	}

	@Override
	public IValidationReport getParentReport() {
		IValidationReport rReport = null;

		Object parentTargetId = this.mongoObject.get("parentTargetId");
		if (parentTargetId != null) {
			BasicDBObject query = new BasicDBObject();
			query.put("targetId", parentTargetId);
			DBCursor results = this.mongoCollection.find(query);
			BasicDBObject mongoVReport = (BasicDBObject) results.next();
			rReport = new MongoValidationReport(this.mongoCollection, mongoVReport);
		}

		return rReport;
	}

	@Override
	public IProblemIterator getProblemIterator() {
		BasicDBObject query = new BasicDBObject();
		query.put("_type", "problem");
		query.put("problemTarget", this.getTargetId());
		DBCursor results = this.mongoCollection.find(query);
		MongoProblemIterator rItr = new MongoProblemIterator(results);
		return rItr;
	}

	@Override
	public IReportIterator getChildReportIterator() {
		BasicDBObject query = new BasicDBObject();
		query.put("parentTargetId", this.getTargetId());
		DBCursor results = this.mongoCollection.find(query);
		IReportIterator rItr = new MongoReportIterator(this.mongoCollection, results);
		return rItr;
	}

	public void delete() {
		// remove previous validation reports for this node.
		BasicDBObject query = new BasicDBObject();
		query.put("_id", this.getMongoObject().get("_id"));
		this.mongoCollection.remove(query);
		// remove previous validation problems for this node.
		query = new BasicDBObject();
		query.put("problemTarget", this.getMongoObject().getString("targetId"));
		this.mongoCollection.remove(query);
	}

	public String getNodeId() {
		return null;
	}

	public IValidationReport getRootReport() {
		return null;
	}

	public void addChildReport(IValidationReport validationReport) {
	}



}
