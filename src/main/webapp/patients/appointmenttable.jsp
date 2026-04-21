<%-- 
    Document   : appointmenttable
    Created on : 21 Apr 2026, 11:27:50 am
    Author     : anonymous
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Appointments</title>
    </head>
    <body>
        <h1>Your Appointments</h1>
        <c:if test="${not empty appointments}">
            <table>
                <thead>                
                    <tr>
                        <th>Id</th>
                        <th>Date</th>
                        <th>Clinic</th>
                        <th>Service</th>
                        <th>Status</th>                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${appointments}" var="app">
                        <tr>
                            <td>${app.getId()}</td>
                            <td>${app.getDate()}</td>
                            <td>${app.getClinic()}</td>
                            <td>${app.getService()}</td>
                            <td>${app.getCancellation()}</td>                                    
                        </tr>                
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
