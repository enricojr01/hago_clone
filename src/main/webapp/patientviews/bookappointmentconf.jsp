<%-- 
    Document   : bookappointmentconf
    Created on : 23 Apr 2026, 5:19:52 pm
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
        <h1>Appointment successfully booked!</h1>
        <p>Clinic: ${(appointmentInfo.clinic).name}</p>
        <p>Service: ${(appointmentInfo.service).name}</p>
        <p>Date: ${appointmentInfo.date}</p>
        <a href="patientListAppointments">Return home</a>
    </body>
</html>
