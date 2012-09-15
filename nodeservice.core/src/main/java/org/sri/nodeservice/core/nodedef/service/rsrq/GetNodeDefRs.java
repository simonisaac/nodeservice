package org.sri.nodeservice.core.nodedef.service.rsrq;

import org.sri.nodeservice.core.nodedef.service.model.NodeDefTO;


/**
 * 
 * @author sisaac
 *
 */
public class GetNodeDefRs extends BaseNodeDefRs {

	private NodeDefTO nodeDefinition;

	public NodeDefTO getNodeDefinition() {
		return nodeDefinition;
	}

	public void setNodeDefinition(NodeDefTO nodeDefinition) {
		this.nodeDefinition = nodeDefinition;
	}
}

