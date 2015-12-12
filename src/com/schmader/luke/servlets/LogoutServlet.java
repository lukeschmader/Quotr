package com.schmader.luke.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie loginCookie = new Cookie("user",null);
		Cookie loginCookie2 = new Cookie("user2",null);
        //setting cookie to expiry in 28 days
        loginCookie.setMaxAge(0);
        loginCookie2.setMaxAge(0);
        loginCookie2.setPath("/");
        response.addCookie(loginCookie);
        response.addCookie(loginCookie2);
        System.out.println("LOGGING OUT");
        response.sendRedirect("main.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie loginCookie = new Cookie("user",null);
		Cookie loginCookie2 = new Cookie("user2",null);
        //setting cookie to expiry in 28 days
        loginCookie.setMaxAge(0);
        loginCookie2.setMaxAge(0);
        response.addCookie(loginCookie);
        response.addCookie(loginCookie2);
        System.out.println("LOGGING OUT");
        response.sendRedirect("main.html");
	}

}
