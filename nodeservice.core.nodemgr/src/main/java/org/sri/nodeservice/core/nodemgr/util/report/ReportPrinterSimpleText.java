package org.sri.nodeservice.core.nodemgr.util.report;

import org.sri.nodeservice.core.nodemgr.validation.report.Problem;

/**
 * 
 * @author sisaac
 *
 */
public class ReportPrinterSimpleText {

	private StringBuilder bil = new StringBuilder();

	private int indentCount = 0;

	private int indentChange = 5;

	//public void fullDetail(ValidationReport report) {
		/*this.incrementIndent();
		this.printElement(report);
		this.decrementIndent();
		{
			for (List<Problem> probList : report.getProblemsByType().values()) {
				for (Problem problem : probList) {
					this.printElement(problem);
				}
			}
		}
		{

			ReportCollection attributeReports = report.getChildReportCollection(NodeDef.ATTR_REPORT);
			if (attributeReports != null) {
				this.printnl("- Attribute Reports: ");
				this.incrementIndent();
				for (ValidationReport attributeReport : attributeReports.getReports()) {
					this.printnl("");
					this.printElement(attributeReport);
					this.incrementIndent();
					for (Problem curProblem : attributeReport.getProblems()) {
						this.printElement(curProblem);
					}
					this.decrementIndent();
				}
				this.decrementIndent();
				this.printnl("");
				this.printnl("");
			}
		}
		{
			ReportCollection childSetReports = report.getChildReportCollection(NodeDef.NODESET_REPORT);
			if (childSetReports != null) {
				this.printnl("- Child Set Reports: ");
				this.incrementIndent();
				for (ValidationReport childSetReport : childSetReports.getReports()) {
					// display the childset
					this.printnl("");
					this.printElement(childSetReport);

					// now do the childset problemse
					this.incrementIndent();
					for (Problem curProblem : childSetReport.getProblems()) {
						this.printElement(curProblem);
					}
					this.decrementIndent();

					// now the child nodes
					this.printnl("- Child Node Reports: ");
					this.incrementIndent();
					this.printnl("");
					ReportCollection childNodeReports = childSetReport.getChildReportCollection(NodeDef.NODE_REPORT);
					for (ValidationReport childNodeReport : childNodeReports.getReports()) {
						this.fullDetail(childNodeReport);
					}
					this.decrementIndent();
				}
				this.decrementIndent();
			}
		}*/
	//S}

	//public void printElement(ValidationReport report) {

		/*this.print("#ReportType [" + report.getEntityType() + "]");
		this.print(" EntityId [" + report.getEntityId() + "]");
		this.print(" Valid [" + report.isValid() + "]");
		this.print(" Worst problem[" + report.getWorstProblem() + "]");
		this.print(" Problem Count [" + report.getProblemCount() + "]");
		this.print(" Child Problem Count[" + report.getChildProblemCount() + "]");*/
	//}

	public void printElement(Problem problem) {

		this.printnl("Problem:");
		this.print("  Id [" + problem.getId() + "]");
		this.print(" Severity [" + problem.getSeverity() + "]");
		this.printnl("  Message: " + problem.getMessage() + "");
		this.printnl("  Detail:" + problem.getDetail() + "");
	}

	private void print(Object s) {
		this.bil.append(s);
	}

	private void printnl(Object s) {
		this.bil.append("\n");
		this.bil.append(this.getIndent());
		this.print(s);
	}

	public String getString() {
		return this.bil.toString();
	}

	public void incrementIndent() {
		this.incrementIndent(this.indentChange);
	}

	public void decrementIndent() {
		this.decrementIndent(this.indentChange);
	}

	private void incrementIndent(int increment) {
		this.indentCount += increment;
	}

	private void decrementIndent(int decrement) {
		this.indentCount -= decrement;
	}

	public String getIndent() {
		StringBuilder bil = new StringBuilder();
		for (int i = 0; i < this.indentCount; i++) {
			bil.append(" ");
		}
		return bil.toString();
	}
}
