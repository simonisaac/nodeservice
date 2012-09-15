package org.sri.nodeservice.core.nodeaccess.service.rsrq;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;

/**
 * 
 * @author sisaac
 *
 */
public class GetNodeRs extends BaseNodeRs {
	
	//private boolean success = true;

	private Node node;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
}

/*
 * { "success":false, "errors":{
 * "lastName":"Last Name has to have at least 2 characters",
 * "email":"Domain blocked - it is known spam source" } }
 */