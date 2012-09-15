package org.sri.nodeservice.core.nodeaccess.service.rsrq;

import java.util.ArrayList;
import java.util.List;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;

/**
 * 
 * @author sisaac
 *
 */
public class ListNodesRs extends BaseNodeRs {
	
	private int totalCount;

	private List<Node> nodes = new ArrayList<Node>();

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}

/* 
{
	"success":false,
	"errors":{
		"lastName":"Last Name has to have at least 2 characters",
		"email":"Domain blocked - it is known spam source"
	}
}

*/