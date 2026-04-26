<%-- 
    Document   : add
    Created on : Apr 20, 2026, 2:58:47 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.ClinicBean, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Employee Account</title>
    </head>
    <body>
        <h1>Add New Employee Account</h1>
		<jsp:useBean id="clinicList" scope="request" class="ArrayList<ClinicBean>"/>
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
				<input type="hidden" name="action" value="addSave"/>
				<label>Name: </label>
				<input type="text" name="employeeName"/>
				<br />
				<label>Email: </label>
				<input type="email" name="employeeEmail"/>
				<br />
				<label>Role: </label>
				<input type="text" name="employeeRole"/>
				<br />
				<label>Clinic: </label>
				<select name="employeeClinic">
					<% 
						for (int i = 0; i < clinicList.size(); i++) {
							ClinicBean cb = clinicList.get(i);
							String optionTag = String.format("<option value='%s'>%s</option>", cb.getId(), cb.getName());
							out.println(optionTag);
						}
					%>
				</select>
				<br />
				<label>Password: </label>
				<input type="password" name="employeePassword"/>
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/employeeBeanServlet?action=list" %>">Return to Employee Master List </a>
    </body>
</html>
