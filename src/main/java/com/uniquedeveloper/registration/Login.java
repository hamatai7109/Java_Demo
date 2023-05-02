package com.uniquedeveloper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    response.setContentType("text/html");

	    String userName = request.getParameter("username");
	    String userPass = request.getParameter("password");
	    HttpSession session = request.getSession();
	    RequestDispatcher dispatcher = null;
	    
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3309/java_demo?useSSL=false", "root", "");
	        PreparedStatement pst = con.prepareStatement("SELECT * FROM users where userName = ? and userPass = ?");
	        pst.setString(1, userName);
	        pst.setString(2, userPass);
	        
	        ResultSet rs = pst.executeQuery();

	        System.out.println("User Name: " + userName);
	        System.out.println("User Password: " + userPass);

	        if (rs.next()) {
	            System.out.println("Login Successful");
	            session.setAttribute("name", rs.getString("userName"));
	            request.setAttribute("status", "success");
	            dispatcher = request.getRequestDispatcher("index.jsp");
	        } else {
	            System.out.println("Login Failed");
	            request.setAttribute("status", "failed");
	            dispatcher = request.getRequestDispatcher("login.jsp");
	        }

	        response.getWriter().flush();
	        dispatcher.forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
