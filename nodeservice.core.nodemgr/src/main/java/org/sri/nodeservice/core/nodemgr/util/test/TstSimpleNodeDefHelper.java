/**
 * 
 */
package org.sri.nodeservice.core.nodemgr.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallbackAccumulator;
import org.sri.nodeservice.core.nodemgr.validation.report.IProblemIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

/**
 * @author sisaac
 *
 */
public class TstSimpleNodeDefHelper {

	public void TestDefaultValid(BaseTstFixture fix) {

		fix.init(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = fix.generateDefaultNode();
		assertNotNull(simpleNode);

		{
			INode retrievedNode = fix.retrieveNode();

			IValidationReport report = FixUtils.validate(fix, retrievedNode, simpleNodeDef);

			// the report should be valid
			assertTrue(report.isValid());
			assertEquals(report.getProblemIterator().hasNext(), false);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);

			// check the accumulator
			INode accumulatorNode = report.getContextNode(ReportCallbackAccumulator.CONTEXT_NODE_ID);
			assertEquals(1, INodeUtils.getIntAttrValue(accumulatorNode, ReportCallbackAccumulator.TOTAL_NODE_COUNT, false));
		}
	}

	@org.junit.Test
	public void TestInvalidNodeType(BaseTstFixture fix) {

		fix.init(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		assertNotNull(simpleNodeDef);
		INode simpleNode = fix.generateDefaultNode();

		NodeDef oneWithTheWrongId = new NodeDef("wrongId");
		{
			IValidationReport report = FixUtils.validate(fix, simpleNode, oneWithTheWrongId);
			
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
			assertEquals(report.getChildErrorCount(), 0);

			// there should be no children
			assertEquals(report.getChildReportIterator().hasNext(), false);
		}
	}

	@org.junit.Test
	public void TestMandatoryAttributes(BaseTstFixture fix) {

		fix.init(TstLanguageDefReader.SIMPLE_NODE_DEF);
		NodeDef simpleNodeDef = fix.getNodeDef();
		INode simpleNode = fix.generateDefaultNode();

		{
			// remove a required attribute
			INodeAttr removedAttr = simpleNode.removeAttr("string");
			assertEquals("A String Value", removedAttr.getValue());

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);
			
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
			assertEquals(report.getChildErrorCount(), 0);
		}

		{
			// now set the atttibute to non-mandatory, the report should be valid.
			simpleNodeDef.getAttrDefById("string").setMandatory(false);

			IValidationReport report = FixUtils.validate(fix, simpleNode, simpleNodeDef);
			
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
			assertEquals(report.getChildErrorCount(), 0);
		}
	}
}
