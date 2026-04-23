/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.EmployeeDAO;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.EmployeeBean;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Enrico Tuvera Jr
 */
@WebServlet(name = "EmployeeBeanServlet", urlPatterns = {"/employeeBeanServlet"})
public class EmployeeBeanServlet extends HttpServlet {
	private EmployeeDAO ed;
	private ClinicDAO cd;
	
	@Override
	public void init() {
		ConnectionDetails cd = DBConnections.prod();
		try {
			this.ed = new EmployeeDAO(
					cd.getUrl(),
					cd.getUsername(),
					cd.getPassword()
			);
			this.cd = new ClinicDAO(
					cd.getUrl(),
					cd.getUsername(),
					cd.getPassword()
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
				listEmployees(request, response);
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
			case "editDisplay":
				editDisplay(request, response);
				break;
			case "editSave":
				editSave(request, response);
				break;
			case "editSuccess":
				editSuccess(request, response);
				break;
			case "deleteConfirm":
				deleteConfirm(request, response);
				break;
			case "deleteSave":
				deleteSave(request, response);
				break;
			case "deleteSuccess":
				deleteSuccess(request, response);
				break;
			default:
				throw new ServletException("Unknown action: " + action);
		}
	}

	private void listEmployees(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ArrayList<EmployeeBean> results = new ArrayList<>();
		
		try {
			results = ed.findAllEmployees();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("employeeList", results);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/list.jsp");
		rd.forward(request, response);
	}

	private void addDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ArrayList<ClinicBean> clinicList = new ArrayList<>();
		try {
			clinicList = cd.findClinics();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		request.setAttribute("clinicList", clinicList);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/add.jsp");
		rd.forward(request, response);
	}

	private void addSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String employeeName = request.getParameter("employeeName");
		String employeeEmail = request.getParameter("employeeEmail");
		String employeeRole = request.getParameter("employeeRole");
		String employeeClinicId = request.getParameter("employeeClinic");
		String employeePassword = request.getParameter("employeePassword");
		String error = "";

		if (StringUtils.isNullOrEmpty(employeeName)) {
			error = error + "Employee name can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(employeeEmail)) {
			error = error + "Employee email can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(employeeRole)) {
			error = error + "Employee role can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(employeeClinicId)) {
			error = error + "Employee clinic can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(employeePassword)) {
			error = error + "Employee password can't be empty! ";
		}

		if (error.isEmpty() == false) {
			ArrayList<ClinicBean> clinicList = new ArrayList<>();
			
			try {
				clinicList = cd.findClinics();
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}
			
			request.setAttribute("error", error);
			request.setAttribute("clinicList", clinicList);
			RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/add.jsp");
			rd.forward(request, response);
		} else {
			long cid = Long.valueOf(employeeClinicId);
			try {
				EmployeeBean eb = ed.addEmployee(
						employeeRole, 
						employeeName, 
						employeeEmail, 
						employeePassword, 
						cid
				);

				String targetPath = String.format(
						"employeeBeanServlet?action=addSuccess&id=%s&role=%s&name=%s&email=%s", 
						eb.getId(), 
						eb.getRole(), 
						eb.getName(), 
						eb.getEmail()
				);
				response.sendRedirect(targetPath);
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}
		}
	}

	private void addSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/addSuccess.jsp")	;
		rd.forward(request, response);
	}

	private void editDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("id"));
		ArrayList<ClinicBean> clinicList = new ArrayList<>();
		EmployeeBean eb;
		
		try {
			clinicList = cd.findClinics();
			eb = ed.findEmployeeByID(id);
			ClinicBean cb = cd.findClinicById(eb.getClinicId());
			eb.setClinic(cb);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("clinicList", clinicList);
		request.setAttribute("employeeBean", eb);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/edit.jsp");
		rd.forward(request, response);
	}

	private void editSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Long employeeId = Long.valueOf(request.getParameter("employeeID"));
		String employeeName = request.getParameter("employeeName");
		String employeeEmail = request.getParameter("employeeEmail");
		String employeeRole = request.getParameter("employeeRole");
		String employeeClinicId = request.getParameter("employeeClinic");
//		String employeePassword = request.getParameter("employeePassword");
		String error = "";

		if (StringUtils.isNullOrEmpty(employeeName)) {
			error = error + "Employee name can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(employeeEmail)) {
			error = error + "Employee email can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(employeeRole)) {
			error = error + "Employee role can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(employeeClinicId)) {
			error = error + "Employee clinic can't be empty! ";
		}

//		if (StringUtils.isNullOrEmpty(employeePassword)) {
//			error = error + "Employee password can't be empty! ";
//		}

		if (error.isEmpty() == false) {
			ArrayList<ClinicBean> clinicList = new ArrayList<>();
			EmployeeBean eb;

			System.out.println("Error: " + error);
			
			try {
				clinicList = cd.findClinics();
				eb = ed.findEmployeeByID(employeeId);
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}
			
			request.setAttribute("error", error);
			request.setAttribute("employeeBean", eb);
			request.setAttribute("clinicList", clinicList);
			RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/edit.jsp");
			rd.forward(request, response);
		} else {
			EmployeeBean eb;
			ClinicBean cb;
			try {
				eb = ed.findEmployeeByID(employeeId);
				cb = cd.findClinicById(Long.parseLong(employeeClinicId));

				eb.setName(employeeName);
				eb.setRole(employeeRole);
				eb.setEmail(employeeEmail);
				eb.setClinic(cb);
				
				ed.updateEmployee(eb);

				String targetPath = String.format(
						"%s/employeeBeanServlet?action=editSuccess&id=%s&name=%s&role=%s&email=%s&clinic=%s",
						request.getContextPath(),
						eb.getId(),
						eb.getName(),
						eb.getRole(),
						eb.getEmail(),
						cb.getName()
				);
				response.sendRedirect(targetPath);
			} catch (SQLException e) {
				throw new ServletException(e);
			}

		}
	}
			
	private void editSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/editSuccess.jsp");
		rd.forward(request, response);
	}

	private void deleteConfirm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		long employeeId = Long.parseLong(request.getParameter("id"));

		EmployeeBean eb;
		try {
			eb = ed.findEmployeeByID(employeeId);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		request.setAttribute("employeeBean", eb);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/deleteConfirm.jsp");
		rd.forward(request, response);
	}

	private void deleteSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		long employeeId = Long.parseLong(request.getParameter("id"));

		EmployeeBean eb;
		ClinicBean cb;
		try {
			eb = ed.findEmployeeByID(employeeId);
			cb = cd.findClinicById(eb.getClinicId());
			eb.setClinic(cb);

			int res = ed.deleteEmployee(eb);
			if (res == 0) {
				String error = String.format("Could not delete Employee with id %s", employeeId);
				throw new ServletException(error);
			} else {
				String targetPath = String.format(
						"%s/employeeBeanServlet?action=deleteSuccess&id=%s&name=%s&role=%s&email=%s&clinic=%s",
						request.getContextPath(),
						eb.getId(),
						eb.getName(),
						eb.getRole(),
						eb.getEmail(),
						eb.getClinic().getName()
				);
				response.sendRedirect(targetPath);
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
	}

	private void deleteSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/employees/deleteSuccess.jsp");
		rd.forward(request, response);
	}
}