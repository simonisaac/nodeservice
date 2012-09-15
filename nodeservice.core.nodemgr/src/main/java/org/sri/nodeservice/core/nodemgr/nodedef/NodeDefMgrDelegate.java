package org.sri.nodeservice.core.nodemgr.nodedef;

import org.sri.nodeservice.core.nodedef.service.model.NodeDefTO;
/**
 * @author sisaac
 *
 */
public interface NodeDefMgrDelegate {
	
	public NodeDefTO getNodeDefinitionTO(String nodeType);
	
}
