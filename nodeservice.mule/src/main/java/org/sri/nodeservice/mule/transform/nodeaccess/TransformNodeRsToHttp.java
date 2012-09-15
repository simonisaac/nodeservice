package org.sri.nodeservice.mule.transform.nodeaccess;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.DataTypeFactory;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.BaseNodeRs;
import org.sri.nodeservice.transform.common.exception.IServiceExceptionTransformer;
import org.sri.nodeservice.transform.common.exception.ServiceException;
import org.sri.nodeservice.transform.common.nodeaccess.OutNodeRsToStringRs;
import org.sri.nodeservice.transform.common.util.TransformContext;

public class TransformNodeRsToHttp extends AbstractMessageTransformer {

	private OutNodeRsToStringRs nodeRsToHttpRs;
	
	private IServiceExceptionTransformer serviceExceptionTransformer;

	public TransformNodeRsToHttp() {
		registerSourceType(DataTypeFactory.OBJECT);
		setReturnDataType(DataTypeFactory.STRING);
	}
	
	public TransformNodeRsToHttp(OutNodeRsToStringRs nodeRsToHttpRs) {
		this.nodeRsToHttpRs = nodeRsToHttpRs;
	}

	@Override
	public String transformMessage(final MuleMessage message, final String outputEncoding) throws TransformerException {
		String rString = null;

		try {
			TransformContext context = (TransformContext) message.getProperty(
					"txContext", PropertyScope.SESSION);
			
			if (message.getExceptionPayload() != null) {
				
				Throwable throwable = message.getExceptionPayload().getException();
				ServiceException serviceException = serviceExceptionTransformer.transform(context, throwable);
				
				// catch all exceptions
				rString = this.nodeRsToHttpRs.transform(context, serviceException.getMessage(), serviceException.getCode());
			} else {
				// get stuff from mule message.
				BaseNodeRs businessRs = (BaseNodeRs) message.getPayload();

				// do the transform.
				rString = (String) this.nodeRsToHttpRs.transform(context,
						businessRs);
			}
		} catch (Throwable e) {
			throw new TransformerException(this, e);
		}
		return rString;
	}

	public OutNodeRsToStringRs getNodeRsToHttpRs() {
		return nodeRsToHttpRs;
	}

	public void setNodeRsToHttpRs(OutNodeRsToStringRs nodeRsToHttpRs) {
		this.nodeRsToHttpRs = nodeRsToHttpRs;
	}
	
	/**
	 * @param serviceExceptionTransformer the serviceExceptionTransformer to set
	 */
	public void setServiceExceptionTransformer(
			IServiceExceptionTransformer serviceExceptionTransformer) {
		this.serviceExceptionTransformer = serviceExceptionTransformer;
	}
}