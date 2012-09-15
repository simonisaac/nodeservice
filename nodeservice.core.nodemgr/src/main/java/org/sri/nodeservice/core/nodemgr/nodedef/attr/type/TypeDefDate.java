package org.sri.nodeservice.core.nodemgr.nodedef.attr.type;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sri.nodeservice.core.nodemgr.validation.report.IValidationProblemReporter;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;

/**
 * @author sisaac
 * 
 */
public class TypeDefDate extends TypeDef {

	private static List<DateFormat> supportedDateFormats = new ArrayList<DateFormat>();

	static {
		supportedDateFormats.add(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"));
	}

	public TypeDefDate(String id) {
		super(id);
	}

	@Override
	protected Object transformToObjectValueInternal(String stringAttrValue, IValidationProblemReporter callback) {
		Date rObject = null;

		for (DateFormat df : supportedDateFormats) {
			try {
				rObject = df.parse(stringAttrValue);
				break;
			} catch (ParseException pe) {
				// nothing to do here
			}
		}
		if (rObject == null) {
			String message = "Value does not represent a valid number of type 'DATE' [" + stringAttrValue + "]";
			callback.registerProblem(ProblemSeverity.ERROR, Problem.ATTR_TYPE, message, message);
		}

		return rObject;
	}

}
