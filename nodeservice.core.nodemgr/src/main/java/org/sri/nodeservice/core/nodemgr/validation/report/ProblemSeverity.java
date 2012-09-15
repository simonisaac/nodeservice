package org.sri.nodeservice.core.nodemgr.validation.report;
/**
 * @author sisaac
 *
 */
public enum ProblemSeverity {
	ERROR(3), WARNING(2), INFO(1), ZERO(0);

	private int level;

	ProblemSeverity(int level) {
		this.level = level;
	}

	public boolean worseThan(ProblemSeverity ps) {
		boolean r = false;
		if (this.level > ps.level) {
			r = true;
		}
		return r;
	}

	public boolean isFatal() {
		boolean r = false;
		if (this.level == 3) {
			r = true;
		}
		return r;
	}
	
	public int getLevel () {
		return this.level;
	}
	
	public static ProblemSeverity fromLevel (int level) {
		ProblemSeverity rSeverity = null;
		
		switch (level) {
		case 0:
			rSeverity = ProblemSeverity.ZERO;
			break;
		case 1:
			rSeverity = ProblemSeverity.INFO;
			break;
		case 2:
			rSeverity = ProblemSeverity.WARNING;
			break;
		case 3:
			rSeverity = ProblemSeverity.ERROR;
			break;
		}
		
		return rSeverity;
	}
}
