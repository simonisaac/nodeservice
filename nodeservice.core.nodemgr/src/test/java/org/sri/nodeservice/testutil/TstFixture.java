package org.sri.nodeservice.testutil;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodedef.service.model.LanguageDefTO;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.util.test.TstLanguageDefReader;
import org.sri.nodeservice.core.nodemgr.util.transform.LanguageLoader;
import org.sri.nodeservice.core.nodemgr.util.transform.NodeDefTOTransformer;

@Deprecated
public class TstFixture {

	private NodeDefMgr nodeDefMgr;
	
	private NodeDef nodeDef;
	
	private Node defaultNode;

	public TstFixture(String nodeType) {
		nodeDefMgr = NodeDefMgr.createDefaultInstance();

		LanguageDefTO langTO = null;
		if (TstLanguageDefReader.SIMPLE_NODE_DEF.equals(nodeType)) {
			langTO = TstLanguageDefReader.generateSimpleLanguage();
		}
		else if (TstLanguageDefReader.SIMPLE_HIERACHY_DEF.equals(nodeType)) {
			langTO = TstLanguageDefReader.generateTwoLevelLanguage();
		}

		NodeDefTOTransformer trans = new NodeDefTOTransformer(nodeDefMgr.getTypeDefMgr());
		LanguageLoader loader = new LanguageLoader(trans, nodeDefMgr);
		loader.loadLanguage(langTO);
		
		this.nodeDef = nodeDefMgr.getNodeDefinition(nodeType);
		//this.defaultNode = this.nodeDef.getDefaultNode("123");
	}

	public NodeDefMgr getNodeDefMgr() {
		return nodeDefMgr;
	}

	public NodeDef getNodeDef() {
		return nodeDef;
	}

	public Node getDefaultNode() {
		return defaultNode;
	}
}
