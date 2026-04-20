<%-- 
    Document   : deleteConfirm.jsp
    Created on : Apr 20, 2026, 1:53:10 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
		<jsp:useBean id="patientBean" scope="request" class="com.clone.hago_clone.models.PatientBean"/>
        <h1>Confirm Delete</h1>
		<p>You wish to delete the following Patient: </p>
		<ul>
			<li>Id: <%= patientBean.getId() %></li>
			<li>Name: <%= patientBean.getName() %></li>
			<li>Address: <%= patientBean.getEmail() %></li>
		</ul>
			<a href="<%= request.getContextPath() + "/patientBeanServlet?action=list" %>">
				No, I want to go back
			</a>
			<a href="<%= request.getContextPath() + "/patientBeanServlet?action=deleteSave&id=" + patientBean.getId() %>">
				Yes, I want to delete this Patient account
			</a>
    </body>
</html>
