/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.models.ServiceBean;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author Enrico Tuvera Jr
 */
@WebServlet(name="ServiceBeanServlet", urlPatterns={"/serviceBeanServlet"})
public class ServiceBeanServlet extends HttpServlet {
	private ServiceDAO sd;
	
	@Override
	public void init() {
		ConnectionDetails cdeet = DBConnections.prod();
		try {
			this.sd = new ServiceDAO(
					cdeet.getUrl(),
					cdeet.getUsername(),
					cdeet.getPassword()
			);
		} catch (ClassNotFoundException e)  {
			e.printStackTrace();
		}
	}	

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action) {
			case "list":
				listServices(request, response);
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

	private void listServices(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ArrayList<ServiceBean> results = new ArrayList<>();
		
		try {
			results = sd.findAllServices();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("serviceList", results);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/list.jsp");
		rd.forward(request, response);	
	}

	private void addDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/add.jsp")	;
		rd.forward(request, response);
	}

	private void addSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String serviceName = request.getParameter("serviceName");
		String serviceDescription = request.getParameter("serviceDescription");
		String error = "";

		if (StringUtils.isNullOrEmpty(serviceName)) {
			error = error + "Service name cannot be empty! ";
		}

		if (StringUtils.isNullOrEmpty(serviceDescription)) {
			error = error + "Service description cannot be empty! ";
		}

		if (error.isEmpty() == false) {
			request.setAttribute("error", error);
			RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/add.jsp");
			rd.forward(request, response);
		}
		
		ServiceBean sb;
		try {
			sb = sd.createService(serviceName, serviceDescription);
			String targetPath = String.format(
					"%s/serviceBeanServlet?action=addSuccess&id=%s&name=%s&description=%s", 
					request.getContextPath(),
					sb.getId(), 
					sb.getName(), 
					sb.getDescription()
			);
			response.sendRedirect(targetPath);
		} catch (SQLException e) { 
			throw new ServletException(e.getMessage());
		}
	}

	private void addSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/addSuccess.jsp");
		rd.forward(request, response);
	}

	private void editDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String serviceId = request.getParameter("id");
	
		ServiceBean sb;
		try {
			sb = sd.findServiceById(Long.parseLong(serviceId));
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("serviceBean", sb);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/edit.jsp");
		rd.forward(request, response);
	}
	
	private void editSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String serviceId = request.getParameter("id")	;
		String serviceName = request.getParameter("serviceName");
		String serviceDescription = request.getParameter("serviceDescription");
		String error = "";

		if (StringUtils.isNullOrEmpty(serviceName)) {
			error = error + "Service name can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(serviceDescription)) {
			error = error + "Service description can't be empty! ";
		}

		if (error.isEmpty() == false) {
			ServiceBean sb;	
			try {
				sb = sd.findServiceById(Long.parseLong(serviceId));
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}

			request.setAttribute("error", error);
			RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/edit.jsp");
			rd.forward(request, response);
		} else {
			ServiceBean sb;
			try {
				sb = sd.findServiceById(Long.parseLong(serviceId));
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}
			sb.setName(serviceName);
			sb.setDescription(serviceDescription);
			
			int res;
			try {
				res = sd.updateService(sb);
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}

			if (res == 0) {
				throw new ServletException("Could not update Service with id " + serviceId);
			} else {
				String targetPath = String.format(
						"%s/serviceBeanServlet?action=editSuccess&id=%s&name=%s&description=%s", 
						request.getContextPath(),
						sb.getId(), 
						sb.getName(), 
						sb.getDescription()
				);
				response.sendRedirect(targetPath);
			}
		}
	}
	
	private void editSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/editSuccess.jsp")	;
		rd.forward(request, response);
	}

	private void deleteConfirm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String serviceId = request.getParameter("id");
		
		ServiceBean sb;
		try {
			sb = sd.findServiceById(Long.parseLong(serviceId));
			if (sb == null) {
				throw new ServletException("Service with ID " + serviceId + " not found!");
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("serviceBean", sb);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/deleteConfirm.jsp");
		rd.forward(request, response);
	}

	private void deleteSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String serviceId = request.getParameter("id");
		
		ServiceBean sb;
		try {
			sb = sd.findServiceById(Long.parseLong(serviceId));
			if (sb == null) {
				throw new ServletException("Service with id " + serviceId + " not found!");
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		int res;
		try {
			res = sd.deleteService(sb);
			if (res == 0) {
				throw new ServletException("Could not delete service with id " + serviceId + "!");
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		String targetPath = String.format(
				"%s/serviceBeanServlet?action=deleteSuccess&id=%s&name=%s&description=%s",
				request.getContextPath(),
				sb.getId(),
				sb.getName(),
				sb.getDescription()
		);
		response.sendRedirect(targetPath);
	}
	
	private void deleteSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/services/deleteSuccess.jsp");
		rd.forward(request, response);
	}
}
