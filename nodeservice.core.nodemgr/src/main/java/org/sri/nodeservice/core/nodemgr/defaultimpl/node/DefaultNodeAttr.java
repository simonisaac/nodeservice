package org.sri.nodeservice.core.nodemgr.defaultimpl.node;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;

/**
 * @author sisaac
 *
 */
public class DefaultNodeAttr implements INodeAttr {

	private String id;

	//private AttrDef attrDef;

	private String value;

	public DefaultNodeAttr(String id, String value) {
		this.id = id;
		this.value = value;
	}

	/*public RichAttr(AttrDef attrDef, String value) {
		this.attrDef = attrDef;
		assert this.attrDef != null;
		this.id = attrDef.getId();
		this.value = value;
	}*/

	/*public AttrDef getAttrDef() {
		return attrDef;
	}*/

	/*public void setAttrDef(AttrDef attrDef) {
		this.attrDef = attrDef;
	}*/

	@Override
	public String getValue() {
		//return value.toString();
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getId() {
		return this.id;
	}
}
