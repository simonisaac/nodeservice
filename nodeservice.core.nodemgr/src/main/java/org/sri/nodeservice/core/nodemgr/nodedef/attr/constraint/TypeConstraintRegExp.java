package org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint;

import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodemgr.validation.report.IValidationProblemReporter;
/**
 * @author sisaac
 *
 */
public class TypeConstraintRegExp extends TypeConstraintDef {
	
	private int minLength;
	
	public TypeConstraintRegExp(String id) {
		super(id);
	}

	@Override
	public void apply(Object value, IValidationProblemReporter callback) {
		/*if (!(value instanceof String)) {
			errors.add(new ProblemDetail(ProblemSeverity.ERROR, this.getId(), "Not a valid String [" + value + "]", ""));
			return;
		}
		
		String stringValue = (String)value;
		if (stringValue.length() < this.minLength) {
			errors.add(new ProblemDetail(ProblemSeverity.ERROR, this.getId(), "Value [" + value + "] is more than " + this.minLength + " characters long", ""));
		}*/
		throw new RuntimeException("not impl");
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	@Override
	public void initFromParams(Map<String, TypeConstraintParameterDef> params) {
		// 
		
	}

	@Override
	public Map<String, TypeConstraintParameterDef> serialiseToParams() {
		// 
		return null;
	}

	@Override
	public List<String> getApplicableTypes() {
		// 
		return null;
	}
}
