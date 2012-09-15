package org.sri.nodeservice.transform.processing;

import java.io.File;

import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;

public interface IProcessor {

	public abstract INode process(INodeSet resultsNodeSet, File spreadsheetFile, INodeStore defaults);

}