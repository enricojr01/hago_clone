/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.TimeSlotDAO;
import com.clone.hago_clone.models.TimeSlotBean;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
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
@WebServlet(name = "TimeSlotBeanServlet", urlPatterns = {"/timeSlotBeanServlet"})
public class TimeSlotBeanServlet extends HttpServlet {
	private TimeSlotDAO tsd;

	@Override
	public void init() {
		ConnectionDetails cdeet = DBConnections.prod();
		try {
			tsd = new TimeSlotDAO(
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
		ArrayList<TimeSlotBean> results = new ArrayList<>();
		
		try {
			results = tsd.findAllTimeSlots();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("timeSlotList", results);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/timeSlots/list.jsp");
		rd.forward(request, response);
	}

	private void addDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ArrayList<TimeSlotBean> clinicList = new ArrayList<>();
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/timeSlots/add.jsp");
		rd.forward(request, response);
	}

	private void addSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String timeSlotStart = request.getParameter("timeSlotStart");
		String timeSlotEnd = request.getParameter("timeSlotEnd");
		String timeSlotCapacity = request.getParameter("timeSlotCapacity");
		String error = "";
		
		LocalTime start = LocalTime.parse(timeSlotStart);
		LocalTime end = LocalTime.parse(timeSlotEnd);
		int capacity = Integer.parseInt(timeSlotCapacity);

		if (StringUtils.isNullOrEmpty(timeSlotStart)) {
			error = error + "Starting time can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(timeSlotEnd)) {
			error = error + "Ending time can't be empty! ";
		}
		
		if (StringUtils.isNullOrEmpty(timeSlotCapacity)) {
			error = error + "Capacity can't be empty! ";
		}

		if (start.isAfter(end)) {
			error = error + "Start time can't be AFTER end time! ";
		}

		if (end.isBefore(start)) {
			error = error + "End time can't be BEFORE start time! ";
		}

		if (capacity <= 0) {
			error = error + "Capacity must be greater than 0! ";
		}

		if (error.isEmpty() == false) {
			request.setAttribute("error", error);
			RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/timeSlots/add.jsp");
			rd.forward(request, response);
		} else {
			
			try {
				TimeSlotBean tsb = tsd.createTimeSlot(
						start, 
						end, 
						capacity	
				);
				String targetPath = String.format(
						"timeSlotBeanServlet?action=addSuccess&id=%s&start=%s&end=%s&capacity=%s", 
						tsb.getId(),
						tsb.getStart(), 
						tsb.getEnd(), 
						tsb.getCapacity()
				);
				response.sendRedirect(targetPath);
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}
		}
	}

	private void addSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/timeSlots/addSuccess.jsp")	;
		rd.forward(request, response);
	}

	private void editDisplay(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("id"));
		
		TimeSlotBean tb;
		try {
			tb = tsd.findTimeSlotById(id);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		request.setAttribute("timeSlotBean", tb);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/timeSlots/edit.jsp");
		rd.forward(request, response);
	}

	private void editSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Long timeSlotId = Long.valueOf(request.getParameter("timeSlotId"));
		String timeSlotStart = request.getParameter("timeSlotStart");
		String timeSlotEnd = request.getParameter("timeSlotEnd");
		String timeSlotCapacity = request.getParameter("timeSlotCapacity");
		String error = "";

		if (StringUtils.isNullOrEmpty(timeSlotStart)) {
			error = error + "Starting time can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(timeSlotEnd)) {
			error = error + "Ending time can't be empty! ";
		}
		
		if (StringUtils.isNullOrEmpty(timeSlotCapacity)) {
			error = error + "Capacity can't be empty! ";
		}

		TimeSlotBean tsb;
		if (error.isEmpty() == false) {
			try {
				tsb = tsd.findTimeSlotById(timeSlotId);
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}
			
			request.setAttribute("error", error);
			request.setAttribute("timeSlotBean", tsb);
			RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/timeSlots/edit.jsp");
			rd.forward(request, response);
		} else {
			try {
				tsb = tsd.findTimeSlotById(timeSlotId);

				tsb.setStart(LocalTime.parse(timeSlotStart));
				tsb.setEnd(LocalTime.parse(timeSlotEnd));
				tsb.setCapacity(Integer.parseInt(timeSlotCapacity));

				tsd.updateTimeSlot(tsb);

				String targetPath = String.format(
						"%s/timeSlotBeanServlet?action=editSuccess&id=%s&start=%s&end=%scapacity=%s",
						request.getContextPath(),
						tsb.getId(),
						tsb.getStart(),
						tsb.getEnd(),
						tsb.getCapacity()
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
		long timeSlotId = Long.parseLong(request.getParameter("id"));

		TimeSlotBean tsb;
		try {
			tsb = tsd.findTimeSlotById(timeSlotId);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		request.setAttribute("timeSlotBean", tsb);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/timeSlots/deleteConfirm.jsp");
		rd.forward(request, response);
	}

	private void deleteSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		long timeSlotId = Long.parseLong(request.getParameter("id"));

		TimeSlotBean tsb;
		try {
			tsb = tsd.findTimeSlotById(timeSlotId);

			int res = tsd.deleteTimeSlot(tsb);
			if (res == 0) {
				String error = String.format("Could not delete Time Slot with id %s", timeSlotId);
				throw new ServletException(error);
			} else {
				String targetPath = String.format(
						"%s/employeeBeanServlet?action=deleteSuccess&id=%s&start=%s&end=%s&capacity=%s",
						request.getContextPath(),
						tsb.getId(),
						tsb.getStart(),
						tsb.getEnd(),
						tsb.getCapacity()
				);
				response.sendRedirect(targetPath);
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
	}

	private void deleteSuccess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/timeSlots/deleteSuccess.jsp");
		rd.forward(request, response);
	}
}
