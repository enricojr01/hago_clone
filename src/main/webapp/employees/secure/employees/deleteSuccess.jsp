<%-- 
    Document   : deleteSuccess
    Created on : Apr 20, 2026, 6:41:33 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Account Delete</title>
    </head>
    <body>
        <h1>Employee account deleted!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Email: <%= request.getParameter("email") %></li>
			<li>Role: <%= request.getParameter("role") %></li>
			<li>Clinic: <%= request.getParameter("clinic") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/employeeBeanServlet?action=list" %>">Back to Employee List</a>
    </body>
</html>
