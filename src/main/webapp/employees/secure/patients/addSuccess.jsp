<%-- 
    Document   : addSuccess
    Created on : Apr 19, 2026, 1:46:58 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Account Successfully Created</title>
    </head>
    <body>
        <h1>Patient Account Successfully Created!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Email: <%= request.getParameter("email") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/patientBeanServlet?action=list" %>">Return to Patient Account List</a>
    </body>
</html>
