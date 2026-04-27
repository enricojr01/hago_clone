<%-- 
    Document   : list.jsp
    Created on : Apr 21, 2026, 3:08:26 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.ClinicBean, java.util.ArrayList"%>
<jsp:useBean id="clinicList" scope="request" class="java.util.ArrayList<ClinicBean>"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clinic-Time Slot Mapping Master List</title>
    </head>
    <body>
        <h1>List of Clinic-Time Slot Mappings</h1>
		<p>Displays a list of clinics and all the time slots they provide. </p>
		<table>
			<tr>
				<th>Id</th>
				<th>Clinic Name</th>
				<th>View Services</th>
			</tr>
			<% 
				for (int i = 0; i < clinicList.size(); i++) {
					ClinicBean cb = clinicList.get(i);
					String targetPath = String.format(
						"/clinicTimeSlotBeanServlet?action=listClinicTimeSlots&clinicId=%s", 
						cb.getId()
					);
					String anchor = String.format(
						"<a href='%s%s'>List Time Slots</a>", 
						request.getContextPath(), 
						targetPath
					);
					out.println("<tr>");
					out.println("<td>" + cb.getId() + "</td>");
					out.println("<td>" + cb.getName() + "</td>");
					out.println("<td>" + anchor + "</td>");
					out.println("</tr>");
				}
			%>
		</table>
		<a href="<%= request.getContextPath() + "/employeeLogin"%>">Back to Dashboard</a>	
    </body>
</html>
