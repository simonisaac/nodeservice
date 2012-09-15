package org.sri.nodeservice.core.nodemgr.nodedef.attr.type;

import java.util.ArrayList;
import java.util.List;

import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrValidationException;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationProblemReporter;

/**
 * @author sisaac
 * 
 */
public abstract class TypeDef implements Cloneable {

	private String id;

	private List<TypeConstraintDef> constraints = new ArrayList<TypeConstraintDef>();

	// -------------------------------------------------------------------------
	//
	//

	public TypeDef(String id) {
		this.id = id;
	}

	// -------------------------------------------------------------------------
	//
	//

	public Object validateStringValue(String stringAttrValue, IValidationProblemReporter iValidationReport) {

		// check the basic type.
		Object objectValue = this.transformToObjectValueInternal(stringAttrValue, iValidationReport);

		// if type ok apply the type constraints.
		if (objectValue != null) {

			for (TypeConstraintDef curConstraint : this.constraints) {
				curConstraint.apply(objectValue, iValidationReport);
			}
		}

		return objectValue;
	}

	/**
	 * Use this method to register problems so that correct exception is thrown if
	 * there is no validation report to use.
	 * 
	 * @param iValidationReport
	 * @param error
	 * @param string
	 * @param message
	 * @param detail
	 * @throws AttrValidationException 
	 *
	protected void registerProblem(IValidationReport iValidationReport, ProblemSeverity error, String string,
			String message, String detail) throws AttrValidationException {
		if (iValidationReport != null) {
			iValidationReport.registerProblem(error, string, message, detail);
		}
		else {
			throw new AttrValidationException(message);
		}
	}*/

	@Override
	public Object clone() throws CloneNotSupportedException {
		TypeDef td = (TypeDef) super.clone();
		List<TypeConstraintDef> cloneConstraints = new ArrayList<TypeConstraintDef>();
		for (TypeConstraintDef typeConstraintDef : this.constraints) {
			cloneConstraints.add(typeConstraintDef);
		}
		td.setConstraints(cloneConstraints);
		return td;
	}

	/**
	 * @param stringAttrValue
	 * @param builder
	 *            in/out parameter to have errors added to it.
	 * @return
	 */
	protected abstract Object transformToObjectValueInternal(String stringAttrValue, IValidationProblemReporter builder);

	// -------------------------------------------------------------------------
	//
	// Get and Set

	public String getId() {
		return id;
	}

	public void setId(String name) {
		this.id = name;
	}

	public List<TypeConstraintDef> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<TypeConstraintDef> constraints) {
		this.constraints = constraints;
	}

	public void addConstraints(List<TypeConstraintDef> constraintDefs) {
		this.constraints.addAll(constraintDefs);
	}
}
