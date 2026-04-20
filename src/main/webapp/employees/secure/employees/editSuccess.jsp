<%-- 
    Document   : editSuccess
    Created on : Apr 20, 2026, 4:17:16 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Account Edit Success</title>
    </head>
    <body>
        <h1>Employee Account Successfully Edited!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Role: <%= request.getParameter("role") %></li>
			<li>Email: <%= request.getParameter("email") %></li>
			<li>Clinic: <%= request.getParameter("clinic") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/employeeBeanServlet?action=list" %>">Return to Employee Account Master List</a>
    </body>
</html>
