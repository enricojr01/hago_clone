<%-- 
    Document   : editSuccess
    Created on : Apr 17, 2026, 3:01:55 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully edited Time Slot</title>
    </head>
    <body>
        <h1>Time Slot Successfully Edited</h1>
		<ul>
			<li>Id: <%= request.getParameter("id") %></li>
			<li>Start: <%= request.getParameter("start") %></li>
			<li>End: <%= request.getParameter("end") %></li>
			<li>Capacity: <%= request.getParameter("capacity") %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/timeSlotBeanServlet?action=list" %>">Back to Time Slot List</a>
    </body>
</html>
