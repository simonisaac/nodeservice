package org.sri.nodeservice.transform.json.nodedef;

import org.sri.nodeservice.core.nodedef.service.rsrq.BaseNodeDefRs;
import org.sri.nodeservice.transform.common.nodedef.OutNodeDefRsToStringRs;
import org.sri.nodeservice.transform.common.util.TransformContext;

import flexjson.JSONSerializer;

/**
 * 
 * @author sisaac
 * 
 */
public class NodeDefRsToJSON implements OutNodeDefRsToStringRs {

	@Override
	public String transform(TransformContext context, BaseNodeDefRs nodeDefRs) {
		try {
			JSONSerializer s = new JSONSerializer();
			String string = s.deepSerialize(nodeDefRs);
			return string;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}