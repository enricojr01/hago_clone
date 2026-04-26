<%-- 
    Document   : viewqueues
    Created on : 23 Apr 2026, 6:36:29 pm
    Author     : anonymous
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>View all Queues you have joined</h1>
        <c:choose>
            <c:when test="${empty queues}">
                <p>You have joined no queues</p>
            </c:when>
            <c:otherwise>
                <ul>
                <c:forEach items="${queues}" var="q">
                    <li>${(q.clinic).name}: ${(q.service).name}</li>
                </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>        
        <a href="patientJoinQueue">Join another queue</a>
        <a href="patientDashboard">Go Back</a>
    </body>
</html>
