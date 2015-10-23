package test;

import java.io.IOException;
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
     * @see HttpServlet#HttpServlet()
     */
    public GraphServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String symbol = request.getParameter("symbol");
		if(symbol != null && symbol != "")
		{
			String returnString = "";
			returnString = getGraphData(symbol);
			response.getWriter().write(returnString);


		}
		
	}
	
	private static String getGraphData(String sym) 
	{
		Stock stock = YahooFinance.get(sym);
		
		if(stock.getName() != null && stock.getName() != "" && !stock.getName().equals("N/A"))
		{		
			System.out.println(stock.getName());
			
			ArrayList<String> dates = new ArrayList();
			ArrayList<String> values = new ArrayList();			
			
			//1 Month
			Calendar end = Calendar.getInstance();
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MONTH, -1);
			List<HistoricalQuote> history = stock.getHistory(start, end, Interval.DAILY);
			Iterator<HistoricalQuote> historyItr = history.iterator();
			HistoricalQuote current;
			while (historyItr.hasNext()) {
				current = historyItr.next();
				GregorianCalendar g = (GregorianCalendar) current.getDate();
				dates.add(Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)));
				values.add(current.getAdjClose().setScale(2, RoundingMode.HALF_UP).toString());	
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
				
				returnString.append(dates.get(i));
				
				if(i != 0)
				{
					returnString.append(",");
				}
			}
			returnString.append("]");	
			
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
