package org.sri.nodeservice.core.nodeaccess.service.model;

import java.util.HashMap;
import java.util.Map;

public class NodeSet {

	private String id;
	
	private Map<String, Node> nodes = new HashMap<String, Node>();
	
	@SuppressWarnings("unused")
	private NodeSet() {
	}
	
	public NodeSet(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Node> getNodes() {
		return this.nodes;
	}

	public void addNode (Node node) {
		this.nodes.put(node.getId(), node);
	}
}
