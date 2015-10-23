package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.util.resources.cldr.aa.CalendarData_aa_ER;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Test {

	
	public static void main(String [ ] args)
	{
		
		
		String symbol = "GOOGL";
		if(symbol != null || symbol != "")
		{
			StringBuffer returnData = null;		
			String returnString = "";
			

			StockProfile stock = getStockProfile(symbol);
			returnData = new StringBuffer ("{\"data\":{");		
			//Stock Data
			returnData.append("\"stock\": {");
			//returnData.append("\"price\":\"" + stock.getPrice().toString() + "\"," );

			returnData.append("}}");
			returnString = returnData.toString();

		}
	}
	
	private static StockProfile getStockProfile(String sym)
	{
		StockProfile prof = new StockProfile();
		Calendar end = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		start.add(Calendar.MONTH, -1);
		
		Stock stock = YahooFinance.get(sym);
		
		if(stock.getName() != null)
		{			
			
			List<HistoricalQuote> history = stock.getHistory(start, end, Interval.DAILY);
			Iterator<HistoricalQuote> historyItr = history.iterator();
			HistoricalQuote current;
			while (historyItr.hasNext()) {
				current = historyItr.next();
				GregorianCalendar g = (GregorianCalendar) current.getDate();
				System.out.println(g.get(GregorianCalendar.DAY_OF_MONTH) + ": " + current.getAdjClose().setScale(2, RoundingMode.HALF_UP).toString());
			}
			
			//System.out.println(stock.getHistory(from, to, interval));	
		}
		
		return prof;
	}
	

	
}
