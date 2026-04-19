/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.db.PatientDAO;
import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.models.PatientBean;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Enrico Tuvera Jr
 */
@WebServlet(name = "PatientBeanServlet", urlPatterns = {"/patientBeanServlet"})
public class PatientBeanServlet extends HttpServlet {
	private PatientDAO pd;
	
	@Override
	public void init() {
		ConnectionDetails cdeet = DBConnections.prod();
		try {
			this.pd = new PatientDAO(
					cdeet.getUrl(),
					cdeet.getUsername(),
					cdeet.getPassword()
			);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();			
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action) {
				case "list":
					listPatients(request, response);
					break;
				case "addDisplay":
					addDisplay(request, response);
					break;
				case "addSave":
					addSave(request, response);
					break;
				case "addSuccess":
					addSuccess(request, response);
					break;
				default:
					String error = String.format("Unknown action: %s, try again!", action);
					throw new ServletException(error);
		}
	}

	private void listPatients(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ArrayList<PatientBean> results = new ArrayList<>();	

		try {
			results = this.pd.getAllPatients();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("patientList", results);
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/patients/list.jsp");
		rd.forward(request, response);
	}

	private void addDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/patients/add.jsp");
		rd.forward(request, response);
	}

	private void addSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String name = request.getParameter("patientName");
		String email = request.getParameter("patientEmail");
		String password = request.getParameter("patientPassword");
		String error = "";
		if (StringUtils.isNullOrEmpty(name)) {
			error = error + "Name field can't be empty! ";
		}
		if (StringUtils.isNullOrEmpty(email)) {
			error = error + "Email field can't be empty! ";
		}
		if (StringUtils.isNullOrEmpty(password)) {
			error = error + "Password can't be empty! ";
		}
		if (error.isEmpty() == false) {
			request.setAttribute("error", error);
			RequestDispatcher rd = request 
					.getRequestDispatcher("/employees/secure/patients/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		PatientBean pb;
		try {
			pb = pd.createPatient(name, email, password);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		String targetPath = String.format(
				"patientBeanServlet?action=addSuccess&id=%s&name=%s&email=%s", 
				pb.getId(), 
				pb.getName(), 
				pb.getEmail()
		);
		response.sendRedirect(targetPath);
	}

	private void addSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/patients/addSuccess.jsp");
		rd.forward(request, response);
	}
}
