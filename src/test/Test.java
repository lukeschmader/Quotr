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
			

			returnString = getGraphData(symbol);
					


		}
	}
	
	private static String getGraphData(String sym)
	{
		Stock stock = YahooFinance.get(sym);
		
		if(stock.getName() != null)
		{		
			System.out.println(stock.getName());
			
			ArrayList<String> dates = new ArrayList();
			ArrayList<String> values = new ArrayList();			
			
			//1 Month
			Calendar end = Calendar.getInstance();
			Calendar start = Calendar.getInstance();
			//start.add(Calendar.MONTH, -1);
			List<HistoricalQuote> history = stock.getHistory();
			Iterator<HistoricalQuote> historyItr = history.iterator();
			HistoricalQuote current;
			while (historyItr.hasNext()) {
				current = historyItr.next();
				GregorianCalendar g = (GregorianCalendar) current.getDate();
				System.out.println(g.getTime());
				dates.add(Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)));
				values.add(current.getAdjClose().setScale(2, RoundingMode.HALF_UP).toString());	
			}
			
			
			//Convert data to JSON
			StringBuffer returnString  = new StringBuffer("{");
			//values
			returnString.append("\"points\":[");
			for(int i= 0; i < values.size(); i++)
			{
				
				returnString.append(values.get(i));
				
				if(i != (values.size()-1))
				{
					returnString.append(",");
				}
			}
			returnString.append("],");		
			
			//dates			
			returnString.append("\"dates\":[");
			for(int i= 0; i < values.size(); i++)
			{
				
				returnString.append(dates.get(i));
				
				if(i != (dates.size()-1))
				{
					returnString.append(",");
				}
			}
			returnString.append("]");	
			
			returnString.append("}");
			
			System.out.println(returnString.toString());
			return returnString.toString();
		}
		return "";
		
	}
	

	
}
