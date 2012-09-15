package org.sri.nodeservice.transform.deprecated;


public class ProcessSpreadsheet /*implements IProcessSpreadsheet */{

	/*Logger log = Logger.getLogger(ProcessSpreadsheet.class);

	private IXmlToSheetTransformer xmlToSheetTransformer;

	@Override
	public Node process(File spreadSheet, File processingInstructionXml)
			throws SpreadsheetProcessingException {
		Node node = null;
		try {
			FileInputStream spreadSheetInputStream = new FileInputStream(
					spreadSheet);
			FileInputStream processingInstructionXmlInputStream = new FileInputStream(
					processingInstructionXml);
			node = process(spreadSheetInputStream,
					processingInstructionXmlInputStream);
		} catch (FileNotFoundException e) {
			throw new SpreadsheetProcessingException(e);
		}
		return node;
	}

	@Override
	public Node process(InputStream spreadSheet,
			InputStream processingInstructionXml)
			throws SpreadsheetProcessingException {
		Node node = null;
		try {
			Workbook workbook = WorkbookFactory.create(spreadSheet);
			Sheet processingInstSheet = xmlToSheetTransformer
					.parseProcessingInstructionXml(CommonUtils
							.convertStreamToString(processingInstructionXml));
			node = createNode(workbook, processingInstSheet);
		} catch (Exception e) {
			throw new SpreadsheetProcessingException(e);
		}
		return node;
	}
	
	
	@Override
	public Node process(InputStream spreadSheet, Sheet processingInstSheet)
			throws SpreadsheetProcessingException {
		Node node = null;
		try {
			Workbook workbook = WorkbookFactory.create(spreadSheet);
			node = createNode(workbook, processingInstSheet);
		} catch (Exception e) {
			throw new SpreadsheetProcessingException("Invalid spreadsheet and instruction combination", e);
		}
		return node;
	}
	

	private Node createNode(Workbook workbook, Sheet processingInstSheet) {

		String sheetName = processingInstSheet.getSheetName();
		String sheetId = processingInstSheet.getSheetId();
		String rootNodeType = processingInstSheet.getSheetRootNodeType();
		String sheetNodeType = processingInstSheet.getSheetNodeType();
		int startRow = processingInstSheet.getStartRow();
		int endRow = startRow;

		List<ColumnType> columns = processingInstSheet.getColumn();
		org.apache.poi.ss.usermodel.Sheet workbookSheet = workbook
				.getSheet(sheetName);

		if (processingInstSheet.getEndRow() != null) {
			endRow = processingInstSheet.getEndRow();
		} else {
			endRow = workbookSheet.getLastRowNum();
		}

		Node sheetNode = new Node(sheetId, rootNodeType);
		NodeSet nodeSet = new NodeSet("childnodes");
		sheetNode.addChildNodeSet(nodeSet);

		if (endRow >= startRow) {
			for (int i = startRow; i <= endRow; i++) {
				log.debug("Processing Row Num : " + i + "");
				Row row = workbookSheet.getRow(i);
				Node rowNode = new Node("ID-" + i, sheetNodeType);
				setFields(rowNode, row, columns);

				nodeSet.addNode(rowNode);
			}
		}
		return sheetNode;
	}

	private void setFields(Node rowNode, Row row, List<ColumnType> columns) {
		for (ColumnType column : columns) {
			String fieldValue = "";
			org.apache.poi.ss.usermodel.Cell cell = row.getCell(column
					.getColumnIndex());
			if (column.isDateType() != null && column.isDateType()
					&& cell.getDateCellValue() != null) {
				Date dateCellValue = cell.getDateCellValue();
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				fieldValue = df.format(dateCellValue);
			} else if (column.isTimeType() != null && column.isTimeType()
					&& cell.getDateCellValue() != null) {
				Date dateCellValue = cell.getDateCellValue();
				DateFormat df = new SimpleDateFormat("HH:mm:ss");
				fieldValue = df.format(dateCellValue);
			} else {
				fieldValue = getCellString(cell);
			}
			rowNode.setField(column.getColumnName(), fieldValue);
		}
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
				dataString = Boolean.toString(cell.getBooleanCellValue());
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
					log.info("Failed to read formula cell as numeric. Next to try as string. Cell="
							+ cell.toString());
					try {
						dataString = cell.getRichStringCellValue().getString();
						log.info("Successfully read formula cell as string. Value="
								+ dataString);
					} catch (Exception e2) {
						log.warn("Failed to read formula cell as numeric or string. Cell="
								+ cell.toString());
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

	public void setXmlToSheetTransformer(
			IXmlToSheetTransformer xmlToSheetTransformer) {
		this.xmlToSheetTransformer = xmlToSheetTransformer;
	}*/

}
