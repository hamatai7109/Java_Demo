package com.uniquedeveloper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("name");
		String userEmail = request.getParameter("email");
		String userPass = request.getParameter("pass");
		String userMobile = request.getParameter("contact");
		RequestDispatcher dispatcher= null;
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3309/java_demo?useSSL=false", "root", "");
			PreparedStatement pst = con.prepareStatement("insert into users(userName, userPass, userEmail, userMobile) values(?,?,?,?)");
			pst.setString(1, userName);
			pst.setString(2, userPass);
			pst.setString(3, userEmail);
			pst.setString(4, userMobile);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");

			if(rowCount > 0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request, response);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				con.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
