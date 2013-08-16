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
	
	public void createExcelSheet(String filePath, String outputPath) throws IOException{
		DSWS.runFileArrayProvider(filePath);
		
		// create a new file
		FileOutputStream out = new FileOutputStream(outputPath);
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
		for (rownum1 = 0; rownum1 < listSize; rownum1++){
		    // create a row
		    row = s.createRow(rownum1);
		    for(int cellnum = 0; cellnum < 1; cellnum++){
		    	cell = row.createCell(cellnum);
		    	cell.setCellValue(DSWS.getItemList().get(rownum1));
		    }
		    for (int cellnum = 1; cellnum < 2; cellnum++){
		        cell = row.createCell(cellnum);
		        cell.setCellValue(DSWS.getPriceList().get(rownum1));
		    }
		    for(int cellnum = 2; cellnum < 3; cellnum++){
		    	cell = row.createCell(cellnum);
		    	cell.setCellValue(DSWS.getBrandList().get(rownum1));
		    }
		    for(int cellnum = 3; cellnum < 4; cellnum++){
		    	cell = row.createCell(cellnum);
		    	cell.setCellValue(DSWS.getModelList().get(rownum1));
		    }
		    for(int cellnum = 4; cellnum < 5; cellnum++){
		    	cell = row.createCell(cellnum);
		    	cell.setCellValue(DSWS.getWeightList().get(rownum1));
		    }
		}
		wb.write(out);
		out.close();
	}
}
