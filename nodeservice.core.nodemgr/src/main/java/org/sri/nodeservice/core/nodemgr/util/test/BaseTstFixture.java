package org.sri.nodeservice.core.nodemgr.util.test;

import org.sri.nodeservice.core.nodedef.service.model.LanguageDefTO;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.util.transform.LanguageLoader;
import org.sri.nodeservice.core.nodemgr.util.transform.NodeDefTOTransformer;

public abstract class BaseTstFixture {

	//private IValidationReportFactory reportFactory;

	private NodeDefMgr nodeDefMgr;

	private NodeDef nodeDef;

	public BaseTstFixture() {
	}

	public void init(String nodeType) {

		nodeDefMgr = NodeDefMgr.createDefaultInstance();

		LanguageDefTO langTO = this.getLanguageDefTO(nodeType);

		/*if (TstLanguageDefReader.SIMPLE_NODE_DEF.equals(nodeType)) {
			langTO = TstLanguageDefReader.generateSimpleLanguage();
		} else if (TstLanguageDefReader.SIMPLE_HIERACHY_DEF.equals(nodeType)) {
			langTO = TstLanguageDefReader.generateTwoLevelLanguage();
		}*/

		NodeDefTOTransformer trans = new NodeDefTOTransformer(nodeDefMgr.getTypeDefMgr());
		LanguageLoader loader = new LanguageLoader(trans, nodeDefMgr);
		loader.loadLanguage(langTO);

		this.nodeDef = nodeDefMgr.getNodeDefinition(nodeType);
	}

	public LanguageDefTO getLanguageDefTO(String nodeType) {
		LanguageDefTO langTO = null;
		if (TstLanguageDefReader.SIMPLE_NODE_DEF.equals(nodeType)) {
			langTO = TstLanguageDefReader.generateSimpleLanguage();
		} else if (TstLanguageDefReader.SIMPLE_HIERACHY_DEF.equals(nodeType)) {
			langTO = TstLanguageDefReader.generateTwoLevelLanguage();
		}
		return langTO;
	}

	/*public NodeDefINodeValidator getValidator() {
		NodeDefINodeValidator validator = new NodeDefINodeValidator(this.getNodeDefMgr());
		return validator;
	}*/

	public abstract INode generateDefaultNode();

	//@Deprecated
	//public abstract INodeFactory getNodeFactory();
	
	public abstract INodeStore getNodeStore();

	//public abstract IValidationCallback getValidationCallback();

	public abstract INode retrieveNode();

	public NodeDefMgr getNodeDefMgr() {
		return nodeDefMgr;
	}

	public NodeDef getNodeDef() {
		return nodeDef;
	}

	//@Deprecated
	//public abstract IValidationReportFactory getValidationReportFactory();
}