/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import java.io.IOException;
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

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.ClinicTimeSlotDAO;
import com.clone.hago_clone.db.TimeSlotDAO;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicTimeSlotBean;
import com.clone.hago_clone.models.TimeSlotBean;

/**
 *
 * @author Enrico Tuvera Jr
 */
@WebServlet(name = "ClinicTimeSlotBeanServlet", urlPatterns = {"/clinicTimeSlotBeanServlet"})
public class ClinicTimeSlotBeanServlet extends HttpServlet {
	private ClinicDAO cd;
	private TimeSlotDAO tsd;
	private ClinicTimeSlotDAO ctsd;

	@Override	
	public void init() {
		ConnectionDetails cdeet = DBConnections.prod();
		try {
			this.cd = new ClinicDAO(
					cdeet.getUrl(),
					cdeet.getUsername(),
					cdeet.getPassword()
			);

			this.tsd = new TimeSlotDAO(
					cdeet.getUrl(),
					cdeet.getUsername(),
					cdeet.getPassword()
			);

			this.ctsd = new ClinicTimeSlotDAO(
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
			case "listClinicTimeSlots":
				listClinicTimeSlots(request, response);
				break;
			case "addClinicTimeSlot":
				addClinicTimeSlot(request, response);
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
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicTimeSlots/list.jsp");
		rd.forward(request, response);
	}

	private void listClinicTimeSlots(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicId = request.getParameter("clinicId");

		// simple reference to the current clinic
		ClinicBean cb;
		// the complete list of all Time Slots loaded into the system
		ArrayList<TimeSlotBean> fullTimeSlotList = new ArrayList<>();

		// the complete list of every associated with the given clinic.
		ArrayList<ClinicTimeSlotBean> clinicTimeSlotPairs = new ArrayList<>();

		// the complete list of every TimeSlotBean from the above list.
		ArrayList<TimeSlotBean> associatedTimeSlots = new ArrayList<>();

		// empty list to hold the available services i.e. ones that have not been associated
		// with this clinic and are "available" to be added.
		ArrayList<TimeSlotBean> availableTimeSlots = new ArrayList<>();

		try {
			// first we get the clinic
			cb = cd.findClinicById(Long.parseLong(clinicId));
			if (cb == null) {
				String error = String.format("No clinic matching id %s", clinicId);
				throw new ServletException(error);
			}
			// then we get all the Time Slots available
			fullTimeSlotList = tsd.findAllTimeSlots();
			// then we get all the ClinicServiceBeans for the given clinicId
			clinicTimeSlotPairs = ctsd.findClinicTimeSlotsByClinicId(cb);
			// then we extract the ServiceBeans and place them into associatedServices
			for (int i = 0; i < clinicTimeSlotPairs.size(); i++) {
				ClinicTimeSlotBean ctsb = clinicTimeSlotPairs.get(i);				
				associatedTimeSlots.add(ctsb.getTimeSlot());
			}	
			// then we convert associatedTimeSlot and  fullTimeSlotList to sets.
			Set<TimeSlotBean> all = new HashSet<>();
			Set<TimeSlotBean> associated = new HashSet<>();

			all.addAll(fullTimeSlotList);
			associated.addAll(associatedTimeSlots);

			// then we determine availableTimeSlots by getting the relative complement
			// of all and associated
			// i.e the items that are in fullServiceList that are not in associatedServices
			all.removeAll(associated);

			// then we load them into the availableTimeSlots ArrayList to conform to the
			// interface we already have.
			Iterator<TimeSlotBean> tsbIterator = all.iterator();
			while (tsbIterator.hasNext()) {
				TimeSlotBean tsb = tsbIterator.next();
				availableTimeSlots.add(tsb);
			}
			System.out.println("final: " + availableTimeSlots);
			
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
//		System.out.println("clinicBean: " + clinicBeanb);
//		System.out.println("timeSlotList: " + fullTimeSlotList);
//		System.out.println("availableTimeSlotList: " + availableTimeSlots);

		request.setAttribute("clinicTimeSlotPairs", clinicTimeSlotPairs);
		request.setAttribute("clinicBean", cb);
		request.setAttribute("timeSlotList", associatedTimeSlots);
		request.setAttribute("availableTimeSlotList", availableTimeSlots);
		
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicTimeSlots/listTimeSlots.jsp");
		rd.forward(request, response);
	}

	private void addClinicTimeSlot(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicId = request.getParameter("clinicId");
		String timeSlotId = request.getParameter("timeSlotId");

		if (timeSlotId.equals("empty")) {
			String targetPath = String.format(
					"%s/clinicTimeSlotBeanServlet?action=listClinicTimeSlots&clinicId=%s", 
					request.getContextPath(), 
					clinicId
			);
			response.sendRedirect(targetPath);
			return;
		}
		
		ClinicBean cb;
		TimeSlotBean tsb;
		try {
			cb = cd.findClinicById(Long.parseLong(clinicId));
			tsb = tsd.findTimeSlotById(Long.parseLong(timeSlotId));
			
			if (cb == null) {
				throw new ServletException("Clinic with id " + clinicId + " not found!");
			}
			if (tsb == null) {
				throw new ServletException("Time Slot with id " + timeSlotId + " not found!");
			}

			ClinicTimeSlotBean ctsb = ctsd.createClinicTimeSlot(cb, tsb);
			if (ctsb == null) {
				String error = String.format(
						"Could not map Service %s to Clinic %s", 
						tsb.getId(), 
						cb.getId()
				);
				throw new ServletException(error);
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		String targetPath = String.format(
				"%s/clinicTimeSlotBeanServlet"
						+ "?action=addSuccess"
						+ "&timeSlotId=%s"
						+ "&timeSlotStart=%s"
						+ "&timeSlotEnd=%s"
						+ "&timeSlotCapacity=%s"
						+ "&clinicId=%s"
						+ "&clinicName=%s",
				request.getContextPath(),
				tsb.getId(),
				tsb.getStart(),
				tsb.getEnd(),
				tsb.getCapacity(),
				cb.getId(),
				cb.getName()
		);
		response.sendRedirect(targetPath);
	}

	private void addSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicTimeSlots/addSuccess.jsp");
		rd.forward(request, response);
	}

	private void deleteConfirm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicTimeSlotId = request.getParameter("clinicTimeSlotId");
		
		ClinicBean cb;
		TimeSlotBean tsb;
		ClinicTimeSlotBean ctsb;
		try {
			ctsb = ctsd.findClinicTimeSlotById(Integer.parseInt(clinicTimeSlotId));
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		System.out.println("ctsb: " + ctsb);
		
		request.setAttribute("clinicTimeSlotBean", ctsb);
		request.setAttribute("clinicBean", ctsb.getClinic());
		request.setAttribute("timeSlotBean", ctsb.getTimeSlot());

		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicTimeSlots/deleteConfirm.jsp");
		rd.forward(request, response);
	}

	private void deleteSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String clinicTimeSlotId = request.getParameter("clinicTimeSlotId");
		
		ClinicTimeSlotBean ctsb;
		try {
			ctsb = ctsd.findClinicTimeSlotById(Integer.parseInt(clinicTimeSlotId));
			if (ctsb == null) {
				throw new ServletException("Couldn't find CSB with id: " + clinicTimeSlotId);
			}

			boolean res = ctsd.deleteClinicTimeSlot(ctsb);
			if (!res) {
				String error = String.format("Clinic Time Slot with id %s could not be deleted!", clinicTimeSlotId);
				throw new ServletException(error);
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		String targetPath = String.format("%s/clinicTimeSlotBeanServlet"
				+ "?action=deleteSuccess"
				+ "&timeSlotId=%s"
				+ "&timeSlotStart=%s"
				+ "&timeSlotEnd=%s"
				+ "&timeSlotCapacity=%s"
				+ "&clinicId=%s"
				+ "&clinicName=%s",
				request.getContextPath(),
				ctsb.getTimeSlot().getId(),
				ctsb.getTimeSlot().getStart(),
				ctsb.getTimeSlot().getEnd(),
				ctsb.getTimeSlot().getCapacity(),
				ctsb.getClinic().getId(),
				ctsb.getClinic().getName()
		);
		response.sendRedirect(targetPath);
	}
	
	private void deleteSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/clinicTimeSlots/deleteSuccess.jsp");
		rd.forward(request, response);
	}
}
