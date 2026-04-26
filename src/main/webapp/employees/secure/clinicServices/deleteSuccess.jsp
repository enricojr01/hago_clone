<%-- 
    Document   : deleteSuccess
    Created on : Apr 21, 2026, 4:28:43 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully removed Service</title>
    </head>
    <body>
        <h1>Removed the following Service:</h1>
		<ul>
			<li>Id: <%= request.getParameter("serviceId") %></li>
			<li>Name: <%= request.getParameter("serviceName") %></li>
		</ul>
		<h1>..from the following Clinic:</h1>
		<ul>
			<li>Id: <%= request.getParameter("clinicId") %></li>
			<li>Name: <%= request.getParameter("clinicName") %></li>
		</ul>
		<a href="<%= 
			request.getContextPath() 
					+ "/clinicServiceBeanServlet?action=listServices&clinicId=" 
					+ request.getParameter("clinicId") 
		%>">Return to List of Services for <%= request.getParameter("clinicName") %></a>
    </body>
</html>
