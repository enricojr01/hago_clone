<%-- 
    Document   : edit
    Created on : Apr 20, 2026, 4:17:07 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.ClinicBean, java.util.ArrayList" %>
<jsp:useBean id="employeeBean" scope="request" class="com.clone.hago_clone.models.EmployeeBean" />
<jsp:useBean id="clinicList" scope="request" class="ArrayList<ClinicBean>"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Employee Account</title>
    </head>
    <body>
        <h1>Edit Employee Account</h1>
		<form action="<%= request.getContextPath() + "/employeeBeanServlet" %>" method="GET">
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
				<input type="hidden" name="action" value="editSave"/>
				<input type="hidden" name="employeeID" value="<%= employeeBean.getId() %>"
				<label>Name: </label>
				<input type="text" name="employeeName" value="<%= employeeBean.getName() %>"/>
				<br />
				<label>Email: </label>
				<input type="email" name="employeeEmail" value="<%= employeeBean.getEmail() %>"/>
				<br />
				<label>Role: </label>
				<input type="text" name="employeeRole" value="<%= employeeBean.getRole() %>"/>
				<br />
				<label>Clinic: </label>
				<select name="employeeClinic">
					<%
						for (int i = 0; i < clinicList.size(); i++) {
							ClinicBean cb = clinicList.get(i);
							long clinicId = employeeBean.getClinic().getId();
							if (cb.getId() == clinicId) {
								String optionTag = String.format("<option value='%s' selected>%s</option>", cb.getId(), cb.getName());
								out.println(optionTag);
							} else {
								String optionTag = String.format("<option value='%s'>%s</option>", cb.getId(), cb.getName());
								out.println(optionTag);
							}
						}
					%>	
				</select>
				<!--<label>Password: </label>-->
				<!--<input type="password" name="patientPassword"/>-->
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/employeeBeanServlet?action=list" %>">Return to Employee Master List</a>
		
    </body>
</html>
