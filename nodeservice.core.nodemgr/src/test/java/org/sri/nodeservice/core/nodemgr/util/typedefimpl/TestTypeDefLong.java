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

public class TestTypeDefLong {
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
		
		fix.init(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = fix.generateDefaultNode();
		
		// break the validaiton
		DefaultNodeAttr existingAttr = (DefaultNodeAttr)simpleNode.getAttr("long");
		existingAttr.setValue("this is not a long");
		//simpleNode.setAttr(new RichAttr(existingAttr.getAttrDef(), "long"));

		{
			//IValidationCallback validationCallback = new DefaultValidationCallback();
			//NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			//validator.validate(simpleNode, simpleNodeDef, validationCallback);
			
			/*NodeDefFirstIterator vitr = new NodeDefFirstIterator(fix.getNodeDefMgr());
			//ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(fix.getValidationReportFactory(), fix.getNodeFactory());
			INodeStore nodeStore = new DefaultNodeStore();
			ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(
					nodeStore.createRootNode("reportRoot", "rootReport"));
			callback.addReportCallback(new NodeDefValidator());
			vitr.iterate(simpleNode, simpleNodeDef, callback);*/
			//IValidationReport report = callback.getRootReport();

			
			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);
			

			//IValidationReport report = validationCallback.getRoot();

			// this should fail with a node structure error
			assertFalse(report.isValid());
			IProblemIterator itr = report.getProblemIterator();
			assertTrue(itr.hasNext());
			Problem problem = itr.next();
			assertEquals(ProblemSeverity.ERROR, problem.getSeverity());
			assertEquals(Problem.ATTR_TYPE, problem.getId());
			assertNotNull(problem.getMessage());
			assertNotNull(problem.getDetail());
			assertFalse(itr.hasNext());
			assertEquals(report.getProblemCount(), 1);
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);
		}
		
		/*fix.init(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = fix.generateDefaultNode();

		INode retrievedNode = fix.retrieveNode();
		NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
		IValidationCallback callback = fix.getValidationCallback();
		validator.validate(retrievedNode, simpleNodeDef, callback);

		IValidationReport report = callback.getRoot();

		simpleNode.getFields().put("long", "stringvalue");
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
		}*/
	}

}
