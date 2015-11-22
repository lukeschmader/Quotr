package com.schmader.luke.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Servlet implementation class GraphServlet
 */
@WebServlet("/GraphServlet")
public class GraphServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String symbol = request.getParameter("symbol");
		String option = request.getParameter("option");
		System.out.println("Graph");
		System.out.println("SYM: " + symbol);
		System.out.println("Option: " + option);
		if(symbol != null && symbol != "")
		{
			String returnString = "";
			returnString = getGraphData(symbol, option);
			response.getWriter().write(returnString);


		}
		
	}
	
	private static String getGraphData(String sym, String option) 
	{
		Stock stock = YahooFinance.get(sym);
		
		if(stock.getName() != null && stock.getName() != "" && !stock.getName().equals("N/A"))
		{		
			System.out.println(stock.getName());
			
			ArrayList<String> dates = new ArrayList();
			ArrayList<String> values = new ArrayList();			
			BigDecimal firstb = new BigDecimal(1);
			BigDecimal lastb = new BigDecimal(1);
			boolean first = true;
			if(option.equals("1"))
			{
				//1 Month
				Calendar end = Calendar.getInstance();
				Calendar start = Calendar.getInstance();
				start.add(Calendar.MONTH, -1);
				List<HistoricalQuote> history = stock.getHistory(start, end, Interval.DAILY);
				Iterator<HistoricalQuote> historyItr = history.iterator();
				HistoricalQuote current;
				
				while (historyItr.hasNext()) {
					
					current = historyItr.next();
					if(first){//first is last value
						lastb = current.getAdjClose();
						first = false;
						
					}
					if(!historyItr.hasNext())
					{
						
						firstb = current.getAdjClose();
					}
					
					
					GregorianCalendar g = (GregorianCalendar) current.getDate();
					
					
					if(!Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)).equals("2")){
						dates.add(Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)));
					}
					else
					{
						
						//dates.add(Integer.toString(g.get(GregorianCalendar.MONTH)) + "/" + Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)));
						dates.add(Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)));
					}
					values.add(current.getAdjClose().setScale(2, RoundingMode.HALF_UP).toString());	
				}
			}
			else if(option.equals("2"))
			{
				//6 months
				Calendar end = Calendar.getInstance();
				Calendar start = Calendar.getInstance();
				start.add(Calendar.MONTH, -6);
				List<HistoricalQuote> history = stock.getHistory(start, end, Interval.DAILY);
				Iterator<HistoricalQuote> historyItr = history.iterator();
				HistoricalQuote current;
				while (historyItr.hasNext()) {
					current = historyItr.next();
					if(first){//first is last value
						lastb = current.getAdjClose();
						first = false;
						
					}
					if(!historyItr.hasNext())
					{
						
						firstb = current.getAdjClose();
					}
					GregorianCalendar g = (GregorianCalendar) current.getDate();
					if(g.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.WEDNESDAY)
					{
					dates.add(Integer.toString(g.get(GregorianCalendar.MONTH)+1) + "/" + Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)));
					}
					else
					{
						dates.add("");
					}
					values.add(current.getAdjClose().setScale(2, RoundingMode.HALF_UP).toString());	
				}
			}
			else if(option.equals("3"))
			{
				//1 Year
				Calendar end = Calendar.getInstance();
				Calendar start = Calendar.getInstance();
				start.add(Calendar.YEAR, -1);
				List<HistoricalQuote> history = stock.getHistory(start, end, Interval.WEEKLY);
				Iterator<HistoricalQuote> historyItr = history.iterator();
				HistoricalQuote current;
				while (historyItr.hasNext()) {
					current = historyItr.next();
					if(first){//first is last value
						lastb = current.getAdjClose();
						first = false;
						
					}
					if(!historyItr.hasNext())
					{
						
						firstb = current.getAdjClose();
					}
					GregorianCalendar g = (GregorianCalendar) current.getDate();
					dates.add(Integer.toString(g.get(GregorianCalendar.MONTH)+1) + "/" + Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)));
					values.add(current.getAdjClose().setScale(2, RoundingMode.HALF_UP).toString());	
				}
			}
			else if(option.equals("4"))
			{
				//5 Years
				Calendar end = Calendar.getInstance();
				Calendar start = Calendar.getInstance();
				start.add(Calendar.YEAR, -5);
				List<HistoricalQuote> history = stock.getHistory(start, end, Interval.MONTHLY);
				Iterator<HistoricalQuote> historyItr = history.iterator();
				HistoricalQuote current;
				
				while (historyItr.hasNext()) {
					current = historyItr.next();
					if(first){//first is last value
						lastb = current.getAdjClose();
						first = false;
						
					}
					if(!historyItr.hasNext())
					{
						
						firstb = current.getAdjClose();
					}
					GregorianCalendar g = (GregorianCalendar) current.getDate();	
					if((g.get(GregorianCalendar.MONTH)+1) % 2 == 0)
					{
					dates.add(Integer.toString(g.get(GregorianCalendar.MONTH)+1) + "/" + Integer.toString(g.get(GregorianCalendar.YEAR)));
					}
					else
					{
						dates.add("");
					}
					values.add(current.getAdjClose().setScale(2, RoundingMode.HALF_UP).toString());	
				}
			}
			
			
			if(values.size() > 5)
			{
			//Convert data to JSON
			StringBuffer returnString  = new StringBuffer("{");
			//values
			returnString.append("\"values\":[");
			for(int i= values.size()-1; i >= 0; i--)
			{
				
				returnString.append(values.get(i));
				
				if(i != 0)
				{
					returnString.append(",");
				}
			}
			returnString.append("],");		
			
			//dates			
			returnString.append("\"dates\":[");
			for(int i= values.size()-1; i >= 0; i--)
			{
				
				returnString.append("\""+dates.get(i)+"\"");
				
//				if(!dates.get(i).equals("15") && !dates.get(i).equals("28") )
//				{
//					returnString.append("\"\"");
//				}
//				else
//				{
//					returnString.append(dates.get(i));
//				}
				if(i != 0)
				{
					returnString.append(",");
				}
			}
			returnString.append("],");	
			
			//diff pct
			returnString.append("\"info\":{");
			
			//Double firstd = firstb.doubleValue();
			//System.out.println(firstd);
			//Double lastd = lastb.doubleValue();
			//System.out.println(lastd);
			
			//Double d = ((lastd-firstd)/(firstd))* 100.00;
			BigDecimal oneHundred = new BigDecimal(100.0);
			double d = (
					((lastb.subtract(firstb)).divide((firstb), 4, RoundingMode.HALF_UP)).multiply(oneHundred)
					).setScale(2, RoundingMode.HALF_UP).doubleValue();
			
			
			switch(option)
			{
			case "1":
				returnString.append("\"interval\":\"Month\"," );
				break;
			case "2":
				returnString.append("\"interval\":\"6 Months\",");
				break;
			case "3":
				returnString.append("\"interval\":\"1 Year\",");
				break;
			case "4":
				returnString.append("\"interval\":\"5 Years\"," );
				break;
			}
			
			returnString.append("\"diff\":" + d);
			
			returnString.append("}");
			
			returnString.append("}");
			
			System.out.println(returnString.toString());
			
			return returnString.toString();
			}
			else
			{
				return "";
			}
		}
		return "";
		
	}
	

	
}
