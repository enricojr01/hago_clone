<%-- 
    Document   : list.jsp
    Created on : Apr 14, 2026, 3:48:27 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.ClinicBean, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clinic Master List</title>
    </head>
    <body>
		<jsp:useBean id="clinicList" scope="request" class="java.util.ArrayList<ClinicBean>"/>
		<a href=<%= request.getContextPath() + "/clinicBeanServlet?action=add" %>>Add New Clinic</a>
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Address</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>	
		<%
			for (int i = 0; i < clinicList.size(); i++) {
				ClinicBean cb = clinicList.get(i);
				
				String cbControllerPath = request.getContextPath() + "/clinicBeanServlet";
				String editPath = String.format("%s?action=editDisplay&id=%s", cbControllerPath, cb.getId());
				String editLink = String.format("<a href='%s'>Edit</a>", editPath);
				String deletePath = String.format("%s?action=deleteConfirm&id=%s", cbControllerPath, cb.getId());
				String deleteLink = String.format("<a href='%s'>Delete</a>", deletePath);
				
				out.println("<tr>");
				out.println("<td>" + cb.getId() + "</td>");
				out.println("<td>" + cb.getName() + "</td>"); 
				out.println("<td>" + cb.getAddress() + "</td>");
				out.println("<td>" + editLink + "</td>");
				out.println("<td>" + deleteLink + "</td>");
				out.println("</tr>");
			}
		%>
		</table>
		<a href="<%= request.getContextPath() + "/employeeLogin"%>">Return to Dashboard</a>

		
    </body>
</html>
