package org.sri.nodeservice.core.nodeaccess.service.rsrq;

import java.util.List;

/**
 * 
 * @author sisaac
 *
 */
public class ListNodesRq extends BaseNodeRq {

	// the type of node to return
	private String nodeType;

	// support for paging.
	private int start;
	private int limit;
	private int page;

	// support for remote sort
	private String sortField;
	private String sortDir;

	// search expressions.
	private List<SearchFilter> filters;

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String type) {
		this.nodeType = type;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sort) {
		this.sortField = sort;
	}

	public String getSortDir() {
		return sortDir;
	}

	public void setSortDir(String dir) {
		this.sortDir = dir;
	}

	public List<SearchFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<SearchFilter> filters) {
		this.filters = filters;
	}

	public SearchFilter getFilterByKey(String columnKey) {
		if (filters != null) {
			for (SearchFilter searchFilter : filters) {
				if (columnKey.equals(searchFilter.getColumnKey())) {
					return searchFilter;
				}
			}
		}
		return null;
	}
}
