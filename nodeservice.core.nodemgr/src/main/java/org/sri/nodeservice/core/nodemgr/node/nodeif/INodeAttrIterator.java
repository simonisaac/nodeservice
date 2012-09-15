package org.sri.nodeservice.core.nodemgr.node.nodeif;

import java.util.Iterator;

public interface INodeAttrIterator extends Iterable<INodeAttr>, Iterator<INodeAttr> {

	@Override
	public Iterator<INodeAttr> iterator();

}
