package org.sri.nodeservice.core.nodedef.service;

import org.sri.nodeservice.core.nodedef.service.rsrq.GetNodeDefRq;
import org.sri.nodeservice.core.nodedef.service.rsrq.GetNodeDefRs;

/**
 * 
 * @author sisaac
 *
 */
public interface NodeDefProvided {

	GetNodeDefRs getNodeDefinition(GetNodeDefRq rq);
}
