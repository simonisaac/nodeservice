package org.sri.nodeservice.core.nodemgr.nodedef;

import java.util.List;

import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeExDef;
/**
 * @author sisaac
 *
 */
public class LanguageDef {
	
	private List<TypeExDef> typeExDefs;
	
	private List<NodeDef> nodeDefs;

	public void setTypeExDefs(List<TypeExDef> newTypes) {
		this.typeExDefs = newTypes;
	}

	public void setNodeDefs(List<NodeDef> newNodes) {
		this.nodeDefs = newNodes;
		
	}

	public List<TypeExDef> getTypeExDefs() {
		return typeExDefs;
	}

	public List<NodeDef> getNodeDefs() {
		return nodeDefs;
	}
}
