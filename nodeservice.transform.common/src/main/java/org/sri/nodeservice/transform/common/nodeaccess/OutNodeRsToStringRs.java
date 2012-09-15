package org.sri.nodeservice.transform.common.nodeaccess;

import org.sri.nodeservice.core.nodeaccess.service.rsrq.BaseNodeRs;
import org.sri.nodeservice.transform.common.util.TransformContext;

/**
 * 
 * @author sisaac
 *
 */
public interface OutNodeRsToStringRs {
	public String transform(TransformContext context, BaseNodeRs nodeRs);
	
	public String transform(TransformContext context, String message, String code);
}
