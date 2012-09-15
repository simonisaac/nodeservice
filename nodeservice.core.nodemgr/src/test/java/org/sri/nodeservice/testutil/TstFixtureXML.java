package org.sri.nodeservice.testutil;

import java.io.FileInputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.sri.nodeservice.core.nodedef.service.model.LanguageDefTO;
import org.sri.nodeservice.core.nodemgr.defaultimpl.node.DefaultNodeStore;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;
import org.sri.nodeservice.core.nodemgr.util.test.BaseTstFixture;
import org.sri.nodeservice.core.nodemgr.util.test.TstLanguageDefReader;
import org.sri.nodeservice.core.nodemgr.util.transform.XmlToLanguageDefTO;

public class TstFixtureXML extends BaseTstFixture {

	private INode generatedNode;

	private INodeStore nodeStore = new DefaultNodeStore();

	@Override
	public LanguageDefTO getLanguageDefTO(String nodeType) {
		LanguageDefTO langTO = null;

		Resource resourceXML = null;
		if (TstLanguageDefReader.SIMPLE_NODE_DEF.equals(nodeType)) {
			resourceXML = new ClassPathResource("xml/languageDef.xml");

		} else if (TstLanguageDefReader.SIMPLE_HIERACHY_DEF.equals(nodeType)) {
			resourceXML = new ClassPathResource("xml/languageDefHierarchy.xml");
		}

		try {
			langTO = XmlToLanguageDefTO.unmarshall(new FileInputStream(resourceXML.getFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return langTO;
	}

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
		//this.generatedNode = this.getNodeDef().getDefaultINode(this.getNodeFactory(), "1", true);
		//return this.generatedNode;
		INode rootNode = this.nodeStore.createRootNode("rootNode", "rootTestNodeType");
		INodeSet nodeSet = rootNode.createChildNodeSet("testNodes");
		this.generatedNode = this.getNodeDef().getDefaultINode(nodeSet, "1", true);
		return this.generatedNode;
	}

	/*@Deprecated
	@Override
	public IValidationReportFactory getValidationReportFactory() {
		assert false;
		return null;
		//return new DefaultReportFactory();
	}*/

	@Override
	public INodeStore getNodeStore() {
		return this.nodeStore;
	}
}
