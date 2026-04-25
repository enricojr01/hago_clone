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
        <title>Time Slot Successfully added!</title>
    </head>
    <body>
		<h1>Time Slot successfully added!</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Start: <%= request.getParameter("start") %></li>
			<li>End: <%= request.getParameter("end") %></li>
			<li>Capacity: <%= request.getParameter("capacity") %></li>
		</ul>
			<a href="<%= request.getContextPath() + "/timeSlotBeanServlet?action=list"%>">Back to List</a>
    </body>
</html>
