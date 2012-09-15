package org.sri.nodeservice.core.nodeaccess.service.rsrq;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;

/**
 * 
 * @author sisaac
 *
 */
public class UpdateNodeRq extends BaseNodeRq {

	private Node node;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
}
