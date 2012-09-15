package org.sri.nodeservice.core.nodemgr.nodedef.attr.type;

import java.util.ArrayList;
import java.util.List;

import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef;
/**
 * @author sisaac
 *
 */
public class TypeExDef {
	
	private String id;
	
	private String parentId;
	
	private List<TypeConstraintDef> constraints = new ArrayList<TypeConstraintDef>();
	
	public TypeExDef () {
		
	}
	
	public TypeExDef(String id, String parentId) {
		this.id = id;
		this.parentId = parentId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String name) {
		this.id = name;
	}

	public void setParentId(String string) {
		this.parentId = string;
		
	}

	public List<TypeConstraintDef> getConstraintDefs() {
		return this.constraints;
	}

	public String getParentId() {
		return parentId;
	}
}
