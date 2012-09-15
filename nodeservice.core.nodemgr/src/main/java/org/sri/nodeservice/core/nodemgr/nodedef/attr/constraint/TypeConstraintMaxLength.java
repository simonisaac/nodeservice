package org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodemgr.validation.report.IValidationProblemReporter;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;
import org.sri.nodeservice.core.nodemgr.validation.report.ProblemSeverity;
/**
 * @author sisaac
 *
 */
public class TypeConstraintMaxLength extends TypeConstraintDef {

	private int maxLength;

	public static String PARAM_MAX_LENGTH = "maxLength";

	public static String ID = "MAX_LENGTH";

	public TypeConstraintMaxLength() {
		super(ID);
	}

	@Override
	public void apply(Object value, IValidationProblemReporter callback) {
		if (!(value instanceof String)) {
			String message = "Not a valid String [" + value + "]";
			//Problem problem = new Problem(ProblemSeverity.ERROR, Problem.CONSTRAINT, message, message);
			callback.registerProblem(ProblemSeverity.ERROR, Problem.CONSTRAINT, message, message);
			//callback.addProblem(Problem.CONSTRAINT, ProblemSeverity.ERROR, message, message);
			//errors.add(new ProblemDetail(ProblemSeverity.ERROR, this.getId(), "Not a valid String ["
			//		+ value + "]", ""));
			return;
		}

		String stringValue = (String) value;
		if (stringValue.length() > this.maxLength) {
			String message = "Value [" + value + "] is more than " + this.maxLength + " characters long";
			//Problem problem = new Problem(ProblemSeverity.ERROR, Problem.CONSTRAINT, message, message);
			callback.registerProblem(ProblemSeverity.ERROR, Problem.CONSTRAINT, message, message);

			//callback.addProblem(Problem.CONSTRAINT, ProblemSeverity.ERROR, message, message);
			//errors.add(new ProblemDetail(ProblemSeverity.ERROR, this.getId(), "Value [" + value + "] is more than " + this.maxLength
			//		+ " characters long", ""));
		}
	}

	@Override
	public void initFromParams(Map<String, TypeConstraintParameterDef> params) {

		TypeConstraintParameterDef maxLengthParam = params.get(PARAM_MAX_LENGTH);
		if (maxLengthParam == null) {
			throw new RuntimeException();
		}
		this.maxLength = Integer.parseInt(maxLengthParam.getValue());
	}

	@Override
	public Map<String, TypeConstraintParameterDef> serialiseToParams() {
		Map<String, TypeConstraintParameterDef> rParams = new HashMap<String, TypeConstraintDef.TypeConstraintParameterDef>();
		rParams.put(PARAM_MAX_LENGTH, new TypeConstraintParameterDef(PARAM_MAX_LENGTH, String.valueOf(this.maxLength)));
		return rParams;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int minLength) {
		this.maxLength = minLength;
	}

	@Override
	public List<String> getApplicableTypes() {
		return null;
	}
}
