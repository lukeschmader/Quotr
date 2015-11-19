package com.schmader.luke.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.schmader.luke.Test;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Get Outa Here");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user = request.getParameter("username");
	    String password = request.getParameter("password");

	    //Query for user with password
	    System.out.println(user);
	    System.out.println(password);

		Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        
        String dburl = "jdbc:mysql://localhost:3306/STOCK_APP";
        String dbuser = "webuser";
        String dbpassword = "webuser";

        try {
        	
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT * FROM USER_PROF where USERNAME = ? and PASSWORD = ?");
            pst.setString(1, user);
            pst.setString(2, password);
            rs = pst.executeQuery();
            
            String message = "{\"message\":\"fail\"}";
            
            if (rs.next()) {
                System.out.println(rs.getString(2));
                message = "{\"message\":\"success\"}";
                System.out.println("{\"user\":\"" + rs.getString(2)+ "\"");
                Cookie loginCookie = new Cookie("user","{\"user\":\"" + rs.getString(2)+ "\"}");
    	        //setting cookie to expiry in 1 year
    	        loginCookie.setMaxAge(60 * 60 * 24 * 28);
    	        response.addCookie(loginCookie);
    	        response.sendRedirect("redirectToMain.jsp");
            }
            else
            {
            	
     	        response.sendRedirect("FailedLogin.html");
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
