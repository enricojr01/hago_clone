<%-- 
    Document   : addSuccess.jsp
    Created on : Apr 20, 2026, 2:58:56 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Account Created</title>
    </head>
    <body>
        <h1>Employee Account Successfully Created!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Role: <%= request.getParameter("role") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Email: <%= request.getParameter("email") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/employeeBeanServlet?action=list" %>">Return to Employee Master List</a>
    </body>
</html>
