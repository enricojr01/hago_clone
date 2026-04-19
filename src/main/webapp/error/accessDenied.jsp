<%-- 
    Document   : accessDenied.jsp
    Created on : Apr 14, 2026, 3:16:50 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Access Denied</title>
    </head>
    <body>
        <h1>You do not have permission to access this page.</h1>
		<p>Click <a href=<%= request.getContextPath() + "/" %>>here</a> to go back to the home page.</p>
    </body>
</html>
