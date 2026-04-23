<%-- 
    Document   : delete
    Created on : Apr 20, 2026, 6:41:06 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Delete Employee</title>
    </head>
    <body>
		<jsp:useBean id="employeeBean" scope="request" class="com.clone.hago_clone.models.EmployeeBean"/>
        <h1>Confirm Delete</h1>
		<p>You wish to delete the following Employee account: </p>
		<ul>
			<li>Id: <%= employeeBean.getId() %></li>
			<li>Name: <%= employeeBean.getName() %></li>
			<li>Address: <%= employeeBean.getEmail() %></li>
		</ul>
			<a href="<%= request.getContextPath() + "/employeeBeanServlet?action=list" %>">
				No, I want to go back
			</a><br/> 
			<a href="<%= request.getContextPath() + "/employeeBeanServlet?action=deleteSave&id=" + employeeBean.getId() %>">
				Yes, I want to delete this Employee account
			</a>
    </body>
</html>
