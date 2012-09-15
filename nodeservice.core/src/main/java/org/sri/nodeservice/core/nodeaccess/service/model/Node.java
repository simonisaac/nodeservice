package org.sri.nodeservice.core.nodeaccess.service.model;

import java.util.HashMap;
import java.util.Map;

import org.sri.nodeservice.core.nodeaccess.service.rsrq.ErrorDetail;
import org.sri.nodeservice.core.nodeaccess.service.rsrq.Errors;

/**
 * 
 * @author sisaac
 *
 */
public class Node {
	
	// id unique within the node store
	private String UID;
	
	// id unique within the childset
	private String	id;
	
	private String parentId;
	
	private String type = "Node";

	private boolean leaf;
	
	private Map<String, String> fields = new HashMap<String, String>();
	
	private Map<String, NodeSet> childSets = new HashMap<String, NodeSet>();
	
	private Errors errors;
	
	@SuppressWarnings("unused")
	private Node() {
	}
	
	@Deprecated
	public Node(String id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public Node(String UID, String id, String type) {
		this.UID = UID;
		this.id = id;
		this.type = type;
	}
	
	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getId() {
		return id;
	}

	public void setId(String path) {
		this.id = path;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setField(String fieldName, String fieldValue) {
		this.fields.put(fieldName, fieldValue);
	}
	
	public String getFieldValue(String fieldName) {
		return this.fields.get(fieldName);
	}
	
	/**
	 * @param fields the fields to set
	 */
	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public NodeSet getChildNodeSet(String id) {
		return this.childSets.get(id);
	}
	
	public void addChildNodeSet (NodeSet childNodeSet) {
		this.childSets.put(childNodeSet.getId(), childNodeSet);
	}
	
	public Errors getErrors() {
		return errors;
	}
	
	public Errors addErrorDetail(ErrorDetail errorDetail){
		if(errors == null){
			errors = new Errors();
		}
		errors.addErrorDetail(errorDetail);
		return errors;
	}
	
	public Map<String, NodeSet> getChildSets() {
		return childSets;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}