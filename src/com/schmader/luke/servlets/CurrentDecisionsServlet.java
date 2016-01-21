package com.schmader.luke.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schmader.luke.Test;

/**
 * Servlet implementation class CurrentDecisionsServlet
 */
@WebServlet("/CurrentDecisionsServlet")
public class CurrentDecisionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrentDecisionsServlet() {
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
            pst = con.prepareStatement("SELECT ID_GROUP, SYMBOL, DT_CREATED, DECISION FROM CURRENT_DECISION_HST");
            rs = pst.executeQuery();
            
            List<String> recordList = new ArrayList<String>();
            StringBuffer returnData;
            returnData = new StringBuffer ("{\"Objects\":[");
           
            boolean hasResults = false;
            int k = 1;
            
            while (rs.next()) {
            	returnData.append("{");
            	returnData.append("\"Group\":\"" + rs.getInt(1) + "\",");
            	returnData.append("\"Symbol\":\"" + rs.getString(2) + "\",");
            	returnData.append("\"Dt_created\":\"" + rs.getInt(3) + "\",");
            	returnData.append("\"Decision\":\"" + rs.getInt(4) + "\"");
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
