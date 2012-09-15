package org.sri.nodeservice.core.nodemgr.nodedef;

/**
 * @author sisaac
 *
 */
public class NodeSetDef {
	
	private String id;
	
	private String nodeType;
	
	private int minOccurances;
	
	private int maxOccurances;

	public NodeSetDef(String id, String nodeType) {
		super();
		this.id = id;
		this.nodeType = nodeType;
	}

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

	public int getMinOccurances() {
		return minOccurances;
	}

	public void setMinOccurances(int minOccurances) {
		this.minOccurances = minOccurances;
	}

	public int getMaxOccurances() {
		return maxOccurances;
	}

	public void setMaxOccurances(int maxOccurances) {
		this.maxOccurances = maxOccurances;
	}
}
