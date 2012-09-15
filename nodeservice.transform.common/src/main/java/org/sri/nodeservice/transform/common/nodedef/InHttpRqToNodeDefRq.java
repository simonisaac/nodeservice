package org.sri.nodeservice.transform.common.nodedef;

import org.sri.nodeservice.core.nodedef.service.rsrq.BaseNodeDefRq;
import org.sri.nodeservice.transform.common.util.HttpRequestAdapter;
import org.sri.nodeservice.transform.common.util.TransformContext;

/**
 * 
 * @author sisaac
 *
 */
public interface InHttpRqToNodeDefRq {

	public BaseNodeDefRq transform(TransformContext context,  HttpRequestAdapter httpRequest);
	
}
