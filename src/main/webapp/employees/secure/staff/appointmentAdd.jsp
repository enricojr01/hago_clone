<%-- 
    Document   : appointmentAdd
    Created on : Apr 23, 2026, 6:06:08 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, com.clone.hago_clone.models.PatientBean, com.clone.hago_clone.models.ServiceBean" %>
<jsp:useBean id="employeeBean" scope="session" class="com.clone.hago_clone.models.EmployeeBean"/>
<jsp:useBean id="patientList" scope="request" class="ArrayList<PatientBean>"/>
<jsp:useBean id="serviceList" scope="request" class="ArrayList<ServiceBean>"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Appointment</title>
    </head>
    <body>
        <h1>Add Appointment at Clinic <%= employeeBean.getClinic().getName() %></h1>
		<form action="<%= request.getContextPath() + "/appointmentBeanServlet" %>" method="GET">
			<% 
				Object error = request.getAttribute("error");
				if (error != null) {
					String message = error.toString();
					out.println("<fieldset>");
					out.println(message);
					out.println("</fieldset>");
				}
			%>
			<fieldset>
				<input type="hidden" name="action" value="addSave"/>
				<label>Time / Date</label>
				<input type="datetime-local" name="appointmentTime" min="<%= request.getAttribute("minValue") %>" max="<%= request.getAttribute("maxValue") %>">
				<br/>
				<label>Patient Name: </label>
				<!--<input type="text" name="patientName"/>-->
				<select name="patientId">
					<% 
						for (int i = 0; i < patientList.size(); i++) {
							PatientBean pb = patientList.get(i);
							out.println("<option value=" + pb.getId() + ">" + pb.getName() + "</option>");
						}
					%>

				</select>
				<br />
				<label>Clinic: </label>
				<input type="text" value="<%= employeeBean.getClinic().getName() %>" disabled/>
				<input type="hidden" name="clinicId" value="<%= employeeBean.getClinic().getId() %>"/>
				<br />
				<label>Service</label>
				<select name="serviceId">
					<%
						for (int i = 0; i < serviceList.size(); i++) {
							ServiceBean sb = serviceList.get(i);
							out.println("<option value=" + sb.getId() + ">" + sb.getName() + "</option>");
							
						}
					%>
				</select>
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/appointmentBeanServlet?action=list" %>">Return to Clinic Appointment Master List</a>
    </body>
	<script></script>
</html>
