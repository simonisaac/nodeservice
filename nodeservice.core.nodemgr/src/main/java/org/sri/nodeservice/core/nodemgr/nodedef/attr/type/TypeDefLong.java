package org.sri.nodeservice.core.nodemgr.nodedef.attr.type;

import org.sri.nodeservice.core.nodemgr.validation.report.IValidationProblemReporter;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;
/**
 * @author sisaac
 *
 */
public class TypeDefLong extends TypeDef {

	public TypeDefLong(String id) {
		super(id);
	}

	@Override
	protected Object transformToObjectValueInternal(String stringAttrValue, IValidationProblemReporter callback) {
		Long rObject = null;

		try {
			rObject = Long.parseLong(stringAttrValue);
		} catch (Throwable t) {
			String message = "Value does not represent a valid number of type 'Long' [" + stringAttrValue + "]";
			callback.registerProblem(ProblemSeverity.ERROR, Problem.ATTR_TYPE, message, message);
		}

		return rObject;
	}
}
