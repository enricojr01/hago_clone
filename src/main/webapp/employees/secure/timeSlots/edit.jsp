<%-- 
    Document   : edit
    Created on : Apr 17, 2026, 2:30:14 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="timeSlotBean" scope="request" class="com.clone.hago_clone.models.TimeSlotBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Time Slot</title>
    </head>
    <body>
		<form action="<%= request.getContextPath() + "/timeSlotBeanServlet" %>">
			<input type="hidden" name="action" value="editSave"/>
			<input type="hidden" name="timeSlotId" value="<%= timeSlotBean.getId() %>"/>
			<label>Start:</label>
			<input type="time" name="timeSlotStart" value= "<%= timeSlotBean.getStart() %>"/>
			<br />
			<label>End:</label>
			<input type="time" name="timeSlotEnd" value="<%= timeSlotBean.getEnd() %>"/>
			<br />
			<label>Capacity:</label>
			<input type="number" name="timeSlotCapacity" value="<%= timeSlotBean.getCapacity() %>"/>
			<br />
			<input type="submit" value="Submit"/>
			<input type="reset" value="Reset"/>
		</form>
    </body>
</html>
