<%-- 
    Document   : list
    Created on : Apr 21, 2026, 11:49:40 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.ServiceBean, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Master List</title>
    </head>
    <body>
		<jsp:useBean id="serviceList" scope="request" class="java.util.ArrayList<ServiceBean>"/>
		<a href="<%= request.getContextPath() + "/serviceBeanServlet?action=addDisplay" %>">Add a New Service</a>
		<table>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Description</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		<%
			for (int i = 0; i < serviceList.size(); i++) {
				ServiceBean eb = serviceList.get(i);
				
				String cbControllerPath = request.getContextPath() + "/serviceBeanServlet";
				String editPath = String.format("%s?action=editDisplay&id=%s", cbControllerPath, eb.getId());
				String editLink = String.format("<a href='%s'>Edit</a>", editPath);
				String deletePath = String.format("%s?action=deleteConfirm&id=%s", cbControllerPath, eb.getId());
				String deleteLink = String.format("<a href='%s'>Delete</a>", deletePath);
				
				out.println("<tr>");
				out.println("<td>" + eb.getId() + "</td>");
				out.println("<td>" + eb.getName() + "</td>");
				out.println("<td>" + eb.getDescription() + "</td>");
				out.println("<td>" + editLink + "</td>");
				out.println("<td>" + deleteLink + "</td>");
				out.println("</tr>");
			}
		%>
		</table>
		<a href="<%= request.getContextPath() + "/employeeLogin" %>">Return to Dashboard</a>
    </body>
</html>
