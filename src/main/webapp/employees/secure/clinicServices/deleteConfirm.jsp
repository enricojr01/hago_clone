<%-- 
    Document   : deleteConfirm.jsp
    Created on : Apr 21, 2026, 5:48:39 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="clinicBean" scope="request" class="com.clone.hago_clone.models.ClinicBean"/>
<jsp:useBean id="serviceBean" scope="request" class="com.clone.hago_clone.models.ServiceBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Removal of Service from Clinic</title>
    </head>
    <body>
        <h1>Confirm that you are going to remove the following service: </h1>
		<ul>
			<li>Id: <%= serviceBean.getId() %></li>
			<li>Name: <%= serviceBean.getName() %></li>
		</ul>
		<h1>From the following clinic: </h1>
		<ul>
			<li>Id: <%= clinicBean.getId() %></li>	
			<li>Name: <%= clinicBean.getName() %></li>
		</ul>
		<br />
		<a href="<%= request.getContextPath() 
				+ "/clinicServiceBeanServlet?action=listServices&clinicId=" 
				+ clinicBean.getId() 
		%>">No, I want to go back.</a><br/>
		<a href="<%= request.getContextPath() 
				+ "/clinicServiceBeanServlet?action=deleteSave&clinicId=" 
				+ clinicBean.getId() 
				+ "&serviceId=" 
				+ serviceBean.getId() 
		%>">Yes, I would like to remove this Service.</a>
    </body>
</html>
