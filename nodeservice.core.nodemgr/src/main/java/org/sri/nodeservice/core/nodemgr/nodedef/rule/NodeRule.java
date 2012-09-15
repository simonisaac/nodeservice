package org.sri.nodeservice.core.nodemgr.nodedef.rule;

import org.sri.nodeservice.core.nodemgr.defaultimpl.node.DefaultNode;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;
/**
 * @author sisaac
 *
 */
public abstract class NodeRule {

	public abstract void validate(DefaultNode node, IValidationReport report);
}
