/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   13 Jul 201111:30:35
 * Workfile::  TestSimpleXMLNodeDef.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.core.nodemgr.nodedef;

import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.TstSimpleNodeDefHelper;
import org.sri.nodeservice.testutil.TstFixtureXML;

/**
 * @author jkochhar
 * 
 */
public class TestSimpleXMLNodeDef {

	@org.junit.Test
	public void TestDefaultValid() {

		BaseTstFixture fix = new TstFixtureXML();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestDefaultValid(fix);
	}

	@org.junit.Test
	public void TestInvalidNodeType() {
		
		BaseTstFixture fix = new TstFixtureXML();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestInvalidNodeType(fix);
	}

	@org.junit.Test
	public void TestMandatoryAttributes() {
		
		BaseTstFixture fix = new TstFixtureXML();
		TstSimpleNodeDefHelper helper = new TstSimpleNodeDefHelper();
		helper.TestMandatoryAttributes(fix);
	}

	
	/*@org.junit.Test
	public void testSimpleVaild() {

		TstFixtureXML fix = new TstFixtureXML(
				TstLanguageDefReader.SIMPLE_NODE_DEF, "xml/languageDef.xml");
		NodeDef simpleNodeDef = fix.getNodeDef();
		Node simpleNode = fix.getDefaultNode();

		{
			IValidationCallback builder = new ReportBuilder();
			simpleNodeDef.validate(simpleNode, builder);
			ValidationReport report = builder.getRoot();
			TstUtil.traceReport(report);
			assertTrue(report.isValid());
			assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT)
					.getReports().size(), simpleNode.getFields().size());
			assertEquals(report.getProblems().size(), 0);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);
		}

	}
	
	@org.junit.Test
	public void TestInvalidNodeType() {

		TstFixtureXML fix = new TstFixtureXML(
				TstLanguageDefReader.SIMPLE_NODE_DEF, "xml/languageDef.xml");
		NodeDef simpleNodeDef = fix.getNodeDef();
		Node simpleNode = fix.getDefaultNode();

		simpleNode.setType("error");
		{
			IValidationCallback builder = new ReportBuilder();
			simpleNodeDef.validate(simpleNode, builder);
			ValidationReport report = builder.getRoot();
			TstUtil.traceReport(report);
			assertFalse(report.isValid());
			assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT), null);
			assertEquals(report.getProblems().size(), 1);
			assertEquals(report.getProblemCount(), 1);
			assertEquals(report.getChildProblemCount(), 0);
		}
	}

	@org.junit.Test
	public void TestMandatoryAttributes() {

		TstFixtureXML fix = new TstFixtureXML(
				TstLanguageDefReader.SIMPLE_NODE_DEF, "xml/languageDef.xml");
		NodeDef simpleNodeDef = fix.getNodeDef();
		Node simpleNode = fix.getDefaultNode();

		simpleNode.getFields().remove("string");
		{
			IValidationCallback builder = new ReportBuilder();
			simpleNodeDef.validate(simpleNode, builder);
			ValidationReport report = builder.getRoot();
			TstUtil.traceReport(report);
			assertFalse(report.isValid());
			assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT).getReports().size(), simpleNode.getFields().size() + 1);
			assertEquals(report.getProblems().size(), 0);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 1);
		}

		simpleNodeDef.getAttrDefById("string").setMandatory(false);
		{
			IValidationCallback builder = new ReportBuilder();
			simpleNodeDef.validate(simpleNode, builder);
			ValidationReport report = builder.getRoot();
			TstUtil.traceReport(report);
			assertTrue(report.isValid());
			assertEquals(report.getChildReportCollection(NodeDef.ATTR_REPORT).getReports().size(), simpleNode.getFields().size() + 1);
			assertEquals(report.getProblems().size(), 0);
			assertEquals(report.getProblemCount(), 0);
			assertEquals(report.getChildProblemCount(), 0);
		}
	}*/

}
