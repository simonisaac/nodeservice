package org.sri.nodeservice.transform.common.nodeaccess;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.BaseNodeRq;
import org.sri.nodeservice.transform.common.util.HttpRequestAdapter;
import org.sri.nodeservice.transform.common.util.TransformContext;

/**
 * 
 * @author sisaac
 *
 */
public interface InHttpRqToNodeRq {

	public BaseNodeRq transform(TransformContext context,  HttpRequestAdapter httpRequest);
	
	public BaseNodeRq transform(TransformContext context,  Node node);
	
}
