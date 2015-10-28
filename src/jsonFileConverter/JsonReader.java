package jsonFileConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
public class JsonReader {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws IOException, JSONException, URISyntaxException {
		BufferedReader reader = new BufferedReader( new FileReader ("CompanyInfo.json"));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	    }

	    reader.close();
		String jsonStr = stringBuilder.toString();
		stringBuilder = null;
		JSONObject outerObject = new JSONObject(jsonStr);
		jsonStr = "";
		
		String industry = "";
		String sector = "";
		JSONArray companies = outerObject.getJSONArray("companies");
	    for (int i = 0, size = companies.length(); i < size; i++)
	    {
	      JSONObject objectInArray = companies.getJSONObject(i);
	      if(objectInArray.getString("symbol").equals("GPRO"))
	      {
	    	  System.out.println(objectInArray.getString("name"));
	    	  System.out.println(sector = objectInArray.getString("sector"));
	    	  System.out.println(industry = objectInArray.getString("industry"));
	    	  break;
	      }
	      

	      // "...and get thier component and thier value."
//	      String[] elementNames = JSONObject.getNames(objectInArray);
//	      System.out.printf("%d ELEMENTS IN CURRENT OBJECT:\n", elementNames.length);
//	      for (String elementName : elementNames)
//	      {
//	        String value = objectInArray.getString(elementName);
//	        //System.out.printf("name=%s, value=%s\n", elementName, value);
//	      }
//	      
//	      System.out.println();
	    }
	    
	    int count = 0;
	    List<String> symbolList = new ArrayList<String>();
	    for (int i = 0, size = companies.length(); i < size; i++)
	    {
	      JSONObject objectInArray = companies.getJSONObject(i);
	      if(objectInArray.getString("industry").equals(industry) && objectInArray.getString("sector").equals(sector))
	      {
	    	  System.out.println(count++);
	    	  symbolList.add(objectInArray.getString("symbol").trim());
	    	  System.out.println(objectInArray.getString("symbol"));
	    	  System.out.println(objectInArray.getString("name"));
	    	  System.out.println(objectInArray.getString("sector"));
	    	  System.out.println(objectInArray.getString("industry"));	    	 
	      }
	    }
	    
	    
	 String[] symbols = symbolList.toArray(new String[symbolList.size()]);
	 Map<String, Stock> stocks = YahooFinance.get(symbols, true);
	 
	 for (Map.Entry<String, Stock> stock : stocks.entrySet())
	 {
		// stock.getValue().
	  //   System.out.println(stock.getKey() + "/" + stock.getValue());
	 }   
	    
	    
	    
	    
	    
	    
	}
	
	

}
