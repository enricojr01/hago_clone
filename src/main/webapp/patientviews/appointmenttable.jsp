<%-- 
    Document   : appointmenttable
    Created on : 21 Apr 2026, 11:27:50 am
    Author     : anonymous
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.clone.hago_clone.models.AppointmentStatus" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Appointments</title>
    </head>
    <body>
        <h1>Your Appointments</h1>
        <%-- A much more simpler way to iterate, at least compared to using JSP by itself--%>
        <c:choose>
            <c:when test="${not empty appointments}">
                <table>
                    <thead>                
                        <tr>                        
                            <th>Id</th>
                            <th>Date</th>
                            <th>Clinic</th>
                            <th>Service</th>
                            <th>Status</th> 
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${appointments}" var="app">
                            <tr>                            
                                <td>${app.id}</td>
                                <td>${app.date}</td>
                                <td>${(app.clinic).name}</td>
                                <td>${(app.service).name}</td>
                                <td>${(app.status).getNiceString()}</td>                                    
                                <td>                                    
                                    <c:if test="${ app.status.canCancel() eq true }">
                                        <a href="<c:url value="/patientCancelAppointment?id=${app.id}"/>">Cancel Appointment</a>        
                                    </c:if>                                                                            
                                </td>
                            </tr>                
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No appointments were made...</p>
            </c:otherwise>
        </c:choose>       
        <a href="patientBookAppointment">Book new appointment</a>
    </body>
</html>
