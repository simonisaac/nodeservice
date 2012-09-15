package org.sri.nodeservice.transform.excel.processorimpls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.xml.bind.ValidationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodemgr.defaultimpl.node.DefaultNodeStore;
import org.sri.nodeservice.core.nodemgr.defaultimpl.validation.DefaultValidationReport;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallbackAccumulator;
import org.sri.nodeservice.core.nodemgr.validation.report.IProblemIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.transform.excel.testutil.ExcelProcessor_Simple_ShortValid_TstFixtureMgr;
import org.sri.nodeservice.transform.processing.ProcessingManager;
import org.sri.nodeservice.transform.processing.ProcessingResult;

public class TestSimpleRows_Simple_Short_InValid {

	private static ExcelProcessor_Simple_ShortValid_TstFixtureMgr fix;

	private static ProcessingManager processor;

	private static INodeStore nodeStore;

	private static ProcessingResult processingResult;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fix = new ExcelProcessor_Simple_ShortValid_TstFixtureMgr();

		nodeStore = new DefaultNodeStore();
		processor = fix.loadProcessor(nodeStore, "spreadsheettests/simple/simpleLanguageDef.xml",
				"spreadsheettests/simple/simpleProcessingInstructions.xml", true);

		Resource resourceXLS = new ClassPathResource("spreadsheettests/simple/simpleLanguageSSShortInvalid.xlsx");
		processingResult = processor.process(resourceXLS.getFile(), "packId-testExcel");
	}

	@Before
	public void setUp() throws Exception {

	}

	@org.junit.Test
	public void testResultRoot() throws Exception {

		// get the root result and assert.
		INodeIterator rootResultsItr = processingResult.getRootResultsNodeIterator();
		assertTrue(rootResultsItr.hasNext());
		INode rootResult = rootResultsItr.next();
		assertFalse(rootResultsItr.hasNext()); // there should be only one root.
		assertEquals("testExcel", rootResult.getNodeDefId());
		
		rootResult = processingResult.getRootResultNodeAt(1);
		assertNull(rootResult);
		
		rootResult = processingResult.getRootResultNodeAt(0);
		assertNotNull(rootResult);
		
		INodeSet rows = rootResult.getChildNodeSet("rows");
		assertNotNull(rows);
	}
	
	@org.junit.Test
	public void testReportRoot() throws Exception {

		DefaultValidationReport report = processingResult.getReportRoot();

		assertFalse(report.isValid());
		assertEquals(0, report.getProblemCount());
		assertEquals(2, report.getChildErrorCount());
	}
	
	@org.junit.Test
	public void testAccumulator() throws Exception {
		DefaultValidationReport report = processingResult.getReportRoot();

		INode accumulatorNode = report.getContextNode(ReportCallbackAccumulator.CONTEXT_NODE_ID);
		assertEquals(13, INodeUtils.getIntAttrValue(accumulatorNode, ReportCallbackAccumulator.TOTAL_NODE_COUNT, false));
		assertEquals(12, INodeUtils.getIntAttrValue(accumulatorNode, "test3SheetType", false));
	}
	
	@org.junit.Test
	public void testRow1() throws Exception {
		INode rootResult = processingResult.getRootResultNodeAt(0);
		INodeSet rows = rootResult.getChildNodeSet("rows");
		INode row1Node = rows.getNodeForNodeId("Row-1");
		
		NodeDefMgr nodeDefMgr = processor.getProcessingDef("packId-testExcel").getNodeDefMgr();
		NodeDef rowNodeDef = nodeDefMgr.getNodeDefinition("test3SheetType");
		
		String valueA = (String)rowNodeDef.getObjectForAttrValue(row1Node, "A - String");
		assertTrue("Action-1".equals(valueA));
		Long valueB = (Long)rowNodeDef.getObjectForAttrValue(row1Node, "B - Number");
		assertEquals(new Long(2), valueB);
		String valueC = (String)rowNodeDef.getObjectForAttrValue(row1Node, "C - Decimal");
		assertTrue("34.78".equals(valueC));
		
		// This one is the error
		ValidationException e = null;
		try {
			Date valueD = (Date)rowNodeDef.getObjectForAttrValue(row1Node, "D - Date");
			assertTrue("a string not adate".equals(valueD));// this should never be reached
		}
		catch (ValidationException ee) {
			e = ee;
		}
		assertNotNull(e);
		String valueD = row1Node.getAttr("D - Date").getValue();
		assertTrue("a string not adate".equals(valueD));
		
		String valueE = (String)rowNodeDef.getObjectForAttrValue(row1Node, "E - Pound");
		assertTrue("434".equals(valueE));
		String valueF = (String)rowNodeDef.getObjectForAttrValue(row1Node, "F - Formula");
		assertTrue("468.78".equals(valueF));
		String valueG = (String)rowNodeDef.getObjectForAttrValue(row1Node, "G - Text");
		assertTrue("ABC1".equals(valueG));
		String valueH = (String)rowNodeDef.getObjectForAttrValue(row1Node, "H - String Formula");
		assertTrue("Action-1ABC1".equals(valueH));
		String valueI = (String)rowNodeDef.getObjectForAttrValue(row1Node, "I - Time Type");
		assertTrue("12:01:00".equals(valueI));
		
		DefaultValidationReport rowOneReport = processingResult.getReportForNodeUID(row1Node.getUID());
		assertEquals(1, rowOneReport.getProblemCount());
		IProblemIterator problemItr = rowOneReport.getProblemIterator();
		Problem problem = problemItr.next();
		assertNotNull(problem);
	}
	
	@org.junit.Test
	public void testProcessTestShortInvalid() throws Exception {

		INodeIterator rootResultsItr = processingResult.getRootResultsNodeIterator();
		assertTrue(rootResultsItr.hasNext());
	
		// check the first row - this should have an invalid date
		Node query = new Node(null, null, DefaultValidationReport.VALIDATION_REPORT_TYPE);
		query.getFields().put(DefaultValidationReport.TARGET_NODE_ID, "Row-1");
		INodeIterator itr = nodeStore.find(query);
		DefaultValidationReport rowOneReport = new DefaultValidationReport(itr.next());
		assertEquals(1, rowOneReport.getProblemCount());
		IProblemIterator problemItr = rowOneReport.getProblemIterator();
		Problem problem = problemItr.next();
		assertNotNull(problem);
	}
}
