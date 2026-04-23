<%-- 
    Document   : addSuccess
    Created on : Apr 21, 2026, 11:54:34 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully created Service!</title>
    </head>
    <body>
		<h1>New Service Created:</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Description: <%= request.getParameter("description") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/serviceBeanServlet?action=list"%>">Return to Service Master List </a>
    </body>
</html>
