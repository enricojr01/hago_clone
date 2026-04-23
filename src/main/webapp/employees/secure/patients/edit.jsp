<%-- 
    Document   : edit
    Created on : Apr 20, 2026, 11:42:45 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Patient Account</title>
    </head>
    <body>
        <h1>Edit Patient Account</h1>
		<jsp:useBean id="patientBean" scope="request" class="com.clone.hago_clone.models.PatientBean" />
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
				<input type="hidden" name="action" value="editSave"/>
				<input type="hidden" name="patientID" value="<%= patientBean.getId() %>"
				<label>Name: </label>
				<input type="text" name="patientName" value="<%= patientBean.getName() %>"/>
				<br />
				<label>Email: </label>
				<input type="email" name="patientEmail" value="<%= patientBean.getEmail() %>"/>
				<br />
				<!--<label>Password: </label>-->
				<!--<input type="password" name="patientPassword"/>-->
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/patientBeanServlet?action=list" %>">Return to Patient Account List</a>
    </body>
</html>
