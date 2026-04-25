<%-- 
    Document   : add.jsp
    Created on : Apr 16, 2026, 2:40:29 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add a Time Slot</title>
    </head>
    <body>
		<form action="<%= request.getContextPath() + "/timeSlotBeanServlet"%>">
		<%
			Object error = request.getAttribute("error");
			if (error != null) {
				String message = error.toString();
				out.println("<fieldset>");
				out.println(error);
				out.println("</fieldset>");
			}
		%>
			<fieldset>
				<input type="hidden" name="action" value="addSave"/>
				<legend>Time Slot Details</legend>
				<label>Start Time:</label>
				<input type="time" id="name" name="timeSlotStart"/> 
				<br/>
				<label>End Time:</label>
				<input type="time" id="address" name="timeSlotEnd"/> 
				<br/>
				<label>Capacity:</label>
				<input type="number" id="address" name="timeSlotCapacity"/> 
				<br/>
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/timeSlotBeanServlet?action=list" %>">Return to Time Slot List</a>
    </body>
</html>
