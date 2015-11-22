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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schmader.luke.Test;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Servlet implementation class FavoritesRetrieverServlet
 */
@WebServlet("/FavoritesRetrieverServlet")
public class FavoritesRetrieverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoritesRetrieverServlet() {
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
            
            String message = "{\"message\":\"fail\"}";
            List<String> symbolList = new ArrayList<String>();
            boolean hasResults = false;
            while (rs.next()) {
            	symbolList.add(rs.getString(3));
                System.out.println(rs.getString(3));
                System.out.println("{\"user\":\"" + rs.getString(2)+ "\"");
                hasResults = true;             
            }
            StringBuffer returnData;
            if(hasResults)
            {
            	
            
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
			 BigDecimal avgPeg = new BigDecimal(0);
			 
			 returnData = new StringBuffer ("{\"favorites\":[");		
			 for (Map.Entry<String, Stock> stock : stocks.entrySet())
			 {			 
			  //   System.out.println(stock.getKey() + "/" + stock.getValue());		
				 k++;
				 returnData.append("{");
				 returnData.append("\"symbol\":\"" +  stock.getValue().getSymbol() + "\",");
				 returnData.append("\"name\":\"" +  stock.getValue().getName() + "\",");
				 
				 returnData.append("\"yieldPct\":" +  stock.getValue().getDividend().getAnnualYieldPercent().setScale(2, RoundingMode.HALF_UP) + ",");
				 avgYieldPct = avgYieldPct.add(stock.getValue().getDividend().getAnnualYieldPercent());
				 
				 returnData.append("\"price\":" +  stock.getValue().getQuote().getPrice().setScale(2, RoundingMode.HALF_UP) + ",");
				 avgPrice = avgPrice.add(stock.getValue().getQuote().getPrice());
				 
				 returnData.append("\"change\":" +  stock.getValue().getQuote().getChange().setScale(2, RoundingMode.HALF_UP) + ",");
				 avgChange = avgChange.add(stock.getValue().getQuote().getChange());
				 
				 returnData.append("\"changePct\":" +  stock.getValue().getQuote().getChangeInPercent().setScale(2, RoundingMode.HALF_UP) + ",");
				 avgChangeInPercent = avgChangeInPercent.add(stock.getValue().getQuote().getChangeInPercent());			 
				 
				 returnData.append("\"oneYearTargetPrice\":" +  stock.getValue().getStats().getOneYearTargetPrice().setScale(2, RoundingMode.HALF_UP) + ",");
				 avgTarget = avgTarget.add(stock.getValue().getStats().getOneYearTargetPrice());			 
				 
				 returnData.append("\"peg\":" +  stock.getValue().getStats().getPeg().setScale(2, RoundingMode.HALF_UP) + ",");
				 avgPeg = avgPeg.add(stock.getValue().getStats().getPeg());	
				 
				 returnData.append("\"volume\":" +  stock.getValue().getQuote().getVolume() + ",");
				 avgVolume = avgVolume.add(new BigDecimal(stock.getValue().getQuote().getVolume()));
				 
				 returnData.append("\"avgVolume\":" +  stock.getValue().getQuote().getAvgVolume() + "");
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
			 		returnData.append("\"avgAvgVolume\":\""+ avgAvgVolume.divide(bCount, 0, RoundingMode.HALF_UP) + "\",");	
			 		returnData.append("\"avgYieldPct\":\""+ avgYieldPct.divide(bCount, 2, RoundingMode.HALF_UP) + "\",");	
			 		returnData.append("\"avgPeg\":\""+ avgPeg.divide(bCount, 2, RoundingMode.HALF_UP) + "\",");	
			 		returnData.append("\"favoritesCount\":\""+ k + "\"");	
			 returnData.append("}}");
            }
            else
            {
            	returnData = new StringBuffer ("{\"favorites\":[],");
            	returnData.append("\"stats\":{}");
            	returnData.append("}");
            }
			 //Write back
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
