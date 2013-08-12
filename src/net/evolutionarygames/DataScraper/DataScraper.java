package net.evolutionarygames.DataScraper;

import java.io.IOException;

public class DataScraper {
	static DSExcelOutput DSEO = new DSExcelOutput();
	
	public static void main(String[] args) throws IOException{
		DSEO.createExcelSheet();
	}
}