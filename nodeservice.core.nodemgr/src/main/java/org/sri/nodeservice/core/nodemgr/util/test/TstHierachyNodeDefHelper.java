/**
 * 
 */
package org.sri.nodeservice.core.nodemgr.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.validation.report.IProblemIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IReportIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

/**
 * @author sisaac
 *
 */
public class TstHierachyNodeDefHelper {

	public void TestDefaultValid(BaseTstFixture fix) {

		fix.init(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = fix.generateDefaultNode();

		IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);

		// do the parent
		assertTrue(report.isValid());
		assertTrue(report.getChildReportIterator().hasNext());
		assertEquals(report.getProblemIterator().hasNext(), false);
		assertEquals(report.getProblemCount(), 0);
		assertEquals(report.getChildErrorCount(), 0);

		// check the children
		IReportIterator childReportItr = report.getChildReportIterator();
		int childReportCount = 0;
		for (IValidationReport childReport : childReportItr) {
			childReportCount++;
			assertTrue(childReport.isValid());
			assertFalse(childReport.getChildReportIterator().hasNext());
			assertEquals(childReport.getProblemIterator().hasNext(), false);
			assertEquals(childReport.getProblemCount(), 0);
			assertEquals(childReport.getChildErrorCount(), 0);
		}
		assertEquals(3, childReportCount);
	}

	@org.junit.Test
	public void TestBelowMinimuChildren(BaseTstFixture fix) {

		fix.init(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = fix.generateDefaultNode();

		//TstFixture fix = new TstFixture(TstLanguageDefReader.SIMPLE_HIERACHY_DEF);
		//NodeDef simpleNodeDef = fix.getNodeDef();
		//INode simpleNode = (INode) simpleNodeDef.getDefaultINode(new RichNodeFactory(), "1", true);
		INode deletedNode = simpleNode.getChildNodeSet("childnodes").removeChildNode("1_0");
		assertNotNull(deletedNode);

		{
			/*NodeDefFirstIterator vitr = new NodeDefFirstIterator(fix.getNodeDefMgr());
			ReportNodeDefFirstIteratorCallback callback = new ReportNodeDefFirstIteratorCallback(fix.getNodeStore().createRootNode("reportRoot",
					"rootReport"));
			callback.addReportCallback(new NodeDefValidator());
			callback.addReportCallback(new Accumulator());
			vitr.iterate(simpleNode, simpleNodeDef, callback);
			IValidationReport report = callback.getRootReport();
			/*IValidationCallback validationCallback = new DefaultValidationCallback();
			NodeDefINodeValidator validator = new NodeDefINodeValidator(fix.getNodeDefMgr());
			validator.validate(simpleNode, simpleNodeDef, validationCallback);
			IValidationReport report = validationCallback.getRoot();*/

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);

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
			assertEquals(report.getChildErrorCount(), 0);

			// now check the child reports
			IReportIterator childReportItr = report.getChildReportIterator();
			int childReportCount = 0;
			for (IValidationReport childReport : childReportItr) {
				childReportCount++;
				assertTrue(childReport.isValid());
				assertFalse(childReport.getChildReportIterator().hasNext());
				assertEquals(childReport.getProblemIterator().hasNext(), false);
				assertEquals(childReport.getProblemCount(), 0);
				assertEquals(childReport.getChildErrorCount(), 0);
			}
			assertEquals(2, childReportCount);
		}
	}
}
