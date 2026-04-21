<%-- 
    Document   : dashboard
    Created on : 15 Apr 2026, 12:15:16 pm
    Author     : anonymous
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.clone.hago_clone.models.PatientBean" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Dashboard</title>
    </head>
    <body>
        <h1>Your Dashboard</h1>
        <p>Hello, ${patientBean.getName()}</p>
        <ul>
            <li><a href="/patientNotification">Notifications</a></li>
            <li><a href="/patientQueue">Queues</a></li>
            <li><a href="/patientAppointment">Appointments</a></li>
            <li><a href="/patientLogout">Logout</a></li>
        </ul>       
    </body>
</html>
