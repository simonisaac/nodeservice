package org.sri.nodeservice.core.nodemgr.nodedef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeExDef;

/**
 * @author sisaac
 *
 */
public class TypeDefMgr {

	//private static TypeDefMgr theInstance;

	private Map<String, TypeDef> typeDefMap = new HashMap<String, TypeDef>();
	
	private Map<String, TypeConstraintDef> typeConstraintDefMap = new HashMap<String, TypeConstraintDef>();

	//public static final String STRING = "STRING";
	//public static final String LONG = "LONG";

	//-------------------------------------------------------------------------
	//
	//

	public void RegisterTypeDef(TypeDef typeDef) {
		if (this.typeDefMap.get(typeDef.getId()) != null) {
			throw new RuntimeException("Attribute Type Definition with id ["
					+ typeDef.getId() + "] already registered.");
		}
		this.typeDefMap.put(typeDef.getId(), typeDef);
	}

	public TypeDef getTypeDef(String typeName) {
		TypeDef rTypeDef = this.typeDefMap.get(typeName);
		if (rTypeDef == null) {
			throw new RuntimeException("No type definition for name [" + typeName + "]");
		}
		return rTypeDef;
	}

	public TypeConstraintDef getNewConstraint(String name) {
		TypeConstraintDef base = this.typeConstraintDefMap.get(name);
		TypeConstraintDef newC = null;
		try {
			newC = (TypeConstraintDef)base.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return newC;
	}

	public void registerTypeDefs(List<TypeDef> newTypes) {
		for (TypeDef typeDef : newTypes) {
			this.RegisterTypeDef(typeDef);
		}
	}

	public void registerTypeExDef(TypeExDef typeEx) {
		// attempt get parent type.
		TypeDef baseType = this.getTypeDef(typeEx.getParentId());
		if (baseType == null) {
			throw new RuntimeException();
		}
		
		// attempt clone
		TypeDef newType;
		try {
			newType = (TypeDef)baseType.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		newType.setId(typeEx.getId());
		
		newType.addConstraints(typeEx.getConstraintDefs());
		
		this.RegisterTypeDef(newType);
	}

	public void registerTypeExDefs(List<TypeExDef> newTypes) {
		for (TypeExDef typeDef : newTypes) {
			this.registerTypeExDef(typeDef);
		}
	}

	public void registerConstraint(TypeConstraintDef typeConstraint) {
		if (this.typeDefMap.get(typeConstraint.getId()) != null) {
			throw new RuntimeException("Constraint with id ["
					+ typeConstraint.getId() + "] already registered.");
		}
		this.typeConstraintDefMap.put(typeConstraint.getId(), typeConstraint);
	}
}
