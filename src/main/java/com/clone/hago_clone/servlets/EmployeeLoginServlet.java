/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.servlets;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.EmployeeDAO;
import com.clone.hago_clone.models.EmployeeBean;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Enrico Tuvera Jr
 */
@WebServlet(name="employeeLoginServlet", urlPatterns={"/employeeLogin"})
public class EmployeeLoginServlet extends HttpServlet {
	private EmployeeDAO ed;
	
	@Override
	public void init() {
		ConnectionDetails cdt = DBConnections.prod();
		try {
			ed = new EmployeeDAO(
					cdt.getUrl(),
					cdt.getUsername(),
					cdt.getPassword()
			);	
		} catch (ClassNotFoundException e) {
			// TODO: figure out how to correctly handle exceptions at the
			// 		 servlet level.
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response
	) throws ServletException, IOException {
		// TODO: redirect to dashboard if a user's already logged in.
		response.sendRedirect("employees/login.jsp");
	}

	@Override	
	protected void doPost(
			HttpServletRequest request, 
			HttpServletResponse response
	) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		RequestDispatcher start = request.getRequestDispatcher(
				"employees/login.jsp"
		);
		RequestDispatcher success = request.getRequestDispatcher(
				"employees/secure/dashboard.jsp"
		);

		if (StringUtils.isNullOrEmpty(email)) {
			request.setAttribute("error", "Email can't be empty!");
			start.forward(request, response);
		}
		
		if (StringUtils.isNullOrEmpty(password)) {
			request.setAttribute("error", "Password can't be empty!");
			start.forward(request, response);
		}
		
		EmployeeBean eb;
		try {
			eb = ed.findEmployeeByEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}

		if (eb != null) {
			char[] pass = password.toCharArray();
			char[] hash = eb.getPassword().toCharArray();
			
			BCrypt.Result result = BCrypt
					.verifyer()
					.verify(pass, hash);
			
			if (result.verified == true) {
				HttpSession session = request.getSession(true);
				session.setAttribute("employeeBean", eb);
				success.forward(request, response);
			} else {
				request.setAttribute(
						"error", 
						"Incorrect email or password, try again."
				);
				start.forward(request, response);
			}
		} else {
			request.setAttribute(
					"error", 
					"Incorrect email or password, try again."
			);
			start.forward(request, response);
		}
	}
}
