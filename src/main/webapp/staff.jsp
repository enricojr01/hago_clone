<%-- 
    Document   : staff
    Created on : Apr 5, 2026, 6:33:28 PM
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
        <h1>Staff Login:</h1>
		<form action="">
			<fieldset>
				<legend>Username</legend>
				<label>Username:</label>
				<input type="text" name="username" value="" />
				<br /> 
				<label>Password:</label>
				<input type="password" name="password" value="" />
			</fieldset>
			<fieldset>
				<legend>Login</legend>
				<input type="submit" value="Login"/>
				<input type="reset" value="Clear"/> <br/>
				<a href="index.html">Patient Login</a>
			</fieldset>
		</form>
    </body>
</html>
