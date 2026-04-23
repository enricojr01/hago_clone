/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.models.ClinicBean;
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
@WebServlet(name = "ClinicBeanServlet", urlPatterns = {"/clinicBeanServlet"})
public class ClinicBeanServlet extends HttpServlet {
	private ClinicDAO cd;

	@Override
	public void init() {
		ConnectionDetails cdeet = DBConnections.prod();
		try {
			this.cd = new ClinicDAO(
					cdeet.getUrl(),
					cdeet.getUsername(),
					cdeet.getPassword()
			);
		} catch (ClassNotFoundException e) {
			// TODO: redirect to server error page here
			e.printStackTrace();
		}
	}
	
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");

		switch(action) {
			case "list":
				listClinics(request, response);
				break;
			case "add":
				addDisplay(request, response);
				break;
			case "save":
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
			case "delete":
				deleteSave(request, response);
				break;
			case "deleteSuccess":
				deleteSuccess(request, response);
				break;
			default:
				String error = String.format(
						"Action %s is unknown and can't be handled!", 
						action
				);
				throw new ServletException(error);
		}
	}

	private void listClinics(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ArrayList<ClinicBean> clinicList = new ArrayList<>();
		try {
			clinicList = cd.findClinics();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		request.setAttribute("clinicList", clinicList);
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/clinics/list.jsp");
		rd.forward(request, response);
	}

	private void addDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/clinics/add.jsp");
		rd.forward(request, response);
	}

	private void addSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicName = request.getParameter("clinicName");
		String clinicAddress = request.getParameter("clinicAddress");
		String error = "";

		if (StringUtils.isNullOrEmpty(clinicName)) {
			error = error + "Clinic name can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(clinicAddress)) {
			error = error + "Clinic address can't be empty! ";
		}
		
		if (error.isEmpty() == false) {
			request.setAttribute("error", error);
			RequestDispatcher rd = request 
					.getRequestDispatcher("/employees/secure/clinics/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		ClinicBean cb;
		try {
			cb = this.cd.createClinic(clinicName, clinicAddress);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		String targetPath = String.format(
				"clinicBeanServlet?action=addSuccess&id=%s&name=%s&address=%s",
				cb.getId(),
				cb.getName(),
				cb.getAddress()
		);
		response.sendRedirect(targetPath);
	}
	
	private void addSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/clinics/addSuccess.jsp");
		rd.forward(request, response);
	}

	private void editDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("id"));
		
		ClinicBean cb;
		try {
			cb = cd.findClinicById(id);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		} 

		if (cb == null) {
			throw new ServletException("Clinic with ID " + id + " not found!");
		}

		request.setAttribute("clinicBean", cb);
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/clinics/edit.jsp");
		rd.forward(request, response);
	}

	private void editSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		long clinicID = Long.parseLong(request.getParameter("id"));
		
		ClinicBean cb;
		try {
			cb = cd.findClinicById(clinicID);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		if (cb == null) {
			throw new ServletException("Clinic with ID " + clinicID + " not found!");
		}

		cb.setName(request.getParameter("clinicName"));
		cb.setAddress(request.getParameter("clinicAddress"));

		int res = 0;
		try {
			res = cd.updateClinic(cb);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		if (res > 0) {
			String successPath = String.format(
					"%s/clinicBeanServlet?action=editSuccess&id=%s&name=%s&address=%s",
					request.getContextPath(),
					cb.getId(),
					cb.getName(),
					cb.getAddress()
			);
			response.sendRedirect(successPath);
		} else {
			String error = String.format("Clinic with ID %s not found!", cb.getId());
			throw new ServletException(error);
		}
	}

	private void editSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/clinics/editSuccess.jsp");
		rd.forward(request, response);
	}

	private void deleteConfirm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		
		ClinicBean cb;

		try {
			cb = cd.findClinicById(Long.parseLong(id));
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		if (cb == null) {
			String error = String.format("Clinic with ID %s not found!", id);
			throw new ServletException(error);
		}
		
		request.setAttribute("clinicBean", cb);
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher("/employees/secure/clinics/deleteConfirm.jsp");
		rd.forward(request, response);
	}

	private void deleteSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ClinicBean cb;

		int results;
		try {
			cb = cd.findClinicById(Long.parseLong(id));
			results = cd.deleteClinic(Long.parseLong(id));
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		if (results == 0) {
			String error = String.format("Clinic with ID %s not found!", id);
			throw new ServletException(error);
		}

		String successPath = String.format(
				"%s/clinicBeanServlet?action=deleteSuccess&id=%s&name=%s&address=%s", 
				request.getContextPath(),
				cb.getId(), 
				cb.getName(), 
				cb.getAddress()
		);
		response.sendRedirect(successPath);
	}

	private void deleteSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String successPage = "/employees/secure/clinics/deleteSuccess.jsp";
		RequestDispatcher rd = this
				.getServletContext()
				.getRequestDispatcher(successPage);
		rd.forward(request, response);
	}
}
