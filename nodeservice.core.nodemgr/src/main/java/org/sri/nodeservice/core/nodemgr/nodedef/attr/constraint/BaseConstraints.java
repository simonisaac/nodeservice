package org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint;

import org.sri.nodeservice.core.nodemgr.nodedef.TypeDefMgr;
/**
 * @author sisaac
 *
 */
public class BaseConstraints {
	
	//public static final String MAX_LENGHT = "MAX_LENGHT";
	public static final String REG_EX = "REG_EX";
	//public static final String MIN_LENGTH = "MIN_LENGTH";

	// -------------------------------------------------------------------------
	//
	//

	public static void registerTypes(TypeDefMgr typeDefMgr) {

		typeDefMgr.registerConstraint(new TypeConstraintMinLength());
		typeDefMgr.registerConstraint(new TypeConstraintMaxLength());
		typeDefMgr.registerConstraint(new TypeConstraintRegExp(REG_EX));
	}
}
