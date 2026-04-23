<%-- 
    Document   : editSuccess
    Created on : Apr 23, 2026, 5:02:47 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="employeeBean" scope="session" class="com.clone.hago_clone.models.EmployeeBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully edited Profile</title>
    </head>
    <body>
        <h1>Successfully edited profile:</h1>
		<ul>
			<li>Name: <%= employeeBean.getName() %></li>
			<li>Email: <%= employeeBean.getEmail() %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/employeeLogin" %>">Back to Dashboard</a>
    </body>
</html>
