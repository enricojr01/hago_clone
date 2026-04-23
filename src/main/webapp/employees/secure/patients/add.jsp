<%-- 
    Document   : add
    Created on : Apr 19, 2026, 1:39:17 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Patient Account</title>
    </head>
    <body>
        <h1>Add New Patient Account</h1>
		<form action="<%= request.getContextPath() + "/patientBeanServlet" %>" method="GET">
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
				<input type="text" name="patientName"/>
				<br />
				<label>Email: </label>
				<input type="email" name="patientEmail"/>
				<br />
				<label>Password: </label>
				<input type="password" name="patientPassword"/>
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/patientBeanServlet?action=list" %>">Return to Patient Account List</a>
    </body>
</html>
