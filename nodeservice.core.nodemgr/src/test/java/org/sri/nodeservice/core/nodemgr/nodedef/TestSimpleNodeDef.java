/**
 * 
 */
package org.sri.nodeservice.core.nodemgr.nodedef;

import org.junit.Before;
import org.junit.BeforeClass;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.TstSimpleNodeDefHelper;
import org.sri.nodeservice.testutil.TstFixtureEx;

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

		BaseTstFixture fix = new TstFixtureEx();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestDefaultValid(fix);

		/*TstFixture fix = new TstFixture(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		RichNode simpleNode = (RichNode) simpleNodeDef.getDefaultINode(new RichNodeFactory(), "1", true);

		{
			IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);

			IValidationReport report = validationCallback.getRoot();

			// the report should be valid
			assertTrue(report.isValid());
			assertEquals(report.getProblemIterator().hasNext(), false);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);
		}*/
	}

	@org.junit.Test
	public void TestInvalidNodeType() {
		
		BaseTstFixture fix = new TstFixtureEx();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestInvalidNodeType(fix);

		/*TstFixture fix = new TstFixture(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = (INode) simpleNodeDef.getDefaultINode(new RichNodeFactory(), "1", true);
		NodeDef oneWithTheWrongId = new NodeDef("wrongId");

		{
			IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, oneWithTheWrongId, validationCallback);

			IValidationReport report = validationCallback.getRoot();

			// this should fail with a node structure error
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

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);
		}*/
	}

	@org.junit.Test
	public void TestMandatoryAttributes() {
		
		BaseTstFixture fix = new TstFixtureEx();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestMandatoryAttributes(fix);

		/*
		// create the node from the NodeDef
		TstFixture fix = new TstFixture(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = (INode) simpleNodeDef.getDefaultINode(new RichNodeFactory(), "1", true);

		{
			// remove a required attribute
			INodeAttr removedAttr = simpleNode.removeAttr("string");
			assertEquals("A String Value", removedAttr.getValue());

			IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);
			IValidationReport report = validationCallback.getRoot();

			// node should be invalid.
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

		{
			// now set the atttibute to non-mandatory, the report should be valid.
			simpleNodeDef.getAttrDefById("string").setMandatory(false);

			IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);
			IValidationReport report = validationCallback.getRoot();

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
		*/
	}
}
