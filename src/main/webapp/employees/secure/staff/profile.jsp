<%-- 
    Document   : profile
    Created on : Apr 23, 2026, 4:16:08 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="employeeBean" scope="session" class="com.clone.hago_clone.models.EmployeeBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Profile Page</title>
    </head>
    <body>
        <h1>Your details:</h1>
		<form action="<%= request.getContextPath() + "/staffProfileServlet" %>" method="GET">
			<input type="hidden" name="action" value="update"/>
			<input type="hidden" name="employeeId" value="<%= employeeBean.getId() %>"/>
			<fieldset>
				<label>Name: </label>
				<input type="text" id="name" name="employeeName" value="<%= employeeBean.getName() %>"/>
				<br/>
				<label>Email: </label>
				<input type="text" id="name" name="employeeEmail" value="<%= employeeBean.getEmail() %>"/>
				<br/>
				<label>Role: </label>
				<input type="text" id="name" value="<%= employeeBean.getRole()%>" disabled/>
				<br/>
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/employeeLogin" %>">Back to Dashboard</a>
    </body>
</html>
