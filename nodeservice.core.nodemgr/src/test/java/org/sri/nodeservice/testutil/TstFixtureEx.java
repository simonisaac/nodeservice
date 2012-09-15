package org.sri.nodeservice.testutil;

import org.sri.nodeservice.core.nodemgr.defaultimpl.node.DefaultNodeStore;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;

public class TstFixtureEx extends BaseTstFixture {

	private INode generatedNode;

	private INodeStore nodeStore = new DefaultNodeStore();

	/*@Override
	public INodeFactory getNodeFactory() {
		//return new RichNodeFactory(this.getNodeDefMgr());
		return null;
	}*/

	@Override
	public INode retrieveNode() {
		return this.generatedNode;
	}

	@Override
	public INode generateDefaultNode() {
		INode rootNode = this.nodeStore.createRootNode("rootNode", "roottype");
		INodeSet nodeSet = rootNode.createChildNodeSet("testNodes");
		this.generatedNode = this.getNodeDef().getDefaultINode(nodeSet, "1", true);
		return this.generatedNode;
	}

	/*@Override
	public IValidationReportFactory getValidationReportFactory() {
		return null;
	}*/

	@Override
	public INodeStore getNodeStore() {
		return this.nodeStore;
	}
}
