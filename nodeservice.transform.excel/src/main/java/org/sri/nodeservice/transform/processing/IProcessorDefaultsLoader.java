package org.sri.nodeservice.transform.processing;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;

public class IProcessorDefaultsLoader {

	private INodeStore defaultsNodeStore;
	
	public INodeStore loadDefaults() {
		return this.defaultsNodeStore;
	}

	public INodeStore getDefaultsNodeStore() {
		return defaultsNodeStore;
	}

	public void setDefaultsNodeStore(INodeStore defaultsNodeStore) {
		this.defaultsNodeStore = defaultsNodeStore;
	}
}
