<%-- 
    Document   : list.jsp
    Created on : Apr 14, 2026, 3:48:27 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.TimeSlotBean, java.util.ArrayList" %>
<jsp:useBean id="timeSlotList" scope="request" class="java.util.ArrayList<TimeSlotBean>"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Time Slot Master List</title>
    </head>
    <body>
		<h1>Time Slot List</h1>
		<a href=<%= request.getContextPath() + "/timeSlotBeanServlet?action=addDisplay" %>>Add New Time Slot</a>
		<table>
			<tr>
				<th>ID</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Capacity</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>	
		<%
			for (int i = 0; i < timeSlotList.size(); i++) {
				TimeSlotBean cb = timeSlotList.get(i);
				
				String cbControllerPath = request.getContextPath() + "/timeSlotBeanServlet";
				String editPath = String.format("%s?action=editDisplay&id=%s", cbControllerPath, cb.getId());
				String editLink = String.format("<a href='%s'>Edit</a>", editPath);
				String deletePath = String.format("%s?action=deleteConfirm&id=%s", cbControllerPath, cb.getId());
				String deleteLink = String.format("<a href='%s'>Delete</a>", deletePath);
				
				out.println("<tr>");
				out.println("<td>" + cb.getId() + "</td>");
				out.println("<td>" + cb.getStart() + "</td>"); 
				out.println("<td>" + cb.getEnd() + "</td>"); 
				out.println("<td>" + cb.getCapacity() + "</td>");
				out.println("<td>" + editLink + "</td>");
				out.println("<td>" + deleteLink + "</td>");
				out.println("</tr>");
			}
		%>
		</table>
		<a href="<%= request.getContextPath() + "/employeeLogin"%>">Return to Dashboard</a>
    </body>
</html>
