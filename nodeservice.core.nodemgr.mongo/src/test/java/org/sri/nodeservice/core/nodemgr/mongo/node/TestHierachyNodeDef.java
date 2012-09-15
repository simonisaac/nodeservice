/**
 * 
 */
package org.sri.nodeservice.core.nodemgr.mongo.node;

import org.junit.Before;
import org.junit.BeforeClass;
import org.sri.nodeservice.core.nodemgr.mongo.util.MongoTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.TstHierachyNodeDefHelper;

/**
 * @author sisaac
 *
 */
public class TestHierachyNodeDef {

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
		TstHierachyNodeDefHelper helper = new TstHierachyNodeDefHelper();
		helper.TestDefaultValid(fix);

		/*TstFixtureMongo mongoFixture = new TstFixtureMongo();
		ITstFixture fix = new MongoTstFixture(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
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

			IValidationCallback validationCallback = new MongoValidationCallback(mongoFixture.getColl());
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);

			MongoValidationReport report = MongoValidationReport.getMongoValidationObjectForTargetId(mongoFixture.getColl(), simpleNode
					.getMongoObject().getString("_id"));
			assertNotNull(report);

			assertTrue(report.isValid());
			IReportIterator childReportItr = report.getChildReportIterator();
			int childReportCount = 0;
			for (IValidationReport childReport : childReportItr) {
				childReportCount++;
				assertTrue(childReport.isValid());
				assertFalse(childReport.getChildReportIterator().hasNext());
				assertEquals(childReport.getProblemIterator().hasNext(), false);
				assertEquals(childReport.getProblemCount(), 0);
				assertEquals(childReport.getChildProblemCount(), 0);
			}
			assertEquals(3, childReportCount);

			assertTrue(report.isValid());
			assertTrue(report.getChildReportIterator().hasNext());
			assertEquals(report.getProblemIterator().hasNext(), false);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);*
		}*/
	}

	@org.junit.Test
	public void TestBelowMinimuChildren() {

		BaseTstFixture fix = new MongoTstFixture();
		TstHierachyNodeDefHelper helper = new TstHierachyNodeDefHelper();
		helper.TestBelowMinimuChildren(fix);

		/*TstFixtureMongo mongoFixture = new TstFixtureMongo();
		ITstFixture fix = new MongoTstFixture(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		{
			MongoNode simpleNode = (MongoNode) simpleNodeDef.getDefaultINode(new MongoNodeFactory(mongoFixture.getColl()), "1", true);
			INode deletedNode = simpleNode.getChildNodeSet("childnodes").removeChildNode("1_0");
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

			IValidationCallback validationCallback = new MongoValidationCallback(mongoFixture.getColl());
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);

			MongoValidationReport report = MongoValidationReport.getMongoValidationObjectForTargetId(mongoFixture.getColl(), simpleNode
					.getMongoObject().getString("_id"));
			assertNotNull(report);

			// check the parent report
			assertFalse(report.isValid());
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
			
			// now check the child reports
			IReportIterator childReportItr = report.getChildReportIterator();
			int childReportCount = 0;
			for (IValidationReport childReport : childReportItr) {
				childReportCount++;
				assertTrue(childReport.isValid());
				assertFalse(childReport.getChildReportIterator().hasNext());
				assertEquals(childReport.getProblemIterator().hasNext(), false);
				assertEquals(childReport.getProblemCount(), 0);
				assertEquals(childReport.getChildProblemCount(), 0);
			}
			assertEquals(2, childReportCount);
		}*/
	}
}
