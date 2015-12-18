package com.schmader.luke.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schmader.luke.Test;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Servlet implementation class FavoritesMonthGraphServlet
 */
@WebServlet("/FavoritesMonthGraphServlet")
public class FavoritesMonthGraphServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoritesMonthGraphServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				Connection con = null;
		        ResultSet rs = null;
		        PreparedStatement pst = null;        
		        
		        String user = request.getParameter("username");
		        String dburl = "jdbc:mysql://localhost:3306/STOCK_APP";
		        String dbuser = "webuser";
		        String dbpassword = "webuser";

		        try {
		        	
		            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
		            pst = con.prepareStatement("SELECT * FROM USER_FAVORITES where USERNAME = ?");
		            pst.setString(1, user);
		            
		            rs = pst.executeQuery();
		            StringBuffer returnData;
		            returnData = new StringBuffer ("fail");		
		            List<String> symbolList = new ArrayList<String>();
		            boolean hasResults = false;
		            while (rs.next()) {
		            	symbolList.add(rs.getString(3));
		                System.out.println(rs.getString(3));
		                System.out.println("{\"user\":\"" + rs.getString(2)+ "\"");
		                hasResults = true;             
		            }
		            
		            if(hasResults)
		            {
		            	
			            
			            returnData = new StringBuffer ("{\"companies\":[");		
			            //Get Quote and Iterate through companies
						String[] symbols = symbolList.toArray(new String[symbolList.size()]);
						Map<String, Stock> stocks = YahooFinance.get(symbols, true);
						int count = 0;
						for (Map.Entry<String, Stock> stock : stocks.entrySet()){
							count++;
							returnData.append(getGraphData(stock.getValue().getSymbol(), "1"));
							if(count < stocks.entrySet().size())
							{
								returnData.append(",");
							}
							
						}
						returnData.append("]}");						
		            }
		            else
		            {
		            	returnData = new StringBuffer ("{\"favorites\":[],");
		            	returnData.append("\"stats\":{}");
		            	returnData.append("}");
		            }
		            
		            System.out.println(returnData.toString());
					response.getWriter().write(returnData.toString());
		            

		        } catch (SQLException ex) {
		            Logger lgr = Logger.getLogger(Test.class.getName());            
		            lgr.log(Level.SEVERE, ex.getMessage(), ex);

		        } finally {

		            try {
		            	
		                if (pst != null) {
		                    pst.close();
		                }
		                if (con != null) {
		                    con.close();
		                }
		                

		            } catch (SQLException ex) {
		                Logger lgr = Logger.getLogger(Test.class.getName());
		                lgr.log(Level.SEVERE, ex.getMessage(), ex);
		            }
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
					
					
					if(!Integer.toString(g.get(GregorianCalendar.DAY_OF_MONTH)).equals("1")){
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
			
			
			if(values.size() > 5) //Not sure why 5 was picked. I guess its smaller than what the smallest range would be for a graph.
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
			}
			returnString.append("\"symbol\":\""+stock.getSymbol()+"\"," );
			returnString.append("\"name\":\""+stock.getName()+"\"," );
			returnString.append("\"diff\":" + d);
			
			returnString.append("}");
			
			returnString.append("}");
			
			//System.out.println(returnString.toString());
			
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
