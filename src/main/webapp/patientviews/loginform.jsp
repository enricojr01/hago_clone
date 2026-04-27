<%-- 
    Document   : loginform
    Created on : 22 Apr 2026, 12:08:37 pm
    Author     : anonymous
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>        
        <h1>Login as Patient</h1>        
        <p><%= (request.getAttribute("error") == null) ? "" : request.getAttribute("error") %></p>
        <form method="POST" action="patientLogin">
            <label>Email: <input type="email" name="email" required="required"/></label>            
            <label>Password: <input type="password" name="password" required="required"/></label>                                    
            <input type="submit" value="Login"/>
        </form>        
    </body>
</html>
