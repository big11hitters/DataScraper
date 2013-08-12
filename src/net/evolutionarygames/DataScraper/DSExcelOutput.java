package net.evolutionarygames.DataScraper;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class DSExcelOutput {
	DSWebScraper DSWS = new DSWebScraper();
	
	public void createExcelSheet() throws IOException{
		
		DSWS.runFileArrayProvider();
		
		// create a new file
		FileOutputStream out = new FileOutputStream("/Users/Anthony/Desktop/workbook.xls");
		// create a new workbook
		Workbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet s = wb.createSheet();
		// declare a row object reference
		Row row = null;
		// declare a cell object reference
		Cell cell = null;
		int listSize = DSWS.getPriceList().size();
		int rownum1;
		for (rownum1 = 0; rownum1 < 5; rownum1++){
		    // create a row
		    row = s.createRow(rownum1);
		    
		    for (int cellnum = 0; cellnum < listSize; cellnum++){
		        // create a numeric cell
		        cell = row.createCell(cellnum);
		        if(cell.equals(1))
		        	cell.setCellValue(DSWS.getPriceList().get(cellnum));
		        if(cell.equals(2))
		        	cell.setCellValue(DSWS.getPriceList().get(cellnum));
		        
		    }
		}
		wb.write(out);
		out.close();
	}
	
}
