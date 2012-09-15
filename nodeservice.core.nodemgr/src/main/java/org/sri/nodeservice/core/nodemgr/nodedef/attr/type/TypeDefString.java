package org.sri.nodeservice.core.nodemgr.nodedef.attr.type;

import org.sri.nodeservice.core.nodemgr.validation.report.IValidationProblemReporter;
/**
 * @author sisaac
 *
 */
public class TypeDefString extends TypeDef {

	public TypeDefString(String id) {
		super(id);
	}

	@Override
	protected Object transformToObjectValueInternal(String stringAttrValue,
			IValidationProblemReporter callback) {
		
		return stringAttrValue;
	}
}
