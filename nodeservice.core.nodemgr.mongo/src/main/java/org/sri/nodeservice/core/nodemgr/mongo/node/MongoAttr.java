package org.sri.nodeservice.core.nodemgr.mongo.node;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;

public class MongoAttr implements INodeAttr {

	private String id;
	
	private String value;

	public MongoAttr(String attrId, String value) {
		this.id = attrId;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
