<%-- 
    Document   : edit
    Created on : Apr 17, 2026, 2:30:14 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Clinic</title>
    </head>
    <body>
		<jsp:useBean id="clinicBean" scope="request" class="com.clone.hago_clone.models.ClinicBean"/>
		<form action="<%= request.getContextPath() + "/clinicBeanServlet" %>">
			<input type="hidden" name="action" value="editSave"/>
			<input type="hidden" name="id" value="<%= clinicBean.getId() %>"/>
			<label>Name:</label>
			<input type="text" name="clinicName" value= "<%= clinicBean.getName() %>"/>
			<label>Address:</label>
			<input type="text" name="clinicAddress" value="<%= clinicBean.getAddress() %>"/>
			<input type="submit" value="Submit"/>
			<input type="reset" value="Reset"/>
		</form>
    </body>
</html>
