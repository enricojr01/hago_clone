/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.EmployeeDAO;
import com.clone.hago_clone.models.EmployeeBean;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "StaffProfileServlet", urlPatterns = {"/staffProfileServlet"})
public class StaffProfileServlet extends HttpServlet {
	private EmployeeDAO ed;

	@Override
	public void init() {
		ConnectionDetails cdeet = DBConnections.prod();
		try {
			ed = new EmployeeDAO(
					cdeet.getUrl(),
					cdeet.getUsername(),
					cdeet.getPassword()
			);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch(action) {
			case "list":
				displayProfile(request, response);
				break;
			case "update":
				updateProfile(request, response);
				break;
			case "updateSuccess":
				updateSuccess(request, response);
				break;
			default:
				throw new ServletException("Unknown action: " + action);
		}
	}
	
	private void displayProfile(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String targetPath = "/employees/secure/staff/profile.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(targetPath);
		rd.forward(request, response);
	}

	private void updateProfile(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new ServletException("Session is not valid! Log in again.");
		}
		EmployeeBean eb = (EmployeeBean) session.getAttribute("employeeBean");

		String name = request.getParameter("employeeName");
		String email = request.getParameter("employeeEmail");
		String error = "";
		if (StringUtils.isNullOrEmpty(name)) {
			error = error + "Name can't be empty! ";
		}
		if (StringUtils.isNullOrEmpty(email)) {
			error = error + "Email can't be empty! ";
		}

		if (error.isEmpty() == false) {
			request.setAttribute("error", error);
			RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/patients/staff/profile.jsp");
			rd.forward(request, response);
		}

		eb.setName(name);
		eb.setEmail(email);

		int res;
		try {
			res = ed.updateEmployee(eb);
			if (res == 0) {
				String error2 = String.format(
						"Update failed for Employee with id %s", 
						eb.getId()
				);
				throw new ServletException(error2);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex.getMessage());
		}
		session.setAttribute("employeeBean", eb);
		response.sendRedirect(request.getContextPath() + "/staffProfileServlet?action=updateSuccess");
	}

	private void updateSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		EmployeeBean eb = (EmployeeBean) session.getAttribute("employeeBean");
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/staff/editSuccess.jsp");
		rd.forward(request, response);
	}
}
