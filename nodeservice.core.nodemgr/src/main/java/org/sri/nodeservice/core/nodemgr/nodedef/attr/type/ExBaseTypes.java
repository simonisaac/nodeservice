package org.sri.nodeservice.core.nodemgr.nodedef.attr.type;

import org.sri.nodeservice.core.nodemgr.nodedef.TypeDefMgr;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintMinLength;
/**
 * @author sisaac
 *
 */
public class ExBaseTypes {
	
	public static final String NON_EMPTY_STRING = "NON_EMPTY_STRING";

	// -------------------------------------------------------------------------
	//
	//

	public static void registerTypes(TypeDefMgr typeDefMgr) {
		// register custom types.
		TypeDefString nonEmptyString = new TypeDefString(NON_EMPTY_STRING);
		TypeConstraintMinLength minLength = (TypeConstraintMinLength)typeDefMgr.getNewConstraint(TypeConstraintMinLength.ID);
		minLength.setMinLength(1);
		typeDefMgr.RegisterTypeDef(nonEmptyString);
	}
}
