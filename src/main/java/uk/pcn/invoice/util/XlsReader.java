package uk.pcn.invoice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsReader {
	/*	public static String fileName = System.getProperty(key)
	*/
	
	//private Logger log = Logger.getLogger(XlsReader.class);
	private String path;
	FileInputStream fis = null;
	private XSSFWorkbook wb = null;
	private XSSFSheet sheet = null;
	
	public XlsReader(String filePath){
		this.path = filePath;
		try {
			 fis = new FileInputStream(new File(filePath));
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheetAt(0);
			//log.info("A default sheet 1 assigned to reader and the seet has "+sheet.getLastRowNum()+" rows.");
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method gets the cell data using cellId and rownum.
	 * @param sheetName
	 * @param cellID
	 * @param rowNum
	 * @return
	 */
	public String getCellData(String sheetName, String cellID, int rowNum){
		int colNum;
		XSSFCell cell;
		String cellData = null;
		sheet = wb.getSheet(sheetName.trim());
		//Get the colNum of the first row(column names).
		colNum = getColNum(sheet.getRow(0), cellID);
		XSSFRow row = sheet.getRow(rowNum);
		if(row!=null && colNum != -1){
		 cell = row.getCell(colNum);
		// cell.setCellType(Cell.CELL_TYPE_STRING);
		 if(cell != null)
		 cellData = cell.toString();
		}
		return cellData;
	}
	/**
	 * This method gets the cell data using cellId and rownum.
	 * @param sheetName
	 * @param cellID
	 * @param rowNum
	 * @return
	 */
	public String getCellData(String sheetName, String cellID, int rowNum, int type){
		int colNum;
		XSSFCell cell;
		String cellData = null;
		sheet = wb.getSheet(sheetName.trim());
		//Get the colNum of the first row(column names).
		colNum = getColNum(sheet.getRow(0), cellID);
		XSSFRow row = sheet.getRow(rowNum);
		if(row!=null && colNum != -1){
		 cell = row.getCell(colNum);
		 cell.setCellType(type);
		 if(cell != null)
		 cellData = cell.toString();
		}
		return cellData;
	}
	public void setCellData(String testCaseName, String colName, int rowNum,
			String result) {
		int colNum;
		XSSFCell cell;
	    FileOutputStream outStream = null;
		sheet = wb.getSheet(testCaseName.trim());
		//Get the colNum of the first row(column names).
		colNum = getColNum(sheet.getRow(0), colName.trim());
		XSSFRow row = sheet.getRow(rowNum);
		if(row!=null && colNum != -1){
		 cell = row.createCell(colNum);
		 cell.setCellValue(result);
		}
			try {
				outStream = new FileOutputStream(new File(path));
				wb.write(outStream);
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * This method gets the cell data using colNum and rowNum.
	 * @param sheetName
	 * @param cellID
	 * @param rowNum
	 * @return
	 */
	public String getCellData(String sheetName, int colNum, int rowNum){
		XSSFCell cell;
		String cellData = null;
		sheet = wb.getSheet(sheetName.trim());
		XSSFRow row = sheet.getRow(rowNum);
		if(row!=null && colNum != -1){
		 cell = row.getCell(colNum);
		 if(cell !=null)
		 cellData = cellToString(cell);
		}
		return cellData;
	}
	/**
	 * This method get the column data using name of the column.
	 * @param sheetName
	 * @param colName
	 * @return
	 */
	public String[] getColumnDataSet(String sheetName, String colName){
		int colNum = 0;
		int rows = getRowCount(sheetName);
		String data[] = new String[rows];
		sheet = wb.getSheet(sheetName.trim());
		XSSFRow row = sheet.getRow(0);
		colNum = getColNum(row, colName);

		for (int i = 1; i <= rows; i++) {
			data[i-1] = getCellData(sheetName, colNum, i);
		}
		return data;
	}
	/**
	 * This method get the column number on a row. 
	 * @param xssfRow
	 * @param data, the data of cell in a row.
	 * @return
	 */
	private int getColNum(XSSFRow xssfRow, String data){
		Iterator<Cell> cells = xssfRow.cellIterator();
		while(cells.hasNext()){
			XSSFCell cell = (XSSFCell) cells.next();
			if(cell.toString().trim().equalsIgnoreCase(data.trim())){
				return cell.getColumnIndex();
			}
		}
		
		return -1;
	}
	public static String cellToString(XSSFCell cell) {
		// This function will convert an object of type excel cell to a string value
	        int type = cell.getCellType();
	        Object result;
	        switch (type) {
	            case XSSFCell.CELL_TYPE_NUMERIC: //0
	                result = cell.getNumericCellValue();
	                break;
	            case XSSFCell.CELL_TYPE_STRING: //1
	                result = cell.getStringCellValue();
	                break;
	            case XSSFCell.CELL_TYPE_FORMULA: //2
	                throw new RuntimeException("We can't evaluate formulas in Java");
	            case XSSFCell.CELL_TYPE_BLANK: //3
	                result = "-";
	                break;
	            case XSSFCell.CELL_TYPE_BOOLEAN: //4
	                result = cell.getBooleanCellValue();
	                break;
	            case XSSFCell.CELL_TYPE_ERROR: //5
	                throw new RuntimeException ("This cell has an error");
	            default:
	                throw new RuntimeException("We don't support this cell type: " + type);
	        }
	        return result.toString();
	    }
	public boolean isSheetExist(String testSheetName) {
		XSSFSheet sheet = wb.getSheet(testSheetName);
		if(sheet!=null)
			return true;
		return false;
	}
	/**
	 * 
	 * @param sheetName
	 * @return
	 */
	public int getRowCount(String sheetName){
		//deafult is -1 if sheet not exist
		int rowCount = -1;
		XSSFSheet sheetTemp = wb.getSheet(sheetName);
		if(sheetTemp != null){
		rowCount = sheetTemp.getLastRowNum();
		}
		//log.info("Row Count: "+rowCount);
		return rowCount;
	}
	/**
	 * 
	 * @param testCaseName, testCaseName is same as sheet name.
	 * @return
	 */
	public int getColCount(String testCaseName) {
		//default is -1 if sheet not exist.
		int cols = -1;
		XSSFSheet sheetTemp = wb.getSheet(testCaseName);
		if(sheetTemp != null){
		cols = sheetTemp.getRow(0).getLastCellNum();
		}
		//log.info("Cols Count: "+cols);
		return cols;
	}

}
