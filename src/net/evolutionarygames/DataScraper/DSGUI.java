package net.evolutionarygames.DataScraper;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DSGUI extends JPanel {
	
	private DSExcelOutput DSEO = new DSExcelOutput();
	private DSWebScraper DSWS = new DSWebScraper();
	
	private JLabel inputLabel, pendingLabel, resultLabel;
	private static JTextField textField;
	private static Button browseButton;
	private static Button getDataButton;
	private static String filePath;
	
	public DSGUI(){
		this.setLayout(new BorderLayout());

		//various components that will be displayed in the application
		inputLabel = new JLabel("Browse to find the text file, click Get Data to start the retrival process.");
		pendingLabel = new JLabel(" ");
		resultLabel = new JLabel(" ");
		browseButton = new Button("Browse");
		getDataButton = new Button("Get Data");
		textField = new JTextField(20);
		//only allow the program to edit the textfield, no user editing abilities
		textField.setEnabled(false);
		
		//add an action listener for the browse button.
		browseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				JFileChooser chooser= new JFileChooser(); //create a new JFileChooser
				int choice = chooser.showOpenDialog(null); //selected file/folder
				if (choice != JFileChooser.APPROVE_OPTION){ //if the chosen file in not valid, tell the user and return
					pendingLabel.setText("File choice is invalid");
					return;
				}
				else{ 
					File chosenFile = chooser.getSelectedFile(); 
					filePath = chosenFile.getPath(); //path of the selected file
					textField.setText(filePath);
					pendingLabel.setText("File is valid");
				}
			}
		});
		
		getDataButton.addActionListener(new ActionListener(){ //getDataButton actionListener
			public void actionPerformed(ActionEvent evt){
				pendingLabel.setText("Retrieving data...Please wait");
				try {
					DSEO.createExcelSheet(filePath); //begin getting the data and creating the Excel sheet
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pendingLabel.setText("Finished");

			}
		});
		
		//add the buttons/labels to the application
		add(inputLabel,BorderLayout.NORTH);
		add(textField, BorderLayout.CENTER);
		add(browseButton, BorderLayout.WEST);
		add(getDataButton, BorderLayout.EAST);
		add(pendingLabel, BorderLayout.SOUTH);

		setPreferredSize(new Dimension (600,200));
		setBackground(Color.white);
	}
}
