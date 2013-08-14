package net.evolutionarygames.DataScraper;

import java.io.IOException;

import javax.swing.JFrame;

public class DataScraper {
	static DSExcelOutput DSEO = new DSExcelOutput();
	
	public static void main(String[] args) throws IOException{
		JFrame frame = new JFrame("Work Scraper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DSGUI panel = new DSGUI();
		
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		//DSEO.createExcelSheet();
	}
}