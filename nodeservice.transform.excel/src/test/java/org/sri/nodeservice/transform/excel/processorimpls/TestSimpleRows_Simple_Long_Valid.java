package org.sri.nodeservice.transform.excel.processorimpls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.sri.nodeservice.core.nodemgr.defaultimpl.node.DefaultNodeStore;
import org.sri.nodeservice.core.nodemgr.defaultimpl.validation.DefaultValidationReport;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;
import org.sri.nodeservice.core.nodemgr.validation.ReportCallbackAccumulator;
import org.sri.nodeservice.transform.excel.testutil.ExcelProcessor_Simple_ShortValid_TstFixtureMgr;
import org.sri.nodeservice.transform.processing.ProcessingManager;
import org.sri.nodeservice.transform.processing.ProcessingResult;

public class TestSimpleRows_Simple_Long_Valid {

	private static ExcelProcessor_Simple_ShortValid_TstFixtureMgr fix;

	private static ProcessingManager processor;

	private static INodeStore nodeStore;

	private static ProcessingResult job;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fix = new ExcelProcessor_Simple_ShortValid_TstFixtureMgr();

		nodeStore = new DefaultNodeStore();
		processor = fix.loadProcessor(nodeStore, "spreadsheettests/simple/simpleLanguageDef.xml",
				"spreadsheettests/simple/simpleProcessingInstructions.xml", true);

		Resource resourceXLS = new ClassPathResource("spreadsheettests/simple/simpleLanguageSSLongValid.xlsx");
		job = processor.process(resourceXLS.getFile(), "packId-testExcel");
	}

	@Before
	public void setUp() throws Exception {

	}

	@org.junit.Test
	public void testProcessTestLong() throws Exception {

		DefaultValidationReport report = job.getReportRoot();

		assertTrue(report.isValid());

		INode accumulatorNode = report.getContextNode(ReportCallbackAccumulator.CONTEXT_NODE_ID);
		assertEquals(2000,
				INodeUtils.getIntAttrValue(accumulatorNode, ReportCallbackAccumulator.TOTAL_NODE_COUNT, false));
		assertEquals(1999, INodeUtils.getIntAttrValue(accumulatorNode, "test3SheetType", false));
	}
}
