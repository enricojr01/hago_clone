<%-- 
    Document   : joinqueueconf
    Created on : 26 Apr 2026, 1:40:07 pm
    Author     : anonymous
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Queue Joined</title>
    </head>
    <body>
        <h1>Queue has been Successfully Joined</h1>
        <p>${pq.patient.name}</p>
        <p>${pq.queue.clinic.name}</p>
        <p>${pq.queue.service.name}</p>                
        <a href="patientListQueues">Go back</a>
    </body>
</html>
