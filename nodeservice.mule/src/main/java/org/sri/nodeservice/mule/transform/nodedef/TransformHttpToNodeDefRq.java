package org.sri.nodeservice.mule.transform.nodedef;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.DataTypeFactory;
import org.sri.nodeservice.core.nodedef.service.rsrq.BaseNodeDefRq;
import org.sri.nodeservice.transform.common.nodedef.InHttpRqToNodeDefRq;
import org.sri.nodeservice.transform.common.util.HttpRequestAdapter;
import org.sri.nodeservice.transform.common.util.TransformContext;

public class TransformHttpToNodeDefRq extends AbstractMessageTransformer {
	
	private InHttpRqToNodeDefRq httpToNodeDefRq;
	
	public TransformHttpToNodeDefRq() {
		registerSourceType(DataTypeFactory.OBJECT);
		setReturnDataType(DataTypeFactory.create(BaseNodeDefRq.class));
		
		//this.httpToMetadataRq = new HttpRequestToNodeMetadataRq();
	}
	
	@Override
	public Object transformMessage(final MuleMessage message, final String outputEncoding) throws TransformerException {

		BaseNodeDefRq nodeDefRq = null;
		
		if (message.getProperty("http.request", PropertyScope.INBOUND) != null) {

			// adapt the http request
			String muleQueryString = (String) message.getProperty("http.request", PropertyScope.INBOUND);
			HttpRequestAdapter httpRequest = new HttpRequestAdapter(muleQueryString);
			
			// create the context.
			TransformContext context = new TransformContext();
			message.setProperty("txContext", context, PropertyScope.INBOUND);
			
			// run the transform.
			nodeDefRq = this.httpToNodeDefRq.transform(context, httpRequest);
		}

		return nodeDefRq;
	}

	public InHttpRqToNodeDefRq getHttpToNodeDefRq() {
		return httpToNodeDefRq;
	}

	public void setHttpToNodeDefRq(InHttpRqToNodeDefRq httpToNodeDefRq) {
		this.httpToNodeDefRq = httpToNodeDefRq;
	}
}
