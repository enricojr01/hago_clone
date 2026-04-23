/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.servlets;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.EmployeeDAO;
import com.clone.hago_clone.models.ClinicBean;
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
	private ClinicDAO cd;
	
	@Override
	public void init() {
		ConnectionDetails cdt = DBConnections.prod();
		try {
			ed = new EmployeeDAO(
					cdt.getUrl(),
					cdt.getUsername(),
					cdt.getPassword()
			);	
			cd = new ClinicDAO(
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (StringUtils.isNullOrEmpty(action)){
			action = "temporary";
		}
		switch (action) {
			case "logout":
				handleLogout(request, response);
				break;
			default:
				RequestDispatcher rd = request.getRequestDispatcher("employees/secure/dashboard.jsp");
				HttpSession session = request.getSession(false);
				if (session != null) {
					Object oeb = session.getAttribute("employeeBean");
					if (oeb == null) {
						response.sendRedirect("employees/login.jsp");
					} else {
						EmployeeBean eb = (EmployeeBean) oeb;
						request.setAttribute("employeeBean", eb);
						rd.forward(request, response);
					}
				} else {
					response.sendRedirect("employees/login.jsp");
				}
		}
	}

	@Override	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
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
		ClinicBean cb;
		try {
			eb = ed.findEmployeeByEmail(email);
			cb = cd.findClinicById(eb.getClinicId());
			if (cb != null){
				eb.setClinic(cb);
			}
			System.out.println(eb);
			System.out.println(eb.getClinicId());
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
				System.out.println("eb: " + eb);
				System.out.println("eb.name: " + eb.getName());
				System.out.println("eb.getRole()" + eb.getRole());
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

	public void handleLogout(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);	
		session.removeAttribute("employeeBean");
		response.sendRedirect(request.getContextPath());
	}
}
