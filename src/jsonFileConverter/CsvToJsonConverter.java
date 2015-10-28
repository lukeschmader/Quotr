package jsonFileConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class CsvToJsonConverter {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		
		
		String file = "";
		String market = "";
		
		PrintWriter writer = new PrintWriter("CompanyInfo.json");
        writer.println("{\"companies\":[");
        
		for(int i = 0; i < 3; i++)
		{
			switch (i){
				case 0:					
					file = "AMEX.csv";
					market = ("AMEX");
					break;
				case 1:					
					file = "NASDAQ.csv";
					market = ("NASDAQ");
					break;
				case 2:
					file = "NYSE.csv";
					market = ("NYSE");
					break;			
			}
				
					
			Scanner scanner = new Scanner(new File(file));
	        scanner.useDelimiter(",");
	         
	        
	        

	        int k = 0;
	        String sym = "";
	        while (scanner.hasNext())
	        {
	        	//System.out.println(scanner.next().trim());
	        	
	        	sym = scanner.next().trim();
	        	if(scanner.hasNext())
	        	{
	        		writer.println("{");
		        	writer.println("\"symbol\":\""+ sym+"\",");	        	
		        	writer.println("\"name\":\""+ scanner.next().trim()+"\",");	
		        	writer.println("\"lastSale\":\""+ scanner.next().trim()+"\",");
		        	writer.println("\"marketCap\":\""+ scanner.next().trim()+"\",");
		        	writer.println("\"ipoYear\":\""+ scanner.next().trim()+"\",");	
		        	writer.println("\"sector\":\""+ scanner.next().trim()+"\",");
		        	writer.println("\"industry\":\""+ scanner.next().trim()+"\",");
		        	writer.println("\"summaryQuote\":\""+ scanner.next().trim()+"\"");
		        	writer.println("},");
		        	writer.flush();
	        	}
	        	
	        	System.out.println(k++);
	        		
	        	
	            
	        }
	        scanner.close();
	        
		}
		writer.println("]}");
		writer.close();
		
		

	}

}
