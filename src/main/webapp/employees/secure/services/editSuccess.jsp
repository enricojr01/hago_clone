<%-- 
    Document   : editSuccess
    Created on : Apr 21, 2026, 12:07:16 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully edited Service</title>
    </head>
    <body>
        <h1>Service Edit Succesful!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Description: <%= request.getParameter("description") %></li>
    </body>
	<a href="<%= request.getContextPath() + "/serviceBeanServlet?action=list" %>">Return to Service Master List</a>
</html>
