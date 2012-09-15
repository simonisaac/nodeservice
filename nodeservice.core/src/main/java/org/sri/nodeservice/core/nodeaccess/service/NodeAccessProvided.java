package org.sri.nodeservice.core.nodeaccess.service;

import org.sri.nodeservice.core.nodeaccess.service.rsrq.CreateNodeRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.CreateNodeRs;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.GetNodeRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.GetNodeRs;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.ListNodesRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.ListNodesRs;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.UpdateNodeRq;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.UpdateNodeRs;

/**
 * 
 * @author sisaac
 *
 */
public interface NodeAccessProvided {

	public GetNodeRs getNode(GetNodeRq rq);
	
	public ListNodesRs retrieveNodes (ListNodesRq rq);
	
	public UpdateNodeRs updateNode (UpdateNodeRq rq);
	
	public CreateNodeRs createNode(CreateNodeRq rq);
	
}
