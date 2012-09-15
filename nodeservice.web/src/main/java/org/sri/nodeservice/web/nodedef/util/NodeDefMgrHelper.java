package org.sri.nodeservice.web.nodedef.util;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.nodedef.TypeDefMgr;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.BaseConstraints;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.BaseTypes;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.ExBaseTypes;
import org.sri.nodeservice.web.nodedef.model.UINodeDefinition;

public class NodeDefMgrHelper {

	private String nodeType;

	private UINodeDefinition nodeDefinition;
	
	private static NodeDefMgr nodeDefMgr;
	
	static {
		// Initialise attr mgr with base types.
		TypeDefMgr attrTypeMgr = new TypeDefMgr();
		BaseConstraints.registerTypes(attrTypeMgr);
		BaseTypes.registerTypes(attrTypeMgr);
		ExBaseTypes.registerTypes(attrTypeMgr);

		nodeDefMgr = new NodeDefMgr();
		nodeDefMgr.setTypeDefMgr(attrTypeMgr);
		
		String jsonUrl = System.getProperty("json.nodedef.delegate.url");
		// add a basic JSON delegate that sends all node def requests to a server.
		nodeDefMgr.setDelegate (NodeDefMgr.DEFAULT_DELEGATE, new HttpJSONDelegate(jsonUrl));
	}

	public UINodeDefinition getNodeDefinition() throws ClientProtocolException, IOException {
		if (this.nodeDefinition == null) {
			this.nodeDefinition = new UINodeDefinition(nodeDefMgr.getNodeDefinition(this.nodeType));
		}
		return this.nodeDefinition;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
}
