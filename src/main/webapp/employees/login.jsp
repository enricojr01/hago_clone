<%-- 
    Document   : login
    Created on : Apr 8, 2026, 10:13:42 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Login</title>
    </head>
    <body>
		<form action=<%= request.getContextPath() + "/employeeLogin" %> method="POST">
<% 
	Object error = request.getAttribute("error");
	if (error != null) {
		String e = error.toString();
		out.println("<fieldset>");
		out.println(error);
		out.println("</fieldset>");
	}
%>
			<fieldset>
				<legend>Email/Password</legend>
				<input type="text" name="email" placeholder="Email address"/>
				<input type="password" name="password" placeholder="Password"/>
			</fieldset>
			<fieldset>
				<legend>Email/Password</legend>
				<input type="submit" value="Login"/>
				<input type="reset" value="Clear"/>
			</fieldset>
		</form>
    </body>
</html>
