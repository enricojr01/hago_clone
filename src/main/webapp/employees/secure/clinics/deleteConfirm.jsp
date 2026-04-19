<%-- 
    Document   : deleteConfirm.jsp
    Created on : Apr 19, 2026, 10:36:06 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete a Clinic</title>
    </head>
    <body>
		<jsp:useBean id="clinicBean" scope="request" class="com.clone.hago_clone.models.ClinicBean"/>
        <h1>Confirm Delete</h1>
		<p>You wish to delete the following Clinic: </p>
		<ul>
			<li>Id: <%= clinicBean.getId() %></li>
			<li>Name: <%= clinicBean.getName() %></li>
			<li>Address: <%= clinicBean.getAddress() %></li>
		</ul>
			<a href="<%= request.getContextPath() + "/clinicBeanServlet?action=list" %>">
				No, I want to go back
			</a>
			<a href="<%= request.getContextPath() + "/clinicBeanServlet?action=delete&id=" + clinicBean.getId() %>">
				Yes, I want to delete this Clinic
			</a>
    </body>
</html>
