package com.schmader.luke.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schmader.luke.Test;

/**
 * Servlet implementation class AddToFavoritesServlet
 */
@WebServlet("/AddToFavoritesServlet")
public class AddToFavoritesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToFavoritesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user = request.getParameter("username");
	    String symbol = request.getParameter("symbol");

	    //Query for user with password
	    System.out.println(user);
	    System.out.println(symbol);

		Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        
        String dburl = "jdbc:mysql://localhost:3306/STOCK_APP";
        String dbuser = "webuser";
        String dbpassword = "webuser";

        try {
        	
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("INSERT INTO USER_FAVORITES(USERNAME, SYMBOL) VALUES(?,?);");
            pst.setString(1, user);
            pst.setString(2, symbol);
            pst.executeUpdate();
           
            

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
