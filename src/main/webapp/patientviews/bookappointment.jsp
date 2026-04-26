<%-- 
    Document   : bookappointment
    Created on : Apr 22, 2026, 4:32:21 PM
    Author     : a1
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList,com.clone.hago_clone.models.ServiceBean,com.clone.hago_clone.models.ClinicBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    public String listToString(ArrayList<ServiceBean> l) {
        StringBuilder sb = new StringBuilder();
        for(ServiceBean b : l) {
            sb.append(b.getId());
            sb.append(" ");
        }
        return sb.toString();
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book New Appointment</title>
        <script>
            function
            onClinicChange(event) 
            {
                //console.log(event);                
                if(event.target.value) {                                        
                    const service = document.getElementById("service");                    
                    const selectedService = service.options[service.selectedIndex].value;
            
                    const validServices = event.target.options[event.target.selectedIndex].dataset.services.split(" ").filter((e) => {return e;} );
                    //console.log(validServices);
                    if(service.selectedIndex > 0 && !validServices.includes(selectedService)) {
                        service.selectedIndex = 0;
                    }
                    
                    for(const s of service.options) {
                        //console.log(s);
                        if(s.value) {
                            if(validServices.includes(s.value)) {
                                s.disabled = false;
                            } else {
                                s.disabled = true;
                            }
                        }
                    }              
                } else {
                    const service = document.getElementById("service");
                    service.selectedIndex = 0;
                }                              
            }
            
            window.onload = () => { 
                const clinic = document.getElementById("clinic");            
                clinic.onchange = onClinicChange;
            };
            
        </script>
    </head>
    <body>
        <h1>Book New Appointment</h1>
        <form method="POST" action="patientBookAppointment">
            <label for="clinic">Clinic: </label>
            <select id="clinic" name="clinic" required>
                <option value="">Select a Clinic</option>
                <c:forEach items="${clinicservices}" var="cs">
                    <option value="${(cs.key).id}" 
                            data-services="<c:forEach items="${cs.value}" var="s">${s.id} </c:forEach>">
                        ${(cs.key).name}                        
                    </option>
                </c:forEach>
            </select>            
            <label for="service">Service: </label>
            <select id="service" name="service" required>
                <option value="">Select a Service</option>
                <c:forEach items="${services}" var="s">
                    <option value="${s.id}">${s.name}</option>
                </c:forEach>
            </select>
            <br/>            
            <label>Appointment Date and Time</label>
            <input type="datetime-local" id="time" name="time" required/>
            <br/>
            <input type="submit" value="Book Appointment"/>            
        </form>
    </body>
</html>
