<%-- 
    Document   : deleteConfirm
    Created on : Apr 21, 2026, 12:09:43 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Delete Service</title>
    </head>
    <body>
        <h1>You wish to delete the following Service:</h1>
		<jsp:useBean id="serviceBean" scope="request" class="com.clone.hago_clone.models.ServiceBean"/>
		<ul>
			<li>Id: <%= serviceBean.getId() %></li>
			<li>Name: <%= serviceBean.getName() %></li>
			<li>Description: <%= serviceBean.getDescription() %></li>
		</ul>
		<a href="<%= request.getContextPath() + "/serviceBeanServlet?action=list" %>">No, I want to go back.</a> <br />
		<a href="<%= request.getContextPath() + "/serviceBeanServlet?action=deleteSave&id=" + serviceBean.getId() %>">Yes, I want to delete this Service.</a>
    </body>

</html>
