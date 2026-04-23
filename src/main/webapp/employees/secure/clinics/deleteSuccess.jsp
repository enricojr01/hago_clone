<%-- 
    Document   : deleteSuccess
    Created on : Apr 19, 2026, 10:57:21 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clinic Successfully Deleted!</title>
    </head>
    <body>
        <h1>Clinic successfully deleted!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Address: <%= request.getParameter("address") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/clinicBeanServlet?action=list" %>">Back to Clinic List</a>
    </body>
</html>
