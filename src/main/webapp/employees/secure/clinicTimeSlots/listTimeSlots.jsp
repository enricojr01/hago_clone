<%-- 
    Document   : listServices.jsp
    Created on : Apr 21, 2026, 3:30:24 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.ClinicBean, com.clone.hago_clone.models.TimeSlotBean, java.util.ArrayList"%>
<jsp:useBean id="clinicBean" scope="request" class="com.clone.hago_clone.models.ClinicBean"/>
<jsp:useBean id="availableTimeSlotList" scope="request" class="java.util.ArrayList<TimeSlotBean>"/>
<jsp:useBean id="timeSlotList" scope="request" class="java.util.ArrayList<TimeSlotBean>"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Time Slots for <%= clinicBean.getName() %></title>
    </head>
    <body>
        <h1>Time Slots associated with clinic: <%= clinicBean.getName() %></h1>
		<table>
			<tr>
				<th>Id</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Capacity</th>
				<th>Delete</th>
			</tr>
			<% 
				for (int i = 0; i < timeSlotList.size(); i++) {
					TimeSlotBean tsb = timeSlotList.get(i);
					String targetPath = String.format(
						"%s/clinicTimeSlotBeanServlet?action=deleteConfirm&clinicTimeSlotId=%s", 
						request.getContextPath(), 
						clinicBean.getId(), 
						tsb.getId()
					);
					String anchor = String.format(
						"<a href='%s'>Delete</a>", 
						targetPath
					);
					
					out.println("<tr>");
					out.println("<td>" + sb.getId() + "</td>");
					out.println("<td>" + sb.getStart() + "</td>");
					out.println("<td>" + sb.getEnd() + "</td>");
					out.println("<td>" + sb.getCapacity() + "</td>");
					out.println("<td>" + anchor + "</td>");
					out.println("</tr>");
				}
			%>
		</table>

		<h1>Add a Time Slot to this Clinic</h1>
		<form action="<%= request.getContextPath() + "/clinicTimeSlotBeanServlet" %>" method="GET">
			<input type="hidden" name="action" value="addTimeSlot"/>
			<input type="hidden" name="clinicId" value="<%= clinicBean.getId() %>"/>
			<fieldset>
			</fieldset>
				<legend>Error</legend>
				<%
					if (error) {
						out.println(error);
					}
				%>
			<fieldset>
				<legend>Select a Time Slot</legend>
				<select name="serviceId">
					<% 
						if (availableTimeSlotList.size() == 0) {
							out.println("<option value='empty'>No available Time Slots</option>");
						} else {
							for (int i = 0; i < availableTimeSlotList.size(); i++) {
								TimeSlotBean sb = availableTimeSlotList.get(i);
								String optionText = String.format(
									"%s - %s", 
									sb.getStart(), 
									sb.getEnd()
								);
								String optionElement= String.format(
									"<option value=%s>%s</option>", 
									sb.getId(), 
									optionText
								);
								out.println(optionElement);
							}
						}
					%>
				</select>
			</fieldset>
			<fieldset>
				<legend>Submit/Clear</legend>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/clinicTimeSlotBeanServlet?action=list"%>">Go Back to Clinic List</a>
    </body>
</html>
