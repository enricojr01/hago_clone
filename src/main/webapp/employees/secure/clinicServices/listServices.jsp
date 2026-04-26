<%-- 
    Document   : listServices.jsp
    Created on : Apr 21, 2026, 3:30:24 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.ClinicBean, com.clone.hago_clone.models.ServiceBean, java.util.ArrayList"%>
<jsp:useBean id="clinicBean" scope="request" class="com.clone.hago_clone.models.ClinicBean"/>
<jsp:useBean id="availableServiceList" scope="request" class="java.util.ArrayList<ServiceBean>"/>
<jsp:useBean id="serviceList" scope="request" class="java.util.ArrayList<ServiceBean>"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clinic Services for <%= clinicBean.getName() %></title>
    </head>
    <body>
        <h1>Services associated with clinic: <%= clinicBean.getName() %></h1>
		<table>
			<tr>
				<th>Id</th>
				<th>Service Name</th>
				<th>Service Description</th>
				<th>Remove Service</th>
			</tr>
			<% 
				for (int i = 0; i < serviceList.size(); i++) {
					ServiceBean sb = serviceList.get(i);
					String targetPath = String.format(
						"%s/clinicServiceBeanServlet?action=deleteConfirm&clinicId=%s&serviceId=%s", 
						request.getContextPath(), 
						clinicBean.getId(), 
						sb.getId()
					);
					String anchor = String.format(
						"<a href='%s'>Remove</a>", 
						targetPath
					);
					
					out.println("<tr>");
					out.println("<td>" + sb.getId() + "</td>");
					out.println("<td>" + sb.getName() + "</td>");
					out.println("<td>" + sb.getName() + "</td>");
					out.println("<td>" + anchor + "</td>");
					out.println("</tr>");
				}
			%>
		</table>

		<h1>Add a Service to this Clinic</h1>
		<form action="<%= request.getContextPath() + "/clinicServiceBeanServlet" %>" method="GET">
			<input type="hidden" name="action" value="addService"/>
			<input type="hidden" name="clinicId" value="<%= clinicBean.getId() %>"/>
			<fieldset>
				<legend>Select a Service</legend>
				<select name="serviceId">
					<% 
						if (availableServiceList.size() == 0) {
							out.println("<option value='empty'>No available Services</option>");
						} else {
							for (int i = 0; i < availableServiceList.size(); i++) {
								ServiceBean sb = availableServiceList.get(i);
								out.println("<option value='" + sb.getId() +"'>" + sb.getName() + "</option>");
							}
						}
					%>
				</select>
			</fieldset>
			<fieldset>
				<legend>Submit/Clear</legend>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
		<a href="<%= request.getContextPath() + "/clinicServiceBeanServlet?action=list"%>">Go Back to Clinic List</a>
    </body>
</html>
