package com.batchjobs.reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.batchjobs.entity.RawSpeedData;
import com.util.Util;



public class ExcelFileItemReader implements ItemReader<RawSpeedData> {

	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private int sheetNumber; 
	private HSSFRow row;
	private HSSFCell cell;
	private String resourcePath;
	private String fileNamePattern;
	private String fileExtension;
	private List<RawSpeedData> speedDataList; 
	private int index; 
	private int rowsToSkip;
	private List<Integer> columnsToRead;
	private int headerRow;
	private int lastRow;
	
	
	public ExcelFileItemReader() throws Exception {
		
	}
	
	
	
	private void initialize() throws Exception {
		
		try{
			this.fs = new POIFSFileSystem(new FileInputStream(new File(resourcePath + Util.getFileName(fileNamePattern, fileExtension))));
			this.wb = new HSSFWorkbook(fs);
			this.sheet = wb.getSheetAt(sheetNumber); 
			String currentDate = null;
			RawSpeedData speedData = null; 
			speedDataList = new ArrayList<RawSpeedData>();
			boolean isEmptyRow = true;
			
			currentDate = Util.getYesterdaysDate();
			
			for(int currentRow = headerRow + 1; currentRow < lastRow; currentRow ++){
				row = sheet.getRow(currentRow);
				speedData = new RawSpeedData();
				speedData.setDate(currentDate);
				isEmptyRow = populateObject(speedData);
				if (!isEmptyRow) {
					speedDataList.add(speedData);
				}
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private boolean populateObject(RawSpeedData speedData){
		boolean isEmptyRow = true;
		for (int columnNumber : columnsToRead) {
			cell = row.getCell(columnNumber);
			if (cell != null && null != speedData) {
				isEmptyRow = false;
				
				switch(columnNumber){
					case 0:
						speedData.setPageName(cell.getStringCellValue());
						break;
					case 9: 
						speedData.setSpeed(cell.getNumericCellValue());
						break;
				}
			}
		}
		return isEmptyRow;
	}
	
	public void setSheetNumber(int sheetNumber) {
		this.sheetNumber = sheetNumber;
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RawSpeedData read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		if(index >= speedDataList.size()){
			return null; 
		}
		RawSpeedData currentRowData = speedDataList.get(index);
		index++;
		return currentRowData;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public int getRowsToSkip() {
		return rowsToSkip;
	}

	public void setRowsToSkip(int rowsToSkip) {
		this.rowsToSkip = rowsToSkip;
	}

	public List<Integer> getColumnsToRead() {
		return columnsToRead;
	}

	public void setColumnsToRead(List<Integer> columnsToRead) {
		this.columnsToRead = columnsToRead;
	}

	public int getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(int headerRow) {
		this.headerRow = headerRow;
	}
	
	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	public String getFileNamePattern() {
		return fileNamePattern;
	}

	public void setFileNamePattern(String fileNamePattern) {
		this.fileNamePattern = fileNamePattern;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
}
