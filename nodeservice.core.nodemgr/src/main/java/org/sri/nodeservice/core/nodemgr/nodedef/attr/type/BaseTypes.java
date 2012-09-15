package org.sri.nodeservice.core.nodemgr.nodedef.attr.type;

import org.sri.nodeservice.core.nodemgr.nodedef.TypeDefMgr;
/**
 * @author sisaac
 *
 */
public class BaseTypes {
	
	public static final String STRING = "STRING";
	public static final String LONG = "LONG";
	public static final String CURRENCY = "CURRENCY";
	public static final String DATE = "DATE";

	// -------------------------------------------------------------------------
	//
	//

	public static void registerTypes(TypeDefMgr typeDefMgr) {
		// register primitive types.
		typeDefMgr.RegisterTypeDef(new TypeDefString(STRING));
		typeDefMgr.RegisterTypeDef(new TypeDefLong(LONG));
		typeDefMgr.RegisterTypeDef(new TypeDefLong("CURRENCY"));
		typeDefMgr.RegisterTypeDef(new TypeDefDate("DATE"));
	}
}
