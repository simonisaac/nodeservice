package org.sri.nodeservice.core.nodemgr.defaultimpl.validation;

import java.util.Iterator;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.validation.report.IReportIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.IValidationReport;

public class DefaultReportIterator implements IReportIterator {

	//private Iterator<IValidationReport> itr;
	
	private INodeIterator nodeItr;

	/*public DefaultReportIterator(Iterator<IValidationReport> itr) {
		super();
		delete this
		this.itr = itr;
	}*/

	public DefaultReportIterator(INodeSet childReportSet) {
		this.nodeItr = childReportSet.getINodeIterator();
	}

	@Override
	public boolean hasNext() {
		return this.nodeItr.hasNext();
	}

	@Override
	public IValidationReport next() {
		INode node = this.nodeItr.next();
		DefaultValidationReport report = new DefaultValidationReport(node);
		return report;
	}

	@Override
	public void remove() {
		this.iterator().remove();
	}

	@Override
	public Iterator<IValidationReport> iterator() {
		return this;
	}
}
