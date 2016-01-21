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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schmader.luke.Test;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Servlet implementation class AnalysisServlet
 */
@WebServlet("/AnalysisServlet")
public class AnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnalysisServlet() {
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
		        
		        //String user = request.getParameter("username");
		        String dburl = "jdbc:mysql://localhost:3306/STOCK_APP";
		        String dbuser = "webuser";
		        String dbpassword = "webuser";

		        try {
		        	
		            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
		            pst = con.prepareStatement("SELECT TIME_INTERVAL,SUMMARY,MOVING_AVERAGES,TECH_INDICATORS,MA5_EXP,MA10_EXP,MA20_EXP,MA50_EXP,MA100_EXP,MA200_EXP,RSI14,STOCH96,STOCHRSI14,MACD1226,ATR14,ADX14,CCI14,HIGHS_LOWS_14,UO,ROC,WILLIAMSR,BULLBEAR13, ID_GROUP, DT_CAPTURED, PRICE FROM TECH_ANLYSS_ONGOING");
		            rs = pst.executeQuery();
		            
		            List<String> recordList = new ArrayList<String>();
		            StringBuffer returnData;
		            returnData = new StringBuffer ("{\"Analysis\":[");
		           
		            boolean hasResults = false;
		            int k = 1;
		            
		            while (rs.next()) {
		            	returnData.append("{");
		            	returnData.append("\"Interval\":\"" + rs.getString(1) + "\",");//TimeInterval());
		            	returnData.append("\"Sumry\":\"" + rs.getInt(2) + "\",");//Summary());
		            	returnData.append("\"MovS\":\"" + rs.getInt(3) + "\",");//MovingAverages());
		            	returnData.append("\"TechS\":\"" + rs.getInt(4) + "\",");//TechnicalIndicators());
		            	returnData.append("\"MA5\":\"" + rs.getInt(5) + "\",");//MA5exp());
		            	returnData.append("\"MA10\":\"" + rs.getInt(6) + "\",");//MA10exp());
		            	returnData.append("\"MA20\":\"" + rs.getInt(7) + "\",");//MA20exp());
		            	returnData.append("\"MA50\":\"" + rs.getInt(8) + "\",");//MA50exp());
		            	returnData.append("\"MA100\":\"" + rs.getInt(9) + "\",");//MA100exp());
		            	returnData.append("\"MA200\":\"" + rs.getInt(10) + "\",");//MA200exp());
		            	returnData.append("\"RSI\":\"" + rs.getInt(11) + "\",");//RSI14());
		            	returnData.append("\"STOCH\":\"" + rs.getInt(12) + "\",");//STOCH96());
		            	returnData.append("\"STCHSI\":\"" + rs.getInt(13) + "\",");//STOCHRSI14());
		            	returnData.append("\"MACD\":\"" + rs.getInt(14) + "\",");//MACD1226());
		            	returnData.append("\"ATR\":\"" + rs.getInt(15) + "\",");//ATR14());
		            	returnData.append("\"ADX\":\"" + rs.getInt(16) + "\",");//ADX14());
		            	returnData.append("\"CCI\":\"" + rs.getInt(17) + "\",");//CCI14());
		            	returnData.append("\"HL\":\"" + rs.getInt(18) + "\",");//HighsLows14());
		            	returnData.append("\"UO\":\"" + rs.getInt(19) + "\",");//UO());
		            	returnData.append("\"ROC\":\"" + rs.getInt(20) + "\",");//ROC());
		            	returnData.append("\"WillR\":\"" + rs.getInt(21) + "\",");//WilliamsR());
		            	returnData.append("\"BllBr\":\"" + rs.getInt(22) + "\",");//BullBear13());
		            	returnData.append("\"Group\":\"" + rs.getInt(23) + "\",");//Group());  
		            	returnData.append("\"Captured\":\"" + rs.getTimestamp(24) + "\",");//Group());  
		            	returnData.append("\"Price\":\"" + rs.getDouble(25) + "\"");//Captured
			            
			            returnData.append("}");
						 if(rs.next())
						 {
							 rs.previous();
							 returnData.append(",");
						 }
						 k++;
		            }
		            returnData.append("]}");
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


}
