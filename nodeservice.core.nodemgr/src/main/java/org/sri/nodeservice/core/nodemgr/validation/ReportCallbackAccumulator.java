package org.sri.nodeservice.core.nodemgr.validation;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.util.INodeUtils;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;

/**
 * Simple ReportCallback to count the numbers of nodes or each type processed.
 * 
 * @author sisaac
 *
 */
public class ReportCallbackAccumulator implements ReportCallback {

	public static final String CONTEXT_NODE_ID = "Accumulator";

	public static final String TOTAL_NODE_COUNT = "totalNodeCount";

	@Override
	public void beginNode(IValidationReport report, NodeDef nodeDef, INode node) {
		IValidationReport rootReport = report.getRootReport();
		INode contextNode = rootReport.getContextNode(CONTEXT_NODE_ID);
		
		// do the total
		int totalNodeCount = INodeUtils.getIntAttrValue(contextNode, TOTAL_NODE_COUNT, true);
		totalNodeCount++;
		INodeUtils.setIntAttrValue(contextNode, TOTAL_NODE_COUNT, totalNodeCount, false);
		
		// do the type count
		int typeCount = INodeUtils.getIntAttrValue(contextNode, nodeDef.getNodeDefId(), true);
		typeCount++;
		INodeUtils.setIntAttrValue(contextNode, nodeDef.getNodeDefId(), typeCount, false);
	}

	@Override
	public void endNode(IValidationReport report, NodeDef nodeDef, INode node) {
	}

	@Override
	public void endNodeSet(IValidationReport report, NodeSetDef curChildSetDef, INodeSet curChildSet) {
	}

	@Override
	public void beginNodeSet(IValidationReport report, NodeSetDef curChildSetDef, INodeSet curChildSet) {
	}

	@Override
	public void endAttr(IValidationReport report, AttrDef curAttrDef, INodeAttr curAttr) {
	}

	@Override
	public void beginAttr(IValidationReport report, AttrDef curAttrDef, INodeAttr curAttr) {
	}
}
