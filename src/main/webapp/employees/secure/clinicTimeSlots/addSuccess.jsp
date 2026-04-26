<%-- 
    Document   : addSuccess
    Created on : Apr 21, 2026, 4:09:40 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully added Time Slot to Clinic</title>
    </head>
    <body>
        <h1>Successfully added the following Time Slot:</h1>
		<ul>
			<li>Id: <%= request.getParameter("timeSlotId") %></li>
			<li>Time Slot Start: <%= request.getParameter("timeSlotStart") %></li>
			<li>Time Slot End: <%= request.getParameter("timeSlotEnd") %></li>
			<li>Capacity: <%= request.getParameter("timeSlotCapacity") %></li>
		</ul>
		<h1>...to the following Clinic:</h1>
		<ul>
			<li>Id: <%= request.getParameter("clinicId") %></li>
			<li>Name: <%= request.getParameter("clinicName") %></li>
		</ul>
			<a href="<%= 
				request.getContextPath() 
						+ "/clinicTimeSlotBeanServlet?action=listTimeSlots&clinicId=" 
						+ request.getParameter("clinicId") 
			%>">Return to List of Services for <%= request.getParameter("clinicName") %></a>
    </body>
</html>
