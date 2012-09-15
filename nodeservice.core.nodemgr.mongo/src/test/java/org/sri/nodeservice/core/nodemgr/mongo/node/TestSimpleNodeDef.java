/**
 * 
 */
package org.sri.nodeservice.core.nodemgr.mongo.node;

import org.junit.Before;
import org.junit.BeforeClass;
import org.sri.nodeservice.core.nodemgr.mongo.util.MongoTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.TstSimpleNodeDefHelper;

/**
 * @author sisaac
 *
 */
public class TestSimpleNodeDef {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@org.junit.Test
	public void TestDefaultValid() {
		
		BaseTstFixture fix = new MongoTstFixture();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestDefaultValid(fix);
		
		/*TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		
		TstFixtureMongo mongoFixture = new TstFixtureMongo();
		BaseTstFixture fix = new MongoTstFixture(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		{
			MongoNode simpleNode = (MongoNode) simpleNodeDef.getDefaultINode(new MongoNodeFactory(mongoFixture.getColl()), "1", true);
			System.out.println(simpleNode);
		}
		{
			// retrieve the node
			BasicDBObject query = new BasicDBObject();
			query.put("nodeId", "1");
			DBCursor cursor = mongoFixture.getColl().find(query);
			BasicDBObject parent = (BasicDBObject) cursor.next();
			assertNotNull(parent);
			MongoNode simpleNode = new MongoNode(mongoFixture.getColl(), parent);

			//IValidationCallback validationCallback = new MongoValidationCallback(mongoFixture.getColl());
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, fix.getValidationCallback());

			MongoValidationReport report = MongoValidationReport.getMongoValidationObjectForTargetId(mongoFixture.getColl(), simpleNode
					.getMongoObject().getString("_id"));
			assertNotNull(report);

			assertTrue(report.isValid());
			//assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT).getReports().size(), simpleNode.getFields().size());
			assertEquals(report.getProblemIterator().hasNext(), false);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);
		}*/
	}

	@org.junit.Test
	public void TestInvalidNodeType() {
		
		BaseTstFixture fix = new MongoTstFixture();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestInvalidNodeType(fix);
		
		/*BaseTstFixture fix = new MongoTstFixture(TstLanguageDefReader.SIMPLE_NODE_DEF);
		
		INode node = fix.getNodeFactory();
		
		NodeDef simpleNodeDef = fix.getNodeDef();
		TstFixtureMongo mongoFixture = new TstFixtureMongo();
		{
			INode simpleNode = (MongoNode) simpleNodeDef.getDefaultINode(new MongoNodeFactory(mongoFixture.getColl()), "1", true);
			simpleNode.setNodeDefId("error");
		}
		{
			// retrieve the node
			INode retrievedNode = fix.retrieveNode();

			//IValidationCallback validationCallback = new MongoValidationCallback(mongoFixture.getColl());
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(retrievedNode, simpleNodeDef, fix.getValidationCallback());

			MongoValidationReport report = MongoValidationReport.getMongoValidationObjectForTargetId(mongoFixture.getColl(), simpleNode
					.getMongoObject().getString("_id"));
			assertNotNull(report);

			assertFalse(report.isValid());
			assertFalse(report.getChildReportIterator().hasNext());
			IProblemIterator itr = report.getProblemIterator();
			assertTrue(itr.hasNext());
			Problem problem = itr.next();
			assertEquals(ProblemSeverity.ERROR, problem.getSeverity());
			assertEquals("NODE_STRUCTURE", problem.getId());
			assertNotNull(problem.getMessage());
			assertNotNull(problem.getDetail());
			assertFalse(itr.hasNext());
			assertEquals(report.getProblemCount(), 1);
			assertEquals(report.getChildProblemCount(), 0);
		}*/
	}

	@org.junit.Test
	public void TestMandatoryAttributes() {
		
		BaseTstFixture fix = new MongoTstFixture();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestMandatoryAttributes(fix);
		
		/*BaseTstFixture fix = new MongoTstFixture(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		TstFixtureMongo mongoFixture = new TstFixtureMongo();
		{
			MongoNode simpleNode = (MongoNode) simpleNodeDef.getDefaultINode(new MongoNodeFactory(mongoFixture.getColl()), "1", true);
			INodeAttr removedAttr = simpleNode.removeAttr("string");
			assertEquals("A String Value", removedAttr.getValue());
		}
		{
			// retrieve the node
			BasicDBObject query = new BasicDBObject();
			query.put("nodeId", "1");
			DBCursor cursor = mongoFixture.getColl().find(query);
			BasicDBObject parent = (BasicDBObject) cursor.next();
			assertNotNull(parent);
			MongoNode simpleNode = new MongoNode(mongoFixture.getColl(), parent);

			IValidationCallback validationCallback = new MongoValidationCallback(mongoFixture.getColl());
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());

			{
				validator.validate(simpleNode, simpleNodeDef, validationCallback);
				MongoValidationReport report = MongoValidationReport.getMongoValidationObjectForTargetId(mongoFixture.getColl(), simpleNode
						.getMongoObject().getString("_id"));
				assertNotNull(report);
				assertFalse(report.isValid());
				assertFalse(report.getChildReportIterator().hasNext());
				IProblemIterator itr = report.getProblemIterator();
				assertTrue(itr.hasNext());
				Problem problem = itr.next();
				assertEquals(ProblemSeverity.ERROR, problem.getSeverity());
				assertEquals("MISSING_ATTR", problem.getId());
				assertNotNull(problem.getMessage());
				assertNotNull(problem.getDetail());
				assertFalse(itr.hasNext());
				assertEquals(report.getProblemCount(), 1);
				assertEquals(report.getChildProblemCount(), 0);
			}

			// now set the atttibute to non-mandatory, the report should be valid.
			simpleNodeDef.getAttrDefById("string").setMandatory(false);
			{
				validator.validate(simpleNode, simpleNodeDef, validationCallback);
				MongoValidationReport report = MongoValidationReport.getMongoValidationObjectForTargetId(mongoFixture.getColl(), simpleNode
						.getMongoObject().getString("_id"));
				assertNotNull(report);
				assertTrue(report.isValid());
				assertFalse(report.getChildReportIterator().hasNext());
				IProblemIterator itr = report.getProblemIterator();
				assertTrue(itr.hasNext());
				Problem problem = itr.next();
				assertEquals(ProblemSeverity.INFO, problem.getSeverity());
				assertEquals("MISSING_ATTR", problem.getId());
				assertNotNull(problem.getMessage());
				assertNotNull(problem.getDetail());
				assertFalse(itr.hasNext());
				assertEquals(report.getProblemCount(), 1);
				assertEquals(report.getChildProblemCount(), 0);
			}
		}*/
	}
}
