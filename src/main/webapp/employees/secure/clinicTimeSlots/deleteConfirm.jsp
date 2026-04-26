<%-- 
    Document   : deleteConfirm.jsp
    Created on : Apr 21, 2026, 5:48:39 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="clinicTimeSlotBean" scope="request" class="com.clone.hago_clone.models.ClinicTimeSlotBean"/>
<jsp:useBean id="clinicBean" scope="request" class="com.clone.hago_clone.models.ClinicBean"/>
<jsp:useBean id="timeSlotBean" scope="request" class="com.clone.hago_clone.models.TimeSlotBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Removal of Time Slot from Clinic</title>
    </head>
    <body>
        <h1>Confirm that you are going to remove the following time slot: </h1>
		<ul>
			<li>Id: <%= timeSlotBean.getId() %></li>
			<li>Start: <%= timeSlotBean.getStart() %></li>
			<li>End: <%= timeSlotBean.getEnd() %></li>
			<li>Capacity: <%= timeSlotBean.getCapacity() %></li>
		</ul>
		<h1>From the following clinic: </h1>
		<ul>
			<li>Id: <%= timeSlotBean.getId() %></li>	
			<li>Name: <%= clinicBean.getName() %></li>
		</ul>
		<br />
		<a href="<%= request.getContextPath() 
				+ "/clinicTimeSlotBeanServlet?action=listClinicTimeSlots&clinicId=" 
				+ clinicBean.getId() 
		%>">No, I want to go back.</a><br/>
		<a href="<%= request.getContextPath() 
				+ "/clinicTimeSlotBeanServlet?action=deleteSave&clinicTimeSlotId=" 
				+ clinicTimeSlotBean.getId()
		%>">Yes, I would like to remove this Time Slot.</a>
    </body>
</html>
