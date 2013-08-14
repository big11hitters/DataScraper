//DSWebScraper -- Anthony Vitale -- net.evolutionarygames.DataScraper
//Last Updated 08/12/13

package net.evolutionarygames.DataScraper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DSWebScraper { 
	///////Elements for each data field I need to pull///////
	private static Element price;
	private static Element brand;
	private static Element model;
	private static Element weight;
	
	///////ArrayLists for each Element///////
	private static ArrayList<String> itemList = new ArrayList<String>();
	private static ArrayList<String> priceList = new ArrayList<String>();
	private static ArrayList<String> brandList = new ArrayList<String>();
	private static ArrayList<String> modelList = new ArrayList<String>();
	private static ArrayList<String> weightList = new ArrayList<String>();
	
	///////getter methods for each ArrayList///////
	public ArrayList<String> getPriceList(){
		return priceList;
	}
	public ArrayList<String> getBrandList(){
		return brandList;
	}
	public ArrayList<String> getModelList(){
		return modelList;
	}
	public ArrayList<String> getWeightList(){
		return weightList;
	}

	
	///////readLines method will take a file and read each line into an ArrayList///////
	public String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
	
	public void runFileArrayProvider(String filePath) throws IOException { //accepts an arg filePath for the file
        DSWebScraper ws = new DSWebScraper();
        String url = null;
        String[] lines = (ws).readLines(filePath); //read lines in from the argument in the methd
        
        try{
        	
        	for (String item : lines){ //sort through the array of line items
        		url = ("http://www.grainger.com/Grainger/wwg/search.shtml?searchQuery=" + item + "&op=search&Ntt=" + item + "&N=0&GlobalSearch=true&sst=subset");
        		Document doc = Jsoup.connect(url).timeout(5000).get();
        		
            		Elements rows = doc.select("td[class = tdrightalign]"); //choosing the html tags from the website
        		
            		for(@SuppressWarnings("unused") Element i : rows){ //going through the rows on the website
            			switch(rows.size()){ //since the amount of rows can vary, I've covered the different row sizes with this switch statement
            			case 10:
            				price = rows.get(1);
            				brand = rows.get(2);
            				model = rows.get(3);
            				weight = rows.get(6);
            				break;
            			case 11:
            				price = rows.get(1);
            				brand = rows.get(2);
            				model = rows.get(3);
            				weight = rows.get(7);
            				break;
            			case 12:
            				price = rows.get(1);
            				brand = rows.get(3);
            				model = rows.get(4);
            				weight = rows.get(8);
            				break;
            			}
            		}
            		
            		//adding the data fields to their respective arrays.
            		itemList.add(item);
            		priceList.add(price.text());
            		brandList.add(brand.text());
            		modelList.add(model.text());
            		weightList.add(weight.text());
        		}
        	
    		System.out.println("Finished " + lines.length + " lines");
        }
        //catching errors that are known to potentially happen, and alerting the user.
        catch(UnknownHostException ex){
        	System.out.println("Either the host is not correctly typed in, or the internet is down.");
        }
        catch(SocketTimeoutException STex){
        	System.out.println(STex);
        	System.out.println("******Connection Lost********");
        }
        catch(NullPointerException ex){
        	System.out.println("Null Pointer -- Please check the file for any blank lines and remove them.");
        }
    }
	
}
