package org.sri.nodeservice.core.nodemgr.nodedef.attr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeDef;

/**
 * This class is used to define an attribute of a node.
 * 
 * @author sisaac
 *
 */
public class AttrDef {

	private String id;

	private TypeDef type;

	private String label;

	private String defaultValue;

	private boolean mandatory = true;

	private boolean readonly = false;

	private List<TypeConstraintDef> constraints = new ArrayList<TypeConstraintDef>();

	//-------------------------------------------------------------------------
	// 
	//
	
	public AttrDef(String name, TypeDef type, String label, String defaultValue, boolean mandatory, boolean readonly) {
		super();
		this.id = name;
		this.type = type;
		this.label = label;
		this.defaultValue = defaultValue;
		this.mandatory = mandatory;
		this.readonly = readonly;
	}

	//-------------------------------------------------------------------------
	// 
	//

	/*@Deprecated
	public void validateStringValue(String stringAttrValue, IValidationCallback builder) {

		// first check the underlying type.
		//Object objectValue = this.type.validateStringValue(stringAttrValue, builder);
		assert false;
		//RichAttr newAttr = new RichAttr(this, objectValue);
		//builder.pushRichObject(newAttr);
		
		// now check this attributes constraints.
		for (TypeConstraintDef curConstraint : this.constraints) {
			//curConstraint.apply(objectValue, builder);
		}
	}*/

	//-------------------------------------------------------------------------
	// 
	//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TypeDef getType() {
		return type;
	}

	public void setType(TypeDef type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
	
	public void addTypeContstraintDef (TypeConstraintDef cDef) {
		this.constraints.add(cDef);
	}
	
	public List<TypeConstraintDef> getTypeConstraintList () {
		return Collections.unmodifiableList(this.constraints);
	}
}
