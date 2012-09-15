/**
 * 
 */
package org.sri.nodeservice.core.nodemgr.nodedef;

import org.junit.Before;
import org.junit.BeforeClass;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.TstHierachyNodeDefHelper;
import org.sri.nodeservice.testutil.TstFixtureEx;

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

		BaseTstFixture fix = new TstFixtureEx();
		TstHierachyNodeDefHelper helper = new TstHierachyNodeDefHelper();
		helper.TestDefaultValid(fix);

		/*TstFixture fix = new TstFixture(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = (INode) simpleNodeDef.getDefaultINode(new RichNodeFactory(), "1", true);
		
		{
			IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);
			IValidationReport report = validationCallback.getRoot();
			
			// do the parent
			assertTrue(report.isValid());
			assertTrue(report.getChildReportIterator().hasNext());
			assertEquals(report.getProblemIterator().hasNext(), false);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);
			
			// check the children
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
		}*/
	}

	@org.junit.Test
	public void TestBelowMinimumChildren() {
		BaseTstFixture fix = new TstFixtureEx();
		TstHierachyNodeDefHelper helper = new TstHierachyNodeDefHelper();
		helper.TestBelowMinimuChildren(fix);

		/*TstFixture fix = new TstFixture(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = (INode) simpleNodeDef.getDefaultINode(new RichNodeFactory(), "1", true);
		INode deletedNode = simpleNode.getChildNodeSet("childnodes").removeChildNode("1_0");
		assertNotNull(deletedNode);
				
		{
			IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);
			IValidationReport report = validationCallback.getRoot();
			
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
