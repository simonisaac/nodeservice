package org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint;

import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodemgr.validation.report.IValidationProblemReporter;

/**
 * @author sisaac
 *
 */
public abstract class TypeConstraintDef implements Cloneable {

	public String id;
	//public List<String> applicableTypes = new ArrayList<String>();
	//public Map<String, AttrConstraintParameter> parameterList = new HashMap<String, AttrConstraintParameter>();

	//-------------------------------------------------------------------------
	// 
	//

	public TypeConstraintDef(String id) {
		this.id = id;
	}
	
	//-------------------------------------------------------------------------
	// 
	//
	
	public abstract void apply (Object value, IValidationProblemReporter iValidationReport);
	
	public abstract void initFromParams(Map<String, TypeConstraintParameterDef> params);
	
	public abstract Map<String, TypeConstraintParameterDef> serialiseToParams();
	
	public abstract List<String> getApplicableTypes();

	//-------------------------------------------------------------------------
	// 
	//
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		TypeConstraintDef clone = (TypeConstraintDef)super.clone();
		clone.initFromParams(this.serialiseToParams());
		return clone;
	}
	
	//-------------------------------------------------------------------------
	// 
	//
	
	public String getId() {
		return id;
	}

	public void setId(String name) {
		this.id = name;
	}

	/**************************************************************************
	/**
	 * 
	 * @author sisaac
	 *
	 */
	public static class TypeConstraintParameterDef {
		private String name;
		private String value;

		public TypeConstraintParameterDef() {
		}

		public TypeConstraintParameterDef(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
