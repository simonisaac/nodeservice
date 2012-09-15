package org.sri.nodeservice.mule.transform.nodeaccess;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.DataTypeFactory;
import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.BaseNodeRq;
import org.sri.nodeservice.transform.common.nodeaccess.InHttpRqToNodeRq;
import org.sri.nodeservice.transform.common.util.HttpRequestAdapter;
import org.sri.nodeservice.transform.common.util.TransformContext;

public class TransformHttpToNodeRq extends AbstractMessageTransformer {
	
	private InHttpRqToNodeRq httpRqToNodeRq;
	
	private InHttpMultipartTransformer httpMultipartTransformer;
	
	public TransformHttpToNodeRq() {
		registerSourceType(DataTypeFactory.OBJECT);
		setReturnDataType(DataTypeFactory.create(BaseNodeRq.class));
	}
	
	public TransformHttpToNodeRq(InHttpRqToNodeRq httpRqToNodeRq) {
		this.httpRqToNodeRq = httpRqToNodeRq;
	}
	
	@Override
	public Object transformMessage(final MuleMessage message, final String outputEncoding) throws TransformerException {

		BaseNodeRq nodeRq = null;
		
		// create the context.
		TransformContext context = new TransformContext();
		message.setProperty("txContext", context, PropertyScope.SESSION);
		
		String contentType = message.getInboundProperty("Content-Type");
		if (contentType != null && contentType.contains("multipart/form-data")) {
			
			//transform the multipart request to node 
			Node node = httpMultipartTransformer.transformMessage(message);
			
			// run the transform.
			nodeRq = this.httpRqToNodeRq.transform(context, node);
			
		}else if (message.getProperty("http.request", PropertyScope.INBOUND) != null) {

			// adapt the http request
			String muleQueryString = (String) message.getProperty("http.request", PropertyScope.INBOUND);
			HttpRequestAdapter httpRequest = new HttpRequestAdapter(muleQueryString);
			
			// run the transform.
			nodeRq = this.httpRqToNodeRq.transform(context, httpRequest);
		}

		return nodeRq;
	}

	public InHttpRqToNodeRq getHttpRqToNodeRq() {
		return httpRqToNodeRq;
	}

	public void setHttpRqToNodeRq(InHttpRqToNodeRq httpRqToNodeRq) {
		this.httpRqToNodeRq = httpRqToNodeRq;
	}
	
	public void setHttpMultipartTransformer(
			InHttpMultipartTransformer httpMultipartTransformer) {
		this.httpMultipartTransformer = httpMultipartTransformer;
	}
	
}
