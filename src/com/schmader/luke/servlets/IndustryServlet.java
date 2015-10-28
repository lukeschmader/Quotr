package com.schmader.luke.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Servlet implementation class IndustryServlet
 */
@WebServlet("/IndustryServlet")
public class IndustryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String symbol = request.getParameter("symbol");
		BufferedReader reader = new BufferedReader( new FileReader ("CompanyInfo.json"));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	    }

	    reader.close();
		String jsonStr = stringBuilder.toString();
		stringBuilder = null;
		
		try {
			JSONObject outerObject;
			
				outerObject = new JSONObject(jsonStr);
			
			jsonStr = "";
			
			String industry = "";
			String sector = "";
			JSONArray companies = outerObject.getJSONArray("companies");
		    for (int i = 0, size = companies.length(); i < size; i++)
		    {
		      JSONObject objectInArray = companies.getJSONObject(i);
		      if(objectInArray.getString("symbol").equals(symbol))
		      {
		    	  System.out.println(objectInArray.getString("name"));
		    	  System.out.println(sector = objectInArray.getString("sector"));
		    	  System.out.println(industry = objectInArray.getString("industry"));
		    	  break;
		      }
		      

		    }
		    
		    
		    ///Get companies in industry
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
		    
		  //Get Quote and Iterate through companies
			 String[] symbols = symbolList.toArray(new String[symbolList.size()]);
			 Map<String, Stock> stocks = YahooFinance.get(symbols, true);
			 int k = 0;
			 BigDecimal bCount = new BigDecimal(stocks.size());
			 BigDecimal avgPrice = new BigDecimal(0);
			 BigDecimal avgChange = new BigDecimal(0);
			 BigDecimal avgChangeInPercent = new BigDecimal(0);
			 BigDecimal avgVolume = new BigDecimal(0);
			 BigDecimal avgAvgVolume = new BigDecimal(0);
			 BigDecimal avgYieldPct = new BigDecimal(0);
			 BigDecimal avgTarget = new BigDecimal(0);
			 
			 
			 StringBuffer returnData = new StringBuffer ("{\"companies\":[");		
			 for (Map.Entry<String, Stock> stock : stocks.entrySet())
			 {
				
			  //   System.out.println(stock.getKey() + "/" + stock.getValue());		
				 k++;
				 returnData.append("{");
				 returnData.append("\"symbol\":\"" +  stock.getValue().getSymbol() + "\",");
				 returnData.append("\"name\":\"" +  stock.getValue().getName() + "\",");
				 
				 returnData.append("\"yieldPct\":\"" +  stock.getValue().getDividend().getAnnualYieldPercent().setScale(2, RoundingMode.HALF_UP) + "\",");
				 avgYieldPct = avgYieldPct.add(stock.getValue().getDividend().getAnnualYieldPercent());
				 
				 returnData.append("\"price\":\"" +  stock.getValue().getQuote().getPrice().setScale(2, RoundingMode.HALF_UP) + "\",");
				 avgPrice = avgPrice.add(stock.getValue().getQuote().getPrice());
				 
				 returnData.append("\"change\":\"" +  stock.getValue().getQuote().getChange().setScale(2, RoundingMode.HALF_UP) + "\",");
				 avgChange = avgChange.add(stock.getValue().getQuote().getChange());
				 
				 returnData.append("\"changePct\":\"" +  stock.getValue().getQuote().getChangeInPercent().setScale(2, RoundingMode.HALF_UP) + "\",");
				 avgChangeInPercent = avgChangeInPercent.add(stock.getValue().getQuote().getChangeInPercent());			 
				 
				 returnData.append("\"oneYearTargetPrice\":\"" +  stock.getValue().getStats().getOneYearTargetPrice().setScale(2, RoundingMode.HALF_UP) + "\",");
				 avgTarget = avgTarget.add(stock.getValue().getStats().getOneYearTargetPrice());			 
				 
				 returnData.append("\"volume\":\"" +  stock.getValue().getQuote().getVolume() + "\",");
				 avgVolume = avgVolume.add(new BigDecimal(stock.getValue().getQuote().getVolume()));
				 
				 returnData.append("\"avgVolume\":\"" +  stock.getValue().getQuote().getAvgVolume() + "\"");
				 avgAvgVolume = avgAvgVolume.add(new BigDecimal(stock.getValue().getQuote().getAvgVolume()));
				 returnData.append("}");
				 if(k != (stocks.size()))
				 {
					 returnData.append(",");
				 }
				 
			 }   
			 returnData.append("],");
			 returnData.append("\"stats\":{");
			 		returnData.append("\"avgDividendYieldPct\":\"" + avgYieldPct.divide(bCount, 2, RoundingMode.HALF_UP) + "\",");
			 		returnData.append("\"avgPrice\":\"" + avgPrice.divide(bCount, 2, RoundingMode.HALF_UP) + "\",");
			 		returnData.append("\"avgChange\":\""+ avgChange.divide(bCount, 2, RoundingMode.HALF_UP) + "\",");
			 		returnData.append("\"avgChangePct\":\""+ avgChangeInPercent.divide(bCount, 2, RoundingMode.HALF_UP) + "\",");
			 		returnData.append("\"avgOneYearTargetPrice\":\""+ avgTarget.divide(bCount, 2, RoundingMode.HALF_UP) + "\",");
			 		returnData.append("\"avgVolume\":\""+ avgVolume.divide(bCount, 0, RoundingMode.HALF_UP) + "\",");
			 		returnData.append("\"avgAvgVolume\":\""+ avgAvgVolume.divide(bCount, 0, RoundingMode.HALF_UP) + "\"");		 
			 returnData.append("}}");
		 
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}


}
