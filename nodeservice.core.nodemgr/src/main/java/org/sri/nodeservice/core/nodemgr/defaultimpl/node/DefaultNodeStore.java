package org.sri.nodeservice.core.nodemgr.defaultimpl.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodeaccess.service.model.Node;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeIterator;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;

public class DefaultNodeStore implements INodeStore {

	private Map<String, DefaultNode> allNodesByUID = new HashMap<String, DefaultNode>();

	private Map<String, List<DefaultNode>> nodesByType = new HashMap<String, List<DefaultNode>>();

	private List<DefaultNode> rootNodeList = new ArrayList<DefaultNode>();

	private Map<String, DefaultNode> rootNodesById = new HashMap<String, DefaultNode>();

	private double currentUID = 0;

	@Override
	public INode getNodeByGUID(String UID) {
		return this.allNodesByUID.get(UID);
	}

	@Override
	public INode createRootNode(String id, String nodeDefId) {
		DefaultNode rootNode = new DefaultNode(this, null, null, this.getNextUID(), id, nodeDefId);
		this.allNodesByUID.put(id, rootNode);
		this.rootNodesById.put(rootNode.getNodeId(), rootNode);
		this.rootNodeList.add(rootNode);
		List<DefaultNode> nodeList = this.nodesByType.get(nodeDefId);
		if (nodeList == null) {
			nodeList = new ArrayList<DefaultNode>();
			this.nodesByType.put(nodeDefId, nodeList);
		}
		nodeList.add(rootNode);
		assert rootNode.getUID() != null;
		return rootNode;
	}

	@Override
	public INodeIterator find(Node query) {
		List<DefaultNode> nodeList = this.nodesByType.get(query.getType());
		List<INode> resultSet = new ArrayList<INode>();
		if (nodeList != null) {
			for (DefaultNode node : nodeList) {
				assert node.getUID() != null;
				for (Map.Entry<String, String> mapEntry : query.getFields().entrySet()) {
					boolean match = true;
					INodeAttr targetAttr = node.getAttr(mapEntry.getKey());
					if (targetAttr == null) {
						match = false;
						break;
					} else {
						if (!mapEntry.getValue().equals(targetAttr.getValue())) {
							match = false;
							break;
						}
					}
					if (match) {
						resultSet.add(node);
					}
				}
				
			}
		}
		return new DefaultNodeIterator(resultSet.iterator());
	}

	void addNode(DefaultNode node) {
		assert node.getUID() != null;
		INode existingNode = this.allNodesByUID.get(node.getUID());
		if (existingNode != null) {
			throw new RuntimeException("node with UID[" + node.getUID() + "] already existst");
		}
		this.allNodesByUID.put(node.getUID(), node);
		List<DefaultNode> nodeList = this.nodesByType.get(node.getNodeDefId());
		if (nodeList == null) {
			nodeList = new ArrayList<DefaultNode>();
			this.nodesByType.put(node.getNodeDefId(), nodeList);
		}
		nodeList.add(node);
	}

	public synchronized String getNextUID() {
		return String.valueOf(this.currentUID++);
	}

	@Override
	public INode getRootNodeById(String id) {
		return this.rootNodesById.get(id);
	}

	public void removeNode(INode node) {
		assert node.getUID() != null;
		this.allNodesByUID.remove(node.getUID());
		this.nodesByType.get(node.getNodeDefId()).remove(node);
	}
}
