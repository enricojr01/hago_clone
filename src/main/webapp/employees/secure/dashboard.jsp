<%-- 
    Document   : dashboard
    Created on : Apr 5, 2026, 7:03:04 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page import="com.clone.hago_clone.models.EmployeeBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="employeeBean" scope="session" class="com.clone.hago_clone.models.EmployeeBean"/>
<!-- comment --><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HAGO Clone Employee Dashboard</title>
    </head>
    <body>
		<% 
			String adminEmployeePath = request.getContextPath() + "/employeeBeanServlet?action=list";
			String adminPatientsPath = request.getContextPath() + "/patientBeanServlet?action=list";
			String adminClinicPath = request.getContextPath() + "/clinicBeanServlet?action=list";
			String adminServicePath = request.getContextPath() + "/serviceBeanServlet?action=list";
			String adminClinicServicePath = request.getContextPath() + "/clinicServiceBeanServlet?action=list";

			String adminEmployeeLink = "<a href='" + adminEmployeePath + "'>Manage Employee Accounts</a>";
			String adminPatientsLink = "<a href='" + adminPatientsPath + "'>Manage Patient Accounts</a>";
			String adminClinicLink = "<a href='" + adminClinicPath + "'>Manage Clinics</a>";
			String adminServiceLink = "<a href='" + adminServicePath + "'>Manage Services</a>";
			String adminClinicServiceLink = "<a href='" + adminClinicServicePath + "'>Manage Clinic/Service Mapping</a>";

			String staffAppointmentsPath = request.getContextPath() + "/appointmentBeanServlet?action=list";
			String staffProfilePath = request.getContextPath() + "/staffProfileServlet?action=list&id=" + employeeBean.getId();
			String staffAppointmentsLink = "<a href='" +  staffAppointmentsPath + "'>Manage Appointments for this Clinic</a>";
			String staffProfileLink = "<a href='" + staffProfilePath + "'>Staff Profile Page</a>";

			String logoutLink = "<a href='" + request.getContextPath() + "/employeeLogin?action=logout" +"'>Logout</a>";
			
			out.println("<h1>Currently logged in as: </h1>");
			out.println("<ul>");
			out.println("<li>" + employeeBean.getName()  + "</li>");
			out.println("<li>" + employeeBean.getRole()  + "</li>");
			out.println("<li>" + employeeBean.getClinic().getName()  + "</li>");
			out.println("</ul>");

			if (employeeBean.getRole().equals("superadmin")) {
				out.println("<h1>Admin Controls</h1>");
				out.println("<ul>");
				out.println("<li>" + adminEmployeeLink + "</li>");
				out.println("<li>" + adminPatientsLink + "</li>");
				out.println("<li>" + adminClinicLink + "</li>");
				out.println("<li>" + adminServiceLink + "</li>");
				out.println("</ul>");
				out.println("<ul>");
				out.println("<li>" + adminClinicServiceLink + "</li>");
				out.println("</ul>");
				out.println("<ul>");
				out.println("<li>" + logoutLink + "</li>");
				out.println("</ul>");
			} else {
				out.println("<h1>Employee Controls</h1>");
				out.println("<ul>");
				out.println("<li>" + staffProfileLink + "</li>");
				out.println("<li>" + staffAppointmentsLink + "</li>");
				out.println("</ul>");
				out.println("<li>" + logoutLink + "</li>");

			}
		%>
    </body>
</html>
