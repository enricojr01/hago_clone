<%-- 
    Document   : appointmentList
    Created on : Apr 23, 2026, 5:53:07 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList, com.clone.hago_clone.models.AppointmentBean" %>
<jsp:useBean id="employeeBean" scope="session" class="com.clone.hago_clone.models.EmployeeBean"/>
<jsp:useBean id="appointmentList" scope="request" class="java.util.ArrayList<AppointmentBean>"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Appointment List</title>
    </head>
    <body>
        <h1>Appointment List for <%= employeeBean.getClinic().getName() %></h1>
		<a href="<%= request.getContextPath() + "/appointmentBeanServlet?action=addDisplay&clinicId=" + employeeBean.getClinicId() %>">Create a new Appointment for this Clinic</a>
		<table>
			<tr>
				<th>Id</th>
				<th>Time</th>
				<th>Patient Name</th>
				<th>Clinic Name</th>
				<th>Service Name</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		<% 
			for (int i = 0; i < appointmentList.size(); i++) {
				AppointmentBean ab = appointmentList.get(i);
				out.println("<tr>");
				out.println("<td>" + ab.getId() + "</td>");
				out.println("<td>" + ab.getDate() + "</td>");
				out.println("<td>" + ab.getPatient().getName() + "</td>");
				out.println("<td>" + ab.getClinic().getName() + "</td>");
				out.println("<td>" + ab.getService().getName() + "</td>");
				out.println("</tr>");
			}
		%>
		</table>
		<a href="<%= request.getContextPath() + "/employeeLogin" %>">Back to Dashboard</a>
    </body>
</html>
