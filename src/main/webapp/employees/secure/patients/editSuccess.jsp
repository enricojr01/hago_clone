<%-- 
    Document   : editSuccess
    Created on : Apr 20, 2026, 11:51:17 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patent Account Edit Success!</title>
    </head>
    <body>
        <h1>Patient Account Successfully Edited!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Email: <%= request.getParameter("email") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/patientBeanServlet?action=list" %>">Return to Patient Account List</a>
    </body>
</html>
