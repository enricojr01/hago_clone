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
        <title>Time Slot Successfully Deleted!</title>
    </head>
    <body>
        <h1>Time Slot successfully deleted!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Start: <%= request.getParameter("start") %></li>
			<li>End: <%= request.getParameter("end") %></li>
			<li>Capacity: <%= request.getParameter("capacity") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/timeSlotBeanServlet?action=list" %>">Back to Time Slot List</a>
    </body>
</html>
