package com.schmader.luke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

		

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        
        String url = "jdbc:mysql://localhost:3306/STOCK_APP";
        String user = "webuser";
        String password = "webuser";

        try {

            String cusername = "admin";
            String cpassword = "admin";
            con = DriverManager.getConnection(url, user, password);

            pst = con.prepareStatement("INSERT INTO USER_PROF(USERNAME, PASSWORD) VALUES(?,?)");
            pst.setString(1, cusername);
            pst.setString(2, cpassword);
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
}
	

	

