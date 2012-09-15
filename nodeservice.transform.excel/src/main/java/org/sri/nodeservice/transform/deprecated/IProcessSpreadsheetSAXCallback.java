package org.sri.nodeservice.transform.deprecated;


import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;

public interface IProcessSpreadsheetSAXCallback {

	public void createRootNode(INode newNode);
	
	public void addChildNode(String childsetName, INode newNode);

	public abstract INode getRootNode();

}
