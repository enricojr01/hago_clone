<%-- 
    Document   : deleteSuccess
    Created on : Apr 20, 2026, 1:53:20 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Successfully Deleted</title>
    </head>
    <body>
        <h1>Patient account successfully deleted!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Email: <%= request.getParameter("address") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/clinicBeanServlet?action=list" %>">Back to Patient List</a>
    </body>
    </body>
</html>
