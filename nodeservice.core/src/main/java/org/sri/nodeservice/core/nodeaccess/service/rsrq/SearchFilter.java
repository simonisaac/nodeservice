package org.sri.nodeservice.core.nodeaccess.service.rsrq;

/**
 * 
 * @author sisaac
 *
 */
public class SearchFilter {

	public final static int LESS_THAN = 2;
	public final static int LESS_THAN_EQUAL_TO = 4;
	public final static int GREATER_THAN = 8;
	public final static int GREATER_THAN_EQUAL_TO = 16;
	public final static int EQUAL = 32;
	public final static int NOT_EQUAL = 64;

	private String columnValue;
	private String columnKey;
	private int compare;

	public SearchFilter() {
		super();
	}

	public SearchFilter(String columnKey, int compare, String columnValue) {
		this.columnKey = columnKey;
		this.columnValue = columnValue;
		this.compare = compare;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public int getCompare() {
		return compare;
	}
}
