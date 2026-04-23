/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.AppointmentDAO;
import com.clone.hago_clone.db.ClinicServiceDAO;
import com.clone.hago_clone.db.PatientDAO;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicServiceBean;
import com.clone.hago_clone.models.EmployeeBean;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.ServiceBean;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
@WebServlet(name = "AppointmentBeanServlet", urlPatterns = {"/appointmentBeanServlet"})
public class AppointmentBeanServlet extends HttpServlet {
	private AppointmentDAO ad;
	private PatientDAO pd;
	private ClinicServiceDAO csd;

	@Override
	public void init() {
		ConnectionDetails cdt = DBConnections.prod();
		try {
			this.ad = new AppointmentDAO(
				cdt.getUrl(),
			cdt.getUsername(),
			cdt.getPassword()
			);
			this.pd = new PatientDAO(
				cdt.getUrl(),
			cdt.getUsername(),
			cdt.getPassword()
			);
			this.csd = new ClinicServiceDAO(
					cdt.getUrl(),
					cdt.getUsername(),
					cdt.getPassword()
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
				displayAppointments(request, response);
				break;
			case "addDisplay":
				addDisplay(request, response);
				break;
			case "addSave":
				addSave(request, response);
				break;
			default:
				throw new ServletException("Unknown action: %s" + action);
		}
	}

	private void displayAppointments(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Object oeb = session.getAttribute("employeeBean");
		if (oeb == null) {
			throw new ServletException("No EmployeeBean set! Log in again!");
		}
		EmployeeBean eb = (EmployeeBean) oeb;
		ClinicBean cb = eb.getClinic();

		ArrayList<AppointmentBean> results;
		try {
			results = ad.findAppointmentsByClinic(cb);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		} 

		request.setAttribute("appointmentList", results);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/staff/appointmentList.jsp");
		rd.forward(request, response);
	}

	private void addDisplay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Object oeb = session.getAttribute("employeeBean");
		if (oeb == null) {
			throw new ServletException("No EmployeeBean set! Log in again!");
		}
		EmployeeBean eb = (EmployeeBean) oeb;
		ClinicBean cb = eb.getClinic();
		
		ArrayList<PatientBean> patients = new ArrayList<>();
		ArrayList<ClinicServiceBean> clinicServices = new ArrayList<>();
		ArrayList<ServiceBean> services = new ArrayList<>();
		try {
			patients = pd.getAllPatients();
			clinicServices = csd.findClinicServiceByClinicId(cb.getId());
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}

		for (ClinicServiceBean csb : clinicServices) {
			services.add(csb.getService());
		}	

		// weird time crap because the datetime input needs a min value
		LocalDate date = LocalDate.now().plusDays(1);
		LocalTime time = LocalTime.of(0, 0);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		DateTimeFormatter htmlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		String minValue = dateTime.format(htmlFormat);

		// another one for the max value
		LocalDate maxDate = LocalDate.now().plusYears(5);
		LocalDateTime maxDateTime = LocalDateTime.of(maxDate, time);
		String maxValue = maxDateTime.format(htmlFormat);

		request.setAttribute("patientList", patients);
		request.setAttribute("serviceList", services);
		request.setAttribute("minValue", minValue);
		request.setAttribute("maxValue", maxValue);
		RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/staff/appointmentAdd.jsp");
		rd.forward(request, response);
	}


	private void addSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String appointmentTime = request.getParameter("appointmentTime");
		String patientId = request.getParameter("patientId");
		String clinicId = request.getParameter("clinicId");
		String serviceId = request.getParameter("serviceId");
		String error = "";

		if (StringUtils.isNullOrEmpty(appointmentTime)) {
			error = error + "Appointment time can't be empty! ";
		}
		
		if (StringUtils.isNullOrEmpty(patientId)) {
			error = error + "Patient can't be empty! ";
		}
		
		if (StringUtils.isNullOrEmpty(clinicId)) {
			error = error + "Clinic can't be empty! ";
		}

		if (StringUtils.isNullOrEmpty(serviceId)) {
			error = error + "Service can't be empty! ";
		}

		if (error.isEmpty() == false) {
			EmployeeBean eb = (EmployeeBean) request.getSession().getAttribute("employeeBean");
			ClinicBean cb = eb.getClinic();
			ArrayList<PatientBean> patients = new ArrayList<>();
			ArrayList<ClinicServiceBean> clinicServices = new ArrayList<>();
			ArrayList<ServiceBean> services = new ArrayList<>();
			
			try {
				patients = pd.getAllPatients();
				clinicServices = csd.findClinicServiceByClinicId(cb.getId());
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}

			for (ClinicServiceBean csb : clinicServices) {
				services.add(csb.getService());
			}
			
			request.setAttribute("error", error);
			request.setAttribute("patientList", patients);
			request.setAttribute("serviceList", services);
			RequestDispatcher rd = request.getRequestDispatcher("/employees/secure/staff/appointmentAdd.jsp");
			rd.forward(request, response);	
		}

		System.out.println("appointmentTime: " + appointmentTime);
	}
}
