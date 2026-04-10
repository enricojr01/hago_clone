<%-- 
    Document   : loginRequired
    Created on : Apr 8, 2026, 11:32:17 AM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Required</title>
    </head>
    <body>
        <h1>You need to log in to access this page!</h1>
		<p><a href="<%= request.getContextPath() %>" >Return to Home Page</a></p>
    </body>
</html>
