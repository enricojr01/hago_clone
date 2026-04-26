<%-- 
    Document   : edit
    Created on : Apr 21, 2026, 11:59:20 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Service</title>
    </head>
    <body>
		<jsp:useBean id="serviceBean" scope="request" class="com.clone.hago_clone.models.ServiceBean"/>
        <h1>Edit Service</h1>
		<form action="<%= request.getContextPath() + "/serviceBeanServlet" %>" method="GET">
			<fieldset>
				<input type="hidden" name="action" value="editSave"/>
				<input type="hidden" name="id" value="<%= serviceBean.getId() %>"/>
				<label>Name:</label>
				<input type="text" id="serviceName" name="serviceName" value="<%= serviceBean.getName() %>"/>
				<br />
				<label>Description:</label>
				<input type="text" id="serviceDescription" name="serviceDescription" value="<%= serviceBean.getDescription() %>"/>
				<br />
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/serviceBeanServlet?action=list" %>">Return to Service Master List</a>
	</body>
</html>
