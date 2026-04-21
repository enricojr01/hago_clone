<%-- 
    Document   : add.jsp
    Created on : Apr 21, 2026, 11:54:27 AM
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
		<h1>Add New Service Type</h1>
		<form action="<%= request.getContextPath() + "/serviceBeanServlet" %>" method="GET">
			<% 
				Object error = request.getAttribute("error");
				if (error != null) {
					String message = error.toString();
					out.println("<fieldset>");
					out.println(message);
					out.println("</fieldset>");
				}
			%>
			<fieldset>
				<input type="hidden" name="action" value="addSave"/>
				<label>Name: </label>
				<input type="text" name="serviceName"/>
				<br />
				<label>Description: </label>
				<input type="text" name="serviceDescription"/>
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
