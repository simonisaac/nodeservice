package org.sri.nodeservice.core.nodemgr.mongo.validation;

import java.util.Stack;

import org.sri.nodeservice.core.nodemgr.mongo.node.MongoNode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.validation.IValidationCallback;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

import com.mongodb.DBCollection;

/**
 * @author sisaac
 * 
 */
public class MongoValidationCallback implements IValidationCallback {
	
	private MongoValidationReport rootReport;

	private Stack<MongoValidationReport> stack = new Stack<MongoValidationReport>();

	private DBCollection mongoCollection;

	public MongoValidationCallback(DBCollection mongoCollection) {
		this.mongoCollection = mongoCollection;
	}

	@Override
	public void addProblem(INode node, String problemId, ProblemSeverity severity, String message, String detailMessage) {
		this.addProblem(problemId, severity, message, detailMessage);
	}
	
	@Override
	public void addProblem(String problemId, ProblemSeverity severity, String message, String detailMessage) {
		Problem problem = new Problem(severity, problemId, message, detailMessage);
		this.getCurrentMongoValidationReport().registerProblem(problem);
	}

	@Override
	public void beginNodeValidation(NodeDef nodeDef, INode node) {
		MongoNode mongoNode = (MongoNode) node;
		MongoValidationReport existingReport = MongoValidationReport.getMongoValidationObjectForTargetId(mongoCollection, mongoNode.getMongoObject()
				.getString("_id"));

		if (existingReport != null) {
			existingReport.delete();
		}
		MongoValidationReport report = new MongoValidationReport(this.mongoCollection, mongoNode);
		this.stack.push(report);
		if (this.rootReport == null) {
			this.rootReport = report;
		}
	}

	@Override
	public void endNodeValidation(NodeDef nodeDef, INode node) {
		this.stack.pop();
	}

	@Override
	public void beginAttrValidation(AttrDef curAttrDef) {
		// TODO Auto-generated method stub
	}

	@Override
	public void endAttrValidation(AttrDef curAttrDef) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beginNodeSetValidation(NodeSetDef curChildSetDef) {
		// TODO Auto-generated method stub
	}

	@Override
	public void endNodeSetValidation(NodeSetDef curChildSetDef) {
		// TODO Auto-generated method stub
	}

	//////////////////////////////
	// DEPRICATED OVERRIDES

	/*@Override
	public void endValidation(String entityType, String entityId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginValidation(String entityType, String entityId, Object node) {
		// TODO Auto-generated method stub

	}*/

	@Override
	public IValidationReport getRoot() {
		/*MongoValidationReport report = MongoValidationReport.getMongoValidationObjectForTargetId(this.mongoCollection, simpleNode
				.getMongoObject().getString("_id"));
		assertNotNull(report);*/
		return this.rootReport;
	}

	/*@Override
	public void pushRichObject(RichObject richObject) {
		// TODO Auto-generated method stub

	}*/



	private MongoValidationReport getCurrentMongoValidationReport() {
		return this.stack.peek();
	}
}
