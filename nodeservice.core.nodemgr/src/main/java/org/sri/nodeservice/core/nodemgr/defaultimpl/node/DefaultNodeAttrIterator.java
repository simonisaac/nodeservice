package org.sri.nodeservice.core.nodemgr.defaultimpl.node;

import java.util.Iterator;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttrIterator;

public class DefaultNodeAttrIterator implements INodeAttrIterator {

	private Iterator<INodeAttr> itr;
	
	public DefaultNodeAttrIterator(Iterator<INodeAttr> itr) {
		super();
		this.itr = itr;
	}

	@Override
	public boolean hasNext() {
		return this.itr.hasNext();
	}

	@Override
	public INodeAttr next() {
		return this.itr.next();
	}

	@Override
	public void remove() {
		assert false;
	}

	@Override
	public Iterator<INodeAttr> iterator() {
		return this;
	}
}
