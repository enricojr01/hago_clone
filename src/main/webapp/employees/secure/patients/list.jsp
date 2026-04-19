<%-- 
    Document   : list
    Created on : Apr 19, 2026, 12:28:39 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.PatientBean, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patients Master List</title>
    </head>
    <body>
		<a href="patientBeanServlet?action=addDisplay">Add new Patient</a>
		<jsp:useBean id="patientList" scope="request" class="java.util.ArrayList<PatientBean>"/>
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Email</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>	
		<%
			for (int i = 0; i < patientList.size(); i++) {
				PatientBean pb = patientList.get(i);
				
				String cbControllerPath = request.getContextPath() + "/patientBeanServlet";
				String editPath = String.format("%s?action=editDisplay&id=%s", cbControllerPath, pb.getId());
				String editLink = String.format("<a href='%s'>Edit</a>", editPath);
				String deletePath = String.format("%s?action=deleteConfirm&id=%s", cbControllerPath, pb.getId());
				String deleteLink = String.format("<a href='%s'>Delete</a>", deletePath);
				
				out.println("<tr>");
				out.println("<td>" + pb.getId() + "</td>");
				out.println("<td>" + pb.getName() + "</td>");
				out.println("<td>" + pb.getEmail() + "</td>");
				out.println("<td>" + editLink + "</td>");
				out.println("<td>" + deleteLink + "</td>");
				out.println("</tr>");
			}
		%>
		</table>
		<a href="<%= request.getContextPath() + "/employeeLogin" %>">Return to Dashboard</a>
    </body>
</html>
