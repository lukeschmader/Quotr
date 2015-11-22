package com.schmader.luke.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schmader.luke.CompanyProfile;
import com.schmader.luke.StockProfile;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Servlet implementation class StockServlet
 */
@WebServlet("/StockServlet")
public class StockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String symbol = request.getParameter("symbol");
		System.out.println("===========NEW STOCK: "+ symbol);
		if(symbol != null && symbol != "") 
		{
			StringBuffer returnData = null;		
			String returnString = "";
			
			CompanyProfile profile = getCompanyProfile(symbol, request);
			
			
			StockProfile stock = getStockProfile(symbol);
			if(stock.getName() != null && stock.getName() != "" && !stock.getName().equals("N/A"))
			{
			returnData = new StringBuffer ("{\"data\":{");		
			//Stock Data
			returnData.append("\"stock\": {");
			returnData.append("\"price\":\"" + stock.getPrice().toString() + "\"," );
			returnData.append("\"name\":\"" + stock.getName() + "\"," );
			returnData.append("\"symbol\":\"" + stock.getSymbol() + "\"," );
			returnData.append("\"change\":\"" + stock.getChange().toString() + "\"," );
			returnData.append("\"pctChg\":\"" + stock.getPctChange().toString() + "\"," );
			returnData.append("\"ask\":\"" + stock.getAsk().toString() + "\"," );
			returnData.append("\"bid\":\"" + stock.getBid().toString() + "\"," );
			returnData.append("\"chgFromYearLowPct\":\"" + stock.getChangeFromYearLowPct().toString() + "\"," );
			returnData.append("\"chgFromYearHighPct\":\"" + stock.getChangeFromYearHighPct().toString() + "\"," );
			returnData.append("\"dayLow\":\"" + stock.getDayLow().toString() + "\"," );
			returnData.append("\"dayHigh\":\"" + stock.getDayHigh().toString() + "\"," );
			returnData.append("\"open\":\"" + stock.getOpen().toString() + "\"," );
			returnData.append("\"volume\":\"" + stock.getVolume() + "\"," );
			returnData.append("\"avgVolume\":\"" + stock.getAvgVolume() + "\"," );
			returnData.append("\"yearLow\":\"" + stock.getYearLow().toString() + "\"," );
			returnData.append("\"yearHigh\":\"" + stock.getYearHigh().toString() + "\"," );
			returnData.append("\"eps\":\"" + stock.getEps().toString() + "\"," );
			returnData.append("\"yieldPct\":\"" + stock.getYieldPct().toString() + "\"," );
			returnData.append("\"yearTarget\":\"" + stock.getYearTarget().toString() + "\"," );
			returnData.append("\"peg\":\"" + stock.getPeg().toString() + "\"}," );
			
			//Company Profile Data
			returnData.append("\"company\":{");
			returnData.append("\"market\":\"" + profile.getMarket() + "\"," );
			returnData.append("\"symbol\":\"" + profile.getSym() + "\"," );		
			returnData.append("\"name\":\"" + profile.getName() + "\"," );
			returnData.append("\"marketCap\":\"" + profile.getMarketCap() + "\"," );
			returnData.append("\"ipoYear\":\"" + profile.getiPOYear() + "\"," );
			returnData.append("\"sector\": \"" + profile.getSector() + "\"," );
			returnData.append("\"industry\":\"" + profile.getIndustry() + "\"," );
			returnData.append("\"summaryQuote\":\"" + profile.getSummaryQuote() + "\"}" );
			returnData.append("}}");
			returnString = returnData.toString();
			}
			else
			{
				returnString = "";
			}
			//Write response with JSON data structure
			System.out.println("====STOCK RETURN STRING: " + returnString);
			response.getWriter().write(returnString);
		}
	}
	
	private StockProfile getStockProfile(String sym)
	{
		StockProfile prof = new StockProfile();
		
		Stock stock = YahooFinance.get(sym);
		
		if(stock != null)
		{			
			prof.setName(stock.getName());
			prof.setSymbol(stock.getSymbol());
			prof.setPrice(stock.getQuote().getPrice().setScale(2, RoundingMode.HALF_UP));
			prof.setChange(stock.getQuote().getChange().setScale(2, RoundingMode.HALF_UP));
			prof.setPctChange(stock.getQuote().getChangeInPercent().setScale(2, RoundingMode.HALF_UP));
			prof.setAsk(stock.getQuote().getAsk().setScale(2, RoundingMode.HALF_UP));
			prof.setBid(stock.getQuote().getBid().setScale(2, RoundingMode.HALF_UP));
			prof.setChangeFromYearLowPct(stock.getQuote().getChangeFromYearLowInPercent().setScale(2, RoundingMode.HALF_UP));
			prof.setChangeFromYearHighPct(stock.getQuote().getChangeFromYearHighInPercent().setScale(2, RoundingMode.HALF_UP));
			prof.setDayLow(stock.getQuote().getDayLow().setScale(2, RoundingMode.HALF_UP));
			prof.setDayHigh(stock.getQuote().getDayHigh().setScale(2, RoundingMode.HALF_UP));
			prof.setOpen(stock.getQuote().getOpen().setScale(2, RoundingMode.HALF_UP));
			prof.setVolume(stock.getQuote().getVolume());
			prof.setAvgVolume(stock.getQuote().getAvgVolume());
			prof.setYearLow(stock.getQuote().getYearLow().setScale(2, RoundingMode.HALF_UP));
			prof.setYearHigh(stock.getQuote().getYearHigh().setScale(2, RoundingMode.HALF_UP));
			prof.setEps(stock.getStats().getEps().setScale(2, RoundingMode.HALF_UP));
			prof.setPeg(stock.getStats().getPeg().setScale(2, RoundingMode.HALF_UP));	
			prof.setYearTarget(stock.getStats().getOneYearTargetPrice().setScale(2, RoundingMode.HALF_UP));
			prof.setYieldPct(stock.getDividend().getAnnualYieldPercent().setScale(2, RoundingMode.HALF_UP));
			
		}
		
		return prof;
	}
	
	private CompanyProfile getCompanyProfile(String sym, HttpServletRequest request) throws FileNotFoundException
	{
		
		ServletContext servletContext = request.getSession().getServletContext();
		String relativeWebPath = "";
		String file = "";
		
		CompanyProfile prof = new CompanyProfile();		
		prof.setName(null);
		prof.setSym(sym);

		boolean found = false;
		
		
		for(int i = 0; i < 3; i++)
		{
			switch (i){
				case 0:					
					relativeWebPath = "AMEX.csv";
					file = servletContext.getRealPath(relativeWebPath);
					prof.setMarket("AMEX");
					break;
				case 1:					
					relativeWebPath = "NASDAQ.csv";
					file = servletContext.getRealPath(relativeWebPath);
					prof.setMarket("NASDAQ");
					break;
				case 2:
					relativeWebPath = "NYSE.csv";
					file = servletContext.getRealPath(relativeWebPath);
					prof.setMarket("NYSE");
					break;			
			}
				
					
			Scanner scanner = new Scanner(new File(file));
         
	        //Set the delimiter used in file
	        scanner.useDelimiter(",");
	         
	    
	        //I am just printing them
	        while (scanner.hasNext())
	        {
	        	//System.out.println(scanner.next().trim());
	        	if(scanner.next().trim().equals(sym.trim()))
	        	{
	        		prof.setName(scanner.next());
	        		prof.setLastSale(scanner.next());
	        		prof.setMarketCap(scanner.next());
	        		prof.setiPOYear(scanner.next());
	        		prof.setSector(scanner.next());
	        		prof.setIndustry(scanner.next());
	        		prof.setSummaryQuote(scanner.next());	        		
	        		found =true;
	        		break;
	        	}
	            
	        }
	        scanner.close();
	        if(found)
	        {
	        	break;
	        }
		}
		return prof;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
