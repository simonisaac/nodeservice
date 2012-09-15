package org.sri.nodeservice.core.nodemgr.nodedef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodedef.service.model.NodeDefTO;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.BaseConstraints;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.BaseTypes;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.ExBaseTypes;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeExDef;
import org.sri.nodeservice.core.nodemgr.util.transform.NodeDefTOTransformer;

/**
 * @author sisaac
 *
 */
public class NodeDefMgr {

	public static final String DEFAULT_DELEGATE = "DEFAULT_DELEGATE";

	private Map<String, NodeDef> nodeDefinitionMap = new HashMap<String, NodeDef>();

	private TypeDefMgr typeDefMgr;

	private Map<String, NodeDefMgrDelegate> delegateMap = new HashMap<String, NodeDefMgrDelegate>();

	// -------------------------------------------------------------------------
	//
	//

	public NodeDef getNodeDefinition(String name) {
		NodeDef nodedef = this.nodeDefinitionMap.get(name);
		// TODO: need to add behaviour around caching here.
		if (nodedef == null) {
			nodedef = this.getNodeDefinitionInternal(name);

			// for now we will rely on delegates to do any required caching.
			// this.addNodeDefinition(nodedef);
		}
		return nodedef;
	}

	protected NodeDef getNodeDefinitionInternal(String name) {
		NodeDef rNodeDef = null;
		NodeDefMgrDelegate delegate = this.delegateMap.get(name);
		if (delegate == null) {
			delegate = this.delegateMap.get(DEFAULT_DELEGATE);
		}
		if (delegate != null) {
			NodeDefTO to = delegate.getNodeDefinitionTO(name);
			NodeDefTOTransformer trans = new NodeDefTOTransformer(this.typeDefMgr);
			rNodeDef = trans.transformNodeDefTO(to);
		}
		return rNodeDef;
	}

	public void registerLanguage(LanguageDef language) {

		for (TypeExDef typeEx : language.getTypeExDefs()) {
			this.typeDefMgr.registerTypeExDef(typeEx);
		}

		for (NodeDef nodeDef : language.getNodeDefs()) {
			this.addNodeDefinition(nodeDef);
		}
	}

	public void addNodeDefinitions(List<NodeDef> newNodes) {
		for (NodeDef nodeDef : newNodes) {
			this.addNodeDefinition(nodeDef);
		}
	}

	public void addNodeDefinition(NodeDef nodeDefinition) {
		this.nodeDefinitionMap.put(nodeDefinition.getNodeDefId(), nodeDefinition);
		nodeDefinition.setNodeDefMgr(this);
	}

	public TypeDefMgr getTypeDefMgr() {
		return typeDefMgr;
	}

	public void setTypeDefMgr(TypeDefMgr attrTypeDefMgr) {
		this.typeDefMgr = attrTypeDefMgr;
	}

	public void setDelegate(String nodeType, NodeDefMgrDelegate delegate) {
		this.delegateMap.put(nodeType, delegate);
	}
	
	/**
	 * @return the nodeDefinitionMap
	 */
	public Map<String, NodeDef> getNodeDefinitionMap() {
		return nodeDefinitionMap;
	}

	// -------------------------------------------------------------------------
	//
	//

	public static NodeDefMgr createDefaultInstance() {
		// Initialise attr mgr with base types.
		TypeDefMgr attrTypeMgr = new TypeDefMgr();
		BaseConstraints.registerTypes(attrTypeMgr);
		BaseTypes.registerTypes(attrTypeMgr);
		ExBaseTypes.registerTypes(attrTypeMgr);

		NodeDefMgr nodeDefMgr = new NodeDefMgr();
		nodeDefMgr.setTypeDefMgr(attrTypeMgr);

		return nodeDefMgr;
	}
}
