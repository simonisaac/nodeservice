package org.sri.nodeservice.transform.excel.processorimpls;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INode;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeAttr;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet;
import org.sri.nodeservice.core.nodemgr.node.nodeif.INodeStore;
import org.sri.nodeservice.core.nodemgr.util.INodeStoreUtils;
import org.sri.nodeservice.transform.excel.ColumnType;
import org.sri.nodeservice.transform.excel.Sheet;
import org.sri.nodeservice.transform.processing.IProcessor;
import org.sri.nodeservice.transform.processing.ProcessingException;

public class ProcessorImplExcelSimpleRows implements IProcessor {

	Logger log = Logger.getLogger(ProcessorImplExcelSimpleRows.class);

	private Sheet processingInstSheet;

	public ProcessorImplExcelSimpleRows(Sheet processingInstSheet) {
		this.processingInstSheet = processingInstSheet;
	}

	/**
	 * @see org.sri.nodeservice.transform.excel.processorimpls.IExcelProcessor#process
	 * (org.sri.nodeservice.core.nodemgr.node.nodeif.INodeSet, java.io.File)
	 */
	@Override
	public INode process(INodeSet resultsNodeSet, File spreadsheetFile, INodeStore defaults) {
		try {
			FileInputStream spreadSheetInputStream = new FileInputStream(spreadsheetFile);
			Workbook workbook = WorkbookFactory.create(spreadSheetInputStream);
			return this.createNode(workbook, resultsNodeSet, defaults);
		} catch (Throwable e) {
			throw new ProcessingException(e);
		}
	}

	private INode createNode(Workbook workbook, INodeSet containerNodeSet, INodeStore defaults) {

		int startRow = processingInstSheet.getStartRow();
		int endRow = startRow;

		List<ColumnType> columns = processingInstSheet.getColumn();
		org.apache.poi.ss.usermodel.Sheet workbookSheet = workbook.getSheet(processingInstSheet.getSheetName());

		if (processingInstSheet.getEndRow() != -1) {
			endRow = processingInstSheet.getEndRow();
		} else {
			endRow = workbookSheet.getLastRowNum();
		}

		INode rootNode = containerNodeSet.createChildNode("dataSheet", processingInstSheet.getSheetRootNodeType());
		INodeSet rowNodeSet = rootNode.createChildNodeSet("rows");

		int maxConsecutiveRows = 0; // hard coded for now - need to change this.!
		int consecutiveEmptyRows = 0;
		int currentRow = startRow;
		if (endRow >= startRow) {
			for (int i = startRow; i <= endRow; i++) {
				log.debug("Processing Row Num : " + i + "");
				Row row = workbookSheet.getRow(i);
				INode rowNode = rowNodeSet.createChildNode("Row-" + currentRow++,
						processingInstSheet.getSheetNodeType());
				boolean emptyRow = setFields(rowNode, row, columns, defaults);
				if (emptyRow) {
					consecutiveEmptyRows++;
					if (consecutiveEmptyRows > maxConsecutiveRows) {
						rowNode.remove();
						break;
					}
				} else {
					consecutiveEmptyRows = 0;
				}
			}
		}

		return rootNode;
	}

	private boolean setFields(INode rowNode, Row row, List<ColumnType> columns, INodeStore defaults) {
		boolean emptyRow = false;
		int emptyColumnCount = 0;
		for (ColumnType column : columns) {
			String fieldValue = null;
			org.apache.poi.ss.usermodel.Cell cell = row.getCell(column.getColumnIndex());
			//if (cell == null) {
			//	System.out.println("oo");
			//}

			// apply defaults if cell is empty and defaults supplied
			String cellString = getCellString(cell);
			if (cellString == null || "".equals(cellString)) {
				INode defaultForType = INodeStoreUtils.getFirstInstanceOfType(defaults,
						processingInstSheet.getSheetNodeType());
				if (defaultForType != null) {
					INodeAttr defaultAttr = defaultForType.getAttr(column.getColumnName());
					if (defaultAttr != null) {
						cellString = defaultAttr.getValue();
					}
				}
				/*else {
					cellString = "";
				}*/
			} else {
				if (column.isDateType() != null && column.isDateType()) {
					Date dateCellValue = null;
					try {
						dateCellValue = cell.getDateCellValue();
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
						fieldValue = df.format(dateCellValue);
					} catch (Exception e) {
						// not sure at this point, not nothing and read as a
						// string
						fieldValue = null;
					}
				} else if (column.isTimeType() != null && column.isTimeType()) {
					Date dateCellValue = null;
					try {
						dateCellValue = cell.getDateCellValue();
						DateFormat df = new SimpleDateFormat("HH:mm:ss");
						fieldValue = df.format(dateCellValue);
					} catch (Exception e) {
						// not sure at this point, not nothing and read as a
						// string
						fieldValue = null;
					}
				}
				if (fieldValue == null) {
					fieldValue = getCellString(cell);
					if (fieldValue == null || "".equals(fieldValue)) {
						emptyColumnCount++;
					}
				}
			}
			rowNode.createAttr(column.getColumnName(), fieldValue);
			if (emptyColumnCount == columns.size()) {
				emptyRow = true;
			}
		}
		return emptyRow;
	};

	private String getCellString(Cell cell) {
		String dataString = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				dataString = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				dataString = readNumericCell(cell);
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				// use get string value because the get boolean value will default at null or empty string to 'false'
				dataString = cell.getStringCellValue();
				// dataString = Boolean.toString(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_ERROR:
				break;
			case Cell.CELL_TYPE_FORMULA:
				// attempt to read formula cell as numeric cell
				try {
					dataString = readNumericCell(cell);
				} catch (Exception e1) {
					log.info("Failed to read formula cell as numeric. Next to try as string. Cell=" + cell.toString());
					try {
						dataString = cell.getRichStringCellValue().getString();
						log.info("Successfully read formula cell as string. Value=" + dataString);
					} catch (Exception e2) {
						log.warn("Failed to read formula cell as numeric or string. Cell=" + cell.toString());
					}
				}
				break;
			default:
				break;
			}
		}
		return dataString;
	}

	private String readNumericCell(Cell cell) {
		double value;
		String dataString = null;
		value = cell.getNumericCellValue();
		if (((int) value) == value) {
			dataString = Integer.toString((int) value);
		} else {
			dataString = Double.toString(cell.getNumericCellValue());
		}
		return dataString;
	}
}
