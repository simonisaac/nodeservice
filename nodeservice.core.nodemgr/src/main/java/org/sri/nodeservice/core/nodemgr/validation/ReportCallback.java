package org.sri.nodeservice.core.nodemgr.validation;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;

public interface ReportCallback {

	void beginNode(IValidationReport report, NodeDef nodeDef, INode node);

	void endNode(IValidationReport report, NodeDef nodeDef, INode node);

	void endNodeSet(IValidationReport report, NodeSetDef curChildSetDef, INodeSet curChildSet);

	void beginNodeSet(IValidationReport report, NodeSetDef curChildSetDef, INodeSet curChildSet);

	void endAttr(IValidationReport report, AttrDef curAttrDef, INodeAttr curAttr);

	void beginAttr(IValidationReport report, AttrDef curAttrDef, INodeAttr curAttr);
}