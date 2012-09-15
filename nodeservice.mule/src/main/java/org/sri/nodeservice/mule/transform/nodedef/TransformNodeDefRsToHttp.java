package org.sri.nodeservice.mule.transform.nodedef;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.DataTypeFactory;
import org.sri.nodeservice.core.nodedef.service.rsrq.BaseNodeDefRs;
import org.sri.nodeservice.transform.common.nodedef.OutNodeDefRsToStringRs;
import org.sri.nodeservice.transform.common.util.TransformContext;

public class TransformNodeDefRsToHttp extends AbstractMessageTransformer {

	private OutNodeDefRsToStringRs nodeDefRsToStringRs;

	public TransformNodeDefRsToHttp() {
		registerSourceType(DataTypeFactory.OBJECT);
		setReturnDataType(DataTypeFactory.STRING);

		//this.metadataRsToJSON = new NodeMetadataRsToJSON();
	}

	@Override
	public String transformMessage(final MuleMessage message, final String outputEncoding) throws TransformerException {
		String rString = null;

		try {
			// get stuff from mule message.
			BaseNodeDefRs businessRs = (BaseNodeDefRs) message.getPayload();
			TransformContext context = (TransformContext) message.getProperty("txContext", PropertyScope.SESSION);

			// do the transform.
			rString = (String) this.nodeDefRsToStringRs.transform(context, businessRs);
		}
		catch (Throwable e) {
			throw new TransformerException(this, e);
		}

		return rString;
	}

	public OutNodeDefRsToStringRs getNodeDefRsToStringRs() {
		return nodeDefRsToStringRs;
	}

	public void setNodeDefRsToStringRs(OutNodeDefRsToStringRs nodeDefRsToStringRs) {
		this.nodeDefRsToStringRs = nodeDefRsToStringRs;
	}
}