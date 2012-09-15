package org.sri.nodeservice.web.nodedef.model;

import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.BaseTypes;

public class UIAttributeDefinition {

	private AttrDef attributeDefinition;

	public UIAttributeDefinition(AttrDef attributeDefinition) {
		super();
		this.attributeDefinition = attributeDefinition;
	}

	public AttrDef getAttributeDefinition() {
		return attributeDefinition;
	}

	public void setAttributeDefinition(AttrDef attributeDefinition) {
		this.attributeDefinition = attributeDefinition;
	}

	public String getId() {
		String rString = "";

		rString = attributeDefinition.getId();

		return rString;
	}

	public String getXtype() {
		String rString = "textfield";

		if (BaseTypes.LONG.equals(attributeDefinition.getType())) {
			rString = "textfield";
		} else if (BaseTypes.STRING.equals(attributeDefinition.getType())) {
			rString = "textfield";
		} else if (attributeDefinition.getId().equals("id")) {
			rString = "hidden";
		}

		return rString;
	}

	public String getLabel() {
		String rString = "";

		rString = this.attributeDefinition.getLabel();

		return rString;
	}

	public String getDefaultValue() {
		return this.attributeDefinition.getDefaultValue();
	}
}
