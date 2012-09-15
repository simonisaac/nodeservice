package org.sri.nodeservice.core.nodemgr.defaultimpl.node;

import java.util.Iterator;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;

public class DefaultNodeIterator implements INodeIterator {

	private Iterator<INode> itr;
	
	public DefaultNodeIterator(Iterator<INode> itr) {
		super();
		this.itr = itr;
	}

	@Override
	public boolean hasNext() {
		return this.itr.hasNext();
	}

	@Override
	public INode next() {
		return this.itr.next();
	}

	@Override
	public void remove() {
		this.iterator().remove();
	}

	@Override
	public Iterator<INode> iterator() {
		return this;
	}
}
