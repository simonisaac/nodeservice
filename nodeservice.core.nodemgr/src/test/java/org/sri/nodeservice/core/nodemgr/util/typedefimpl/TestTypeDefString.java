package org.sri.nodeservice.core.nodemgr.util.typedefimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.sri.nodeservice.core.nodemgr.defaultimpl.node.DefaultNodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.FixUtils;
import org.sri.nodeservice.core.nodemgr.util.test.TstLanguageDefReader;
import org.sri.nodeservice.core.nodemgr.validation.report.IProblemIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;
import org.sri.nodeservice.testutil.TstFixtureEx;

public class TestTypeDefString {
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
	public void TestMaxLengthConstraint() {

		BaseTstFixture fix = new TstFixtureEx();

		fix.init(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = fix.generateDefaultNode();

		//TstFixture fix = new TstFixture(TstLanguageDefReader.SIMPLE_NODE_DEF);
		//NodeDef simpleNodeDef = fix.getNodeDef();
		//Node simpleNode = fix.getDefaultNode();

		//simpleNode.getFields().put("stringMax20", "01234567890123456789_");
		{
			simpleNode.getAttr("stringMax20").setValue("01234567890123456789_");
			//RichAttr existingAttr = (RichAttr) simpleNode.getAttr("stringMax20");
			//simpleNode.setAttr(new RichAttr(existingAttr.getAttrDef(), "01234567890123456789_"));

			/*IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);

			IValidationReport report = validationCallback.getRoot();
			*/
			/*NodeDefFirstIterator vitr = new NodeDefFirstIterator(fix.getNodeDefMgr());
			INodeStore nodeStore = new DefaultNodeStore();
			ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(nodeStore.createRootNode("reportRoot", "rootReport"));
			callback.addReportCallback(new NodeDefValidator());
			vitr.iterate(simpleNode, simpleNodeDef, callback);
			IValidationReport report = callback.getRootReport();*/

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);

			// this should fail with a node structure error
			assertFalse(report.isValid());
			IProblemIterator itr = report.getProblemIterator();
			assertTrue(itr.hasNext());
			Problem problem = itr.next();
			assertEquals(ProblemSeverity.ERROR, problem.getSeverity());
			assertEquals(Problem.CONSTRAINT, problem.getId());
			assertNotNull(problem.getMessage());
			assertNotNull(problem.getDetail());
			assertFalse(itr.hasNext());
			assertEquals(report.getProblemCount(), 1);
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);

			/*			
						assertFalse(report.isValid());
						assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT).getReports().size(), simpleNode.getFields().size());
						assertEquals(report.getProblems().size(), 0);
						assertEquals(report.getProblemCount(), 0);
						assertEquals(report.getChildProblemCount(), 1);*/
		}

		//simpleNode.getFields().put("stringMax20", "01234567890123456789");
		{
			DefaultNodeAttr existingAttr = (DefaultNodeAttr) simpleNode.getAttr("stringMax20");
			existingAttr.setValue("01234567890123456789");
			//simpleNode.setAttr(new RichAttr(existingAttr.getAttrDef(), "01234567890123456789"));

			/*IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);

			IValidationReport report = validationCallback.getRoot();
			*/
			/*NodeDefFirstIterator vitr = new NodeDefFirstIterator(fix.getNodeDefMgr());
			INodeStore nodeStore = new DefaultNodeStore();
			ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(nodeStore.createRootNode("reportRoot", "rootReport"));
			vitr.iterate(simpleNode, simpleNodeDef, callback);
			IValidationReport report = callback.getRootReport();*/

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);

			// this should fail with a node structure error
			assertTrue(report.isValid());
			IProblemIterator itr = report.getProblemIterator();
			assertFalse(itr.hasNext());
			//Problem problem = itr.next();
			//assertEquals(ProblemSeverity.ERROR, problem.getSeverity());
			//assertEquals(Problem.ATTR_TYPE, problem.getId());
			//assertNotNull(problem.getMessage());
			//assertNotNull(problem.getDetail());
			//assertFalse(itr.hasNext());
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);

			/*IValidationCallback builder = new ReportBuilder();
			simpleNodeDef.validate(simpleNode, builder);
			ValidationReport report = builder.getRoot();
			TstUtil.traceReport(report);
			assertTrue(report.isValid());
			assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT).getReports().size(), simpleNode.getFields().size());
			assertEquals(report.getProblems().size(), 0);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);*/
		}
	}

	@org.junit.Test
	public void TestMinMaxAndExtension() {

		BaseTstFixture fix = new TstFixtureEx();

		fix.init(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = fix.generateDefaultNode();

		//simpleNode.getFields().put("stringExMin5Max10", "0123");
		{
			DefaultNodeAttr existingAttr = (DefaultNodeAttr) simpleNode.getAttr("stringExMin5Max10");
			existingAttr.setValue("0123");
			//simpleNode.setAttr(new RichAttr(existingAttr.getAttrDef(), "0123"));

			/*IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);

			IValidationReport report = validationCallback.getRoot();
			*/
			/*NodeDefFirstIterator vitr = new NodeDefFirstIterator(fix.getNodeDefMgr());
			INodeStore nodeStore = new DefaultNodeStore();
			ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(nodeStore.createRootNode("reportRoot", "rootReport"));
			callback.addReportCallback(new NodeDefValidator());
			vitr.iterate(simpleNode, simpleNodeDef, callback);
			IValidationReport report = callback.getRootReport();*/

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);

			// this should fail with a node structure error
			assertFalse(report.isValid());
			IProblemIterator itr = report.getProblemIterator();
			assertTrue(itr.hasNext());
			Problem problem = itr.next();
			assertEquals(ProblemSeverity.ERROR, problem.getSeverity());
			assertEquals(Problem.CONSTRAINT, problem.getId());
			assertNotNull(problem.getMessage());
			assertNotNull(problem.getDetail());
			assertFalse(itr.hasNext());
			assertEquals(report.getProblemCount(), 1);
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);
		}

		{
			DefaultNodeAttr existingAttr = (DefaultNodeAttr) simpleNode.getAttr("stringExMin5Max10");
			existingAttr.setValue("0123456789_");
			//simpleNode.setAttr(new RichAttr(existingAttr.getAttrDef(), "0123456789_"));

			/*NodeDefFirstIterator vitr = new NodeDefFirstIterator(fix.getNodeDefMgr());
			INodeStore nodeStore = new DefaultNodeStore();
			ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(nodeStore.createRootNode("reportRoot", "rootReport"));
			callback.addReportCallback(new NodeDefValidator());
			vitr.iterate(simpleNode, simpleNodeDef, callback);
			IValidationReport report = callback.getRootReport();*/

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);

			// this should fail with a node structure error
			assertFalse(report.isValid());
			IProblemIterator itr = report.getProblemIterator();
			assertTrue(itr.hasNext());
			Problem problem = itr.next();
			assertEquals(ProblemSeverity.ERROR, problem.getSeverity());
			assertEquals(Problem.CONSTRAINT, problem.getId());
			assertNotNull(problem.getMessage());
			assertNotNull(problem.getDetail());
			assertFalse(itr.hasNext());
			assertEquals(report.getProblemCount(), 1);
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);
		}

		{
			simpleNode.getAttr("stringExMin5Max10").setValue("0123456789");
			//RichAttr existingAttr = (RichAttr) simpleNode.getAttr("stringExMin5Max10");
			//simpleNode.setAttr(new RichAttr(existingAttr.getAttrDef(), "0123456789"));

			/*IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);

			IValidationReport report = validationCallback.getRoot();
			*/
			/*NodeDefFirstIterator vitr = new NodeDefFirstIterator(fix.getNodeDefMgr());
			INodeStore nodeStore = new DefaultNodeStore();
			ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(nodeStore.createRootNode("reportRoot", "rootReport"));
			vitr.iterate(simpleNode, simpleNodeDef, callback);
			IValidationReport report = callback.getRootReport();*/

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);

			assertTrue(report.isValid());
			IProblemIterator itr = report.getProblemIterator();
			assertFalse(itr.hasNext());
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);
		}

		{
			simpleNode.getAttr("stringExMin5Max10").setValue("01234");
			//RichAttr existingAttr = (RichAttr) simpleNode.getAttr("stringExMin5Max10");
			//simpleNode.setAttr(new RichAttr(existingAttr.getAttrDef(), "01234"));

			/*NodeDefFirstIterator vitr = new NodeDefFirstIterator(fix.getNodeDefMgr());
			INodeStore nodeStore = new DefaultNodeStore();
			ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(nodeStore.createRootNode("reportRoot", "rootReport"));
			vitr.iterate(simpleNode, simpleNodeDef, callback);
			IValidationReport report = callback.getRootReport();*/

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);

			assertTrue(report.isValid());
			IProblemIterator itr = report.getProblemIterator();
			assertFalse(itr.hasNext());
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);
		}

		/*simpleNode.getFields().put("stringExMin5Max10", "0123456789_");
		{
			IValidationCallback builder = new ReportBuilder();
			simpleNodeDef.validate(simpleNode, builder);
			ValidationReport report = builder.getRoot();
			TstUtil.traceReport(report);
			assertFalse(report.isValid());
			assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT).getReports().size(), simpleNode.getFields().size());
			assertEquals(report.getProblems().size(), 0);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 1);
		}

		simpleNode.getFields().put("stringExMin5Max10", "0123456789");
		{
			IValidationCallback builder = new ReportBuilder();
			simpleNodeDef.validate(simpleNode, builder);
			ValidationReport report = builder.getRoot();
			TstUtil.traceReport(report);
			assertTrue(report.isValid());
			assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT).getReports().size(), simpleNode.getFields().size());
			assertEquals(report.getProblems().size(), 0);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);
		}

		simpleNode.getFields().put("stringExMin5Max10", "01234");
		{
			IValidationCallback builder = new ReportBuilder();
			simpleNodeDef.validate(simpleNode, builder);
			ValidationReport report = builder.getRoot();
			TstUtil.traceReport(report);
			assertTrue(report.isValid());
			assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT).getReports().size(), simpleNode.getFields().size());
			assertEquals(report.getProblems().size(), 0);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);
		}*/
	}
}
