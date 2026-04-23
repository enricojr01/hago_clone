<%-- 
    Document   : deleteSuccess
    Created on : Apr 21, 2026, 2:31:52 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully Deleted Service</title>
    </head>
    <body>
		<h1>Service Deleted Successfully!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Description: <%= request.getParameter("description") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/serviceBeanServlet?action=list" %>">Return to Service Master List</a>
    </body>
</html>
