package net.evolutionarygames.DataScraper;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DSGUI extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public boolean showGetDataButton = true;
	public boolean showCreateExcelButton = false;
	
	//Class references for use below
	private DSExcelOutput DSEO = new DSExcelOutput();
	private DSWebScraper DSWS = new DSWebScraper();
	
	//labels, textfields and buttons
	private JLabel pendingLabel;
	private static JTextField textField, outputFileField;
	private static Button browseButton, saveButton, getDataButton, createExcelButton;
	private static String filePath, outputFilePath;
	
	public DSGUI(){
		GridLayout gLay = new GridLayout(0,2);
		this.setLayout(gLay);
		
		//various components that will be displayed in the application
		pendingLabel = new JLabel("Browse to where you want to save");
		browseButton = new Button("Browse");
		saveButton = new Button("Choose Save Path");
		getDataButton = new Button("Get Data");
		createExcelButton = new Button("Create Excel Sheet");
		textField = new JTextField(20);
		outputFileField = new JTextField(20);
		//only allow the program to edit the textfield, no user editing abilities
		textField.setEnabled(false);
		outputFileField.setEnabled(false);
		//disable the getData button
		getDataButton.setEnabled(false);
		browseButton.setEnabled(false);
		
		this.initiateButtonActionListeners();
		this.createAndShowGUI();
	}
	
	public void initiateButtonActionListeners(){
		//add action listener for save button
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {//open a JFileChooser save dialog box
				JFileChooser chooser = new JFileChooser();
				int choice = chooser.showSaveDialog(null);
				if(choice != JFileChooser.APPROVE_OPTION){
					return;
				}
				else{ //get the chosen directory and file name for the saved file
					File chosenFile = chooser.getSelectedFile();
					outputFilePath = chosenFile.toString();
					
					outputFileField.setText(outputFilePath);
					browseButton.setEnabled(true);
					pendingLabel.setText("Browse to where the file with the item numbers is");
					if(showGetDataButton == false){
						showGetDataButton = true;
						remove(createExcelButton);
						add(getDataButton);
					}
				}
			}
		});
				
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
					pendingLabel.setText("Please click 'Get Data'. Depending on the list size, this may take some time");
					getDataButton.setEnabled(true);

				}
			}
		});
				
		//action listener for the get data button
		getDataButton.addActionListener(new ActionListener(){ //getDataButton actionListener
			public void actionPerformed(ActionEvent evt){
				try {
					DSEO.getData(filePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				showGetDataButton = false;
				remove(getDataButton);
				add(createExcelButton);
				pendingLabel.setText("Please click 'Create Excel Sheet' to complete the process");
			}
		});
		
		createExcelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				try {
					DSEO.createExcelSheet(outputFilePath); //begin getting the data and creating the Excel sheet
					if(DSWS.nullPointer == true)
						pendingLabel.setText("Please check the file for any blank lines and remove them");
					if(DSWS.socketTimeout == true)
						pendingLabel.setText("Connection lost. Please check your internet connection. It may also be their end");
					if(DSWS.unknownHost == true)
						pendingLabel.setText("You have an incorrect item number in your list");
					else{
						pendingLabel.setText("Finished " + DSWS.getItemList().size() + " lines. You will find the excel where you saved it");
						DSWS.cleanUp();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void createAndShowGUI(){
		//add the buttons/labels to the application
		add(outputFileField);
		add(saveButton);
		add(textField);
		add(browseButton);
		add(pendingLabel);
		if(showGetDataButton == true){
			add(getDataButton);
		}
		else{
			remove(getDataButton);
			add(createExcelButton);
		}
				
		//application's size and color
		textField.setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1000,110));
		this.setMaximumSize(getSize());
		setBackground(Color.WHITE);
	}
	
	
}
