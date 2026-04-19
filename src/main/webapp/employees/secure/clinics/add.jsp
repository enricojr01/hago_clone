<%-- 
    Document   : add.jsp
    Created on : Apr 16, 2026, 2:40:29 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add a Clinic</title>
    </head>
    <body>
		<form action="<%= request.getContextPath() + "/clinicBeanServlet" %>">
		<%
			Object error = request.getAttribute("error");
			if (error != null) {
				String message = error.toString();
				out.println("<fieldset>");
				out.println(error);
				out.println("</fieldset>");
			}
		%>
			<fieldset>
				<input type="hidden" name="action" value="save"/>
				<legend>Clinic Details</legend>
				<label>Clinic Name:</label>
				<input type="text" id="name" name="clinicName"/> <br/>
				<label>Clinic Address:</label>
				<input type="text" id="address" name="clinicAddress"/> <br/>
			</fieldset>
			<fieldset>
				<input type="submit" value="Submit"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
    </body>
</html>
