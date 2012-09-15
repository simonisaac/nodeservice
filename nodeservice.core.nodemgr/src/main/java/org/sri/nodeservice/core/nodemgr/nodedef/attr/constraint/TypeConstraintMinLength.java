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
public class TypeConstraintMinLength extends TypeConstraintDef {

	public static String ID = "MIN_LENGTH";

	public static String PARAM_MIN_LENGTH = "minLength";

	private int minLength;

	public TypeConstraintMinLength() {
		super(ID);
	}

	@Override
	public void apply(Object value, IValidationProblemReporter callback) {
		if (!(value instanceof String)) {
			String message = "Not a valid String [" + value + "]";
			//Problem problem = new Problem(ProblemSeverity.ERROR, Problem.CONSTRAINT, message, message);
			callback.registerProblem(ProblemSeverity.ERROR, Problem.CONSTRAINT, message, message);

			
			//callback.addProblem(Problem.CONSTRAINT, ProblemSeverity.ERROR, message, message);
			//errors.add(new ProblemDetail(ProblemSeverity.ERROR, this.getId(), "Not a valid String [" + value + "]", ""));
			return;
		}

		String stringValue = (String) value;
		if (stringValue.length() < this.minLength) {
			String message = "Value [" + value + "] is less than " + this.minLength + " characters long";
			//Problem problem = new Problem(ProblemSeverity.ERROR, Problem.CONSTRAINT, message, message);
			callback.registerProblem(ProblemSeverity.ERROR, Problem.CONSTRAINT, message, message);

			
			//callback.addProblem(Problem.CONSTRAINT, ProblemSeverity.ERROR, message, message);
			//errors.add(new ProblemDetail(ProblemSeverity.ERROR, this.getId(), "Value [" + value + "] is less than " + this.minLength
			//		+ " characters long", ""));
		}
	}

	@Override
	public void initFromParams(Map<String, TypeConstraintParameterDef> params) {
		TypeConstraintParameterDef maxLengthParam = params.get(PARAM_MIN_LENGTH);
		if (maxLengthParam == null) {
			throw new RuntimeException();
		}
		this.minLength = Integer.parseInt(maxLengthParam.getValue());
	}

	@Override
	public Map<String, TypeConstraintParameterDef> serialiseToParams() {
		Map<String, TypeConstraintParameterDef> rParams = new HashMap<String, TypeConstraintDef.TypeConstraintParameterDef>();
		rParams.put(PARAM_MIN_LENGTH, new TypeConstraintParameterDef(PARAM_MIN_LENGTH, String.valueOf(this.minLength)));
		return rParams;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	@Override
	public List<String> getApplicableTypes() {
		// 
		return null;
	}
}
