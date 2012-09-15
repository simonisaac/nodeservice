package org.sri.nodeservice.transform.common.nodedef;

import org.sri.nodeservice.core.nodedef.service.rsrq.BaseNodeDefRs;
import org.sri.nodeservice.transform.common.util.TransformContext;

/**
 * 
 * @author sisaac
 *
 */
public interface OutNodeDefRsToStringRs {
	public String transform(TransformContext context, BaseNodeDefRs nodeDefRs);
}
