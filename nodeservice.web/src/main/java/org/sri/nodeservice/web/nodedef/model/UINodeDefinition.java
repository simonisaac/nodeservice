package org.sri.nodeservice.web.nodedef.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;

public class UINodeDefinition {
	private NodeDef nodeDef;

	public UINodeDefinition(NodeDef nodeDef) {
		super();
		this.nodeDef = nodeDef;
	}

	public Collection<UIAttributeDefinition> getAttributes() {
		//Collection<AttrDefTO> attrDefs = this.nodeDef.getAttributes().values();
		List<UIAttributeDefinition> rList = new ArrayList<UIAttributeDefinition>();
		for (AttrDef curAttr : this.nodeDef.getAttrDefList()) {
			rList.add(new UIAttributeDefinition(curAttr));
		}
		return rList;
	}

}
