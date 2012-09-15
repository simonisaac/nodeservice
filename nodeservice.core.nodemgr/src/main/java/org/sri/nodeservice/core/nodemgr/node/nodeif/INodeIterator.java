package org.sri.nodeservice.core.nodemgr.node.nodeif;

import java.util.Iterator;

public interface INodeIterator extends Iterable<INode>, Iterator<INode> {

	@Override
	public Iterator<INode> iterator();

}
