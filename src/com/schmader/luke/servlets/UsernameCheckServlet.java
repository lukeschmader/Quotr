package com.schmader.luke.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schmader.luke.Test;

/**
 * Servlet implementation class UsernameCheckServlet
 */
@WebServlet("/UsernameCheckServlet")
public class UsernameCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String cUsername = request.getParameter("username");
				
				System.out.println(cUsername);

				Connection con = null;
		        Statement st = null;
		        ResultSet rs = null;
		        PreparedStatement pst = null;
		        
		        String url = "jdbc:mysql://localhost:3306/STOCK_APP";
		        String user = "webuser";
		        String password = "webuser";

		        try {

		            con = DriverManager.getConnection(url, user, password);

		            
		            con = DriverManager.getConnection(url, user, password);
		            pst = con.prepareStatement("SELECT * FROM USER_PROF where USERNAME = ?");
		            pst.setString(1, cUsername);
		            rs = pst.executeQuery();
		            
		            String message = "{\"message\":\"new\"}";
		            while (rs.next()) {
		                System.out.print(rs.getInt(1));
		                System.out.print(": ");
		                System.out.println(rs.getString(2));
		                message = "{\"message\":\"exists\"}";
		                System.out.println("exists");
		            }		            
		            
		            System.out.println(message);
		            response.getWriter().write(message);
		            

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
