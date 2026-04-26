<%-- 
    Document   : addSuccess.jsp
    Created on : Apr 16, 2026, 4:43:25 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clinic Successfully added!</title>
    </head>
	<jsp:useBean id="clinicBean" scope="request" class="com.clone.hago_clone.models.ClinicBean"/>
    <body>
		<h1>Clinic successfully added!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Name: <%= request.getParameter("name") %></li>
			<li>Address <%= request.getParameter("address") %></li>
		</ul>
			<a href="<%= request.getContextPath() + "/clinicBeanServlet?action=list"%>">Back to List</a>
    </body>
</html>
