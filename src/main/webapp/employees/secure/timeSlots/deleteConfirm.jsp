<%-- 
    Document   : deleteConfirm.jsp
    Created on : Apr 19, 2026, 10:36:06 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="timeSlotBean" scope="request" class="com.clone.hago_clone.models.TimeSlotBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete a Time Slot</title>
    </head>
    <body>
        <h1>Confirm Delete</h1>
		<p>You wish to delete the following Clinic: </p>
		<ul>
			<li>Id: <%= timeSlotBean.getId() %></li>
			<li>Name: <%= timeSlotBean.getStart()%></li>
			<li>Address: <%= timeSlotBean.getEnd() %></li>
			<li>Capacity: <%= timeSlotBean.getCapacity() %></li>
		</ul>
			<a href="<%= request.getContextPath() + "/timeSlotBeanServlet?action=list" %>">
				No, I want to go back.
			</a>
			<br />
			<a href="<%= request.getContextPath() + "/timeSlotBeanServlet?action=delete&id=" + timeSlotBean.getId() %>">
				Yes, I want to delete this Clinic.
			</a>
    </body>
</html>
