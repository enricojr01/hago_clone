<%-- 
    Document   : cancelappointmentconf
    Created on : Apr 22, 2026, 3:54:38 PM
    Author     : a1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Cancellation</title>
    </head>
    <body>
        <h1>Are you sure you want to cancel this appointment?</h1>
        <p>${app.date}</p>
        <p>${(app.clinic).name}}</p>
        <p>${(app.service).name}</p>
        <p>${(app.status).getNiceString()}</p>        
        <a href="patientCancelAppointment?id=${app.id}&confirm=YES">Yes</a>
        <a href="patientListAppointment">No</a>        
        
    </body>
</html>
