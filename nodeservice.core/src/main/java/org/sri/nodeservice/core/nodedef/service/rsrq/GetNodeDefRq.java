package org.sri.nodeservice.core.nodedef.service.rsrq;

/**
 * 
 * @author sisaac
 *
 */
public class GetNodeDefRq extends BaseNodeDefRq {
	
	private String nodeType;

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
}
