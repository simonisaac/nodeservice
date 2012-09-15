package org.sri.nodeservice.core.nodemgr.util;


/**
 * 
 * @author sisaac
 *
 */
public class NodeMgrUtil {

	/*public static Map<String, Node> generateNodes(NodeDef def, int number, IdGenerator idGenerator, boolean createChildren) {
		Map<String, Node> rMap = new HashMap<String, Node>();
		for (int i = 0; i < number; i++) {
			Node node = def.getDefaultNode(String.valueOf(i), createChildren);
			rMap.put(node.getId(), node);
		}
		return rMap;
	}*/

	public static interface IdGenerator {
		public String generateId();
	}

	public static class DefaultGenerator implements IdGenerator {

		int currentId = 0;

		boolean ascending = true;

		@Override
		public String generateId() {
			if (ascending) {
				return  String.valueOf(++currentId);
			}
			else {
				return String.valueOf(--currentId);
			}
		}

		public int getCurrentId() {
			return currentId;
		}

		public void setCurrentId(int currentId) {
			this.currentId = currentId;
		}

		public boolean isAscending() {
			return ascending;
		}

		public void setAscending(boolean ascending) {
			this.ascending = ascending;
		}
	}
}
