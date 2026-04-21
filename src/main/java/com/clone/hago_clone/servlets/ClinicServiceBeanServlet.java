/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.ClinicServiceDAO;
import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicServiceBean;
import com.clone.hago_clone.models.ServiceBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
@WebServlet(name = "ClinicServiceBeanServlet", urlPatterns = {"/clinicServiceBeanServlet"})
public class ClinicServiceBeanServlet extends HttpServlet {
	private ClinicDAO cd;
	private ServiceDAO sd;
	private ClinicServiceDAO csd;

	@Override	
	public void init() {
		ConnectionDetails cdeet = DBConnections.prod();
		try {
			this.cd = new ClinicDAO(
					cdeet.getUrl(),
					cdeet.getUsername(),
					cdeet.getPassword()
			);

			this.sd = new ServiceDAO(
					cdeet.getUrl(),
					cdeet.getUsername(),
					cdeet.getPassword()
			);

			this.csd = new ClinicServiceDAO(
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
				listClinics(request, response);
				break;
			case "listServices":
				listClinicServices(request, response);
				break;
			case "addService":
				addClinicService(request, response);
				break;
			case "addSuccess":
				addSuccess(request, response);
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

	private void listClinics(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ArrayList<ClinicBean> results = new ArrayList<>();
		try {
			results = cd.findClinics();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		request.setAttribute("clinicList", results);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicServices/list.jsp");
		rd.forward(request, response);
	}

	private void listClinicServices(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicId = request.getParameter("clinicId");

		// simple reference to the current clinic
		ClinicBean cb;
		// the complete list of all services loaded into the system
		ArrayList<ServiceBean> fullServiceList = new ArrayList<>();

		// the complete list of every ClinicServiceBean associated with the given clinic.
		ArrayList<ClinicServiceBean> clinicServicePairs = new ArrayList<>();

		// the complete list of every ServiceBean from the above list.
		ArrayList<ServiceBean> associatedServices = new ArrayList<>();

		// empty list to hold the available services i.e. ones that have not been associated
		// with this clinic and are "available" to be added.
		ArrayList<ServiceBean> availableServices = new ArrayList<>();

		try {
			// first we get the clinic
			cb = cd.findClinicById(Long.parseLong(clinicId));
			if (cb == null) {
				String error = String.format("No clinic matching id %s", clinicId);
				throw new ServletException(error);
			}
			// then we get all the services available
			fullServiceList = sd.findAllServices();
			// then we get all the ClinicServiceBeans for the given clinicId
			clinicServicePairs = csd.findClinicServiceByClinicId(cb.getId());
			// then we extract the ServiceBeans and place them into associatedServices
			for (int i = 0; i < clinicServicePairs.size(); i++) {
				ClinicServiceBean csb = clinicServicePairs.get(i);				
				associatedServices.add(csb.getService());
			}	

			// then we convert them to sets.
			Set<ServiceBean> all = new HashSet<>();
			Set<ServiceBean> associated = new HashSet<>();

			all.addAll(fullServiceList);
			associated.addAll(associatedServices);

			System.out.println("all: " + all.toString());
			System.out.println("associated: " + associated.toString());
			// then we determine availableServices by getting the relative complement
			// of all and associated
			// i.e the items that are in fullServiceList that are not in associatedServices
			all.removeAll(associated);
			System.out.println("available: " + all.toString());

			// then we load them into the associatedServices ArrayList to conform to the
			// interface we already have.
			Iterator<ServiceBean> sbIterator = all.iterator();
			while (sbIterator.hasNext()) {
				ServiceBean sb = sbIterator.next();
				availableServices.add(sb);
			}
			System.out.println("final: " + availableServices);
			
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("clinicBean", cb);
		request.setAttribute("serviceList", associatedServices);
		request.setAttribute("availableServiceList", availableServices);
		
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicServices/listServices.jsp");
		rd.forward(request, response);
	}

	private void addClinicService(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicId = request.getParameter("clinicId");
		String serviceId = request.getParameter("serviceId");

		if (serviceId.equals("empty")) {
			String targetPath = String.format(
					"%s/clinicServiceBeanServlet?action=listServices&clinicId=%s", 
					request.getContextPath(), 
					clinicId
			);
			response.sendRedirect(targetPath);
			return;
		}
		
		ClinicBean cb;
		ServiceBean sb;
		try {
			cb = cd.findClinicById(Long.parseLong(clinicId));
			sb = sd.findServiceById(Long.parseLong(serviceId));
			
			if (cb == null) {
				throw new ServletException("Clinic with id " + clinicId + " not found!");
			}
			if (sb == null) {
				throw new ServletException("Service with id " + serviceId + " not found!");
			}

			ClinicServiceBean csb = csd.createClinicService(cb, sb);
			if (csb == null) {
				String error = String.format(
						"Could not map Service %s to Clinic %s", 
						sb.getId(), 
						cb.getId()
				);
				throw new ServletException(error);
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		String targetPath = String.format(
				"%s/clinicServiceBeanServlet"
						+ "?action=addSuccess"
						+ "&serviceId=%s"
						+ "&serviceName=%s"
						+ "&serviceDescription=%s"
						+ "&clinicId=%s"
						+ "&clinicName=%s",
				request.getContextPath(),
				sb.getId(),
				sb.getName(),
				sb.getDescription(),
				cb.getId(),
				cb.getName()
		);
		response.sendRedirect(targetPath);
	}

	private void addSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicServices/addSuccess.jsp");
		rd.forward(request, response);
	}

	private void deleteConfirm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicId = request.getParameter("clinicId");
		String serviceID = request.getParameter("serviceId");
		
		ClinicBean cb;
		ServiceBean sb;
		try {
			cb = this.cd.findClinicById(Long.parseLong(clinicId));
			sb = this.sd.findServiceById(Long.parseLong(serviceID));

		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("clinicBean", cb);
		request.setAttribute("serviceBean", sb);

		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicServices/deleteConfirm.jsp");
		rd.forward(request, response);
	}

	private void deleteSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicId = request.getParameter("clinicId");
		String serviceId = request.getParameter("serviceId");
		
		ClinicServiceBean csb;
		try {
			csb = csd.findByClinicIdServiceId(
					Long.parseLong(clinicId), 
					Long.parseLong(serviceId)
			);
			if (csb == null) {
				throw new ServletException("Couldn't find CSB with matching clinic / service Ids");
			}
			int res = csd.deleteClinicService(csb);
			if (res == 0) {
				String error = String.format("ClinicServiceBean with clinicId %s and serviceId %s could not be deleted!",
						csb.getService().getId(),
						csb.getClinic().getId()
				);
				throw new ServletException(error);
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		String targetPath = String.format("%s/clinicServiceBeanServlet"
				+ "?action=deleteSuccess"
				+ "&serviceId=%s"
				+ "&serviceName=%s"
				+ "&clinicId=%s"
				+ "&clinicName=%s",
				request.getContextPath(),
				csb.getService().getId(),
				csb.getService().getName(),
				csb.getClinic().getId(),
				csb.getClinic().getName()
		);
		response.sendRedirect(targetPath);
	}
	
	private void deleteSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicServices/deleteSuccess.jsp");
		rd.forward(request, response);
	}
}

