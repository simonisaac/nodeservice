package org.sri.nodeservice.core.nodeaccess.service.rsrq;

/**
 * 
 * @author sisaac
 *
 */
public class GetNodeRq extends BaseNodeRq {

	private String id;
	
	private String nodeType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
}
