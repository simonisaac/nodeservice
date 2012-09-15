package org.sri.nodeservice.core.nodemgr.defaultimpl.validation;

import java.util.Iterator;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.validation.report.IProblemIterator;
import org.sri.nodeservice.core.nodemgr.validation.report.Problem;

public class DefaultProblemIterator implements IProblemIterator {
	
	private INodeIterator nodeItr;
	
	//public DefaultProblemIterator(Iterator<Problem> itr) {
	//	super();
	//	this.itr = itr;
	//}

	public DefaultProblemIterator(INodeSet nodeSet) {
		this.nodeItr = nodeSet.getINodeIterator();
	}

	@Override
	public boolean hasNext() {
		return this.nodeItr.hasNext();
	}

	@Override
	public Problem next() {
		INode node = this.nodeItr.next();
		Problem problem = new Problem(node);
		return problem;
	}

	@Override
	public void remove() {
		this.iterator().remove();
	}

	@Override
	public Iterator<Problem> iterator() {
		return this;
	}
}
