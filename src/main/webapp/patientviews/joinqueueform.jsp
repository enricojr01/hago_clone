<%-- 
    Document   : joinqueueform
    Created on : 25 Apr 2026, 10:16:37 pm
    Author     : anonymous
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList,com.clone.hago_clone.models.ClinicBean,com.clone.hago_clone.models.QueueBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public String listToString(ArrayList<QueueBean> l) {
        StringBuilder sb = new StringBuilder();
        for (QueueBean b : l) {
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
        <title>JSP Page</title>
        <script>
            function
            onClinicChange(event)
            {
                //console.log(event);                                                
                if (event.target.selectedIndex > 0) {
                    //console.log("YES");
                    const service = document.getElementById("service");                                                            
                    const selectedService = service.options[service.selectedIndex].value;
                    const validServices = event.target.options[event.target.selectedIndex].dataset.services.split(" ").filter((e) => {return e;});                    
                    
                    if (service.selectedIndex > 0 && !validServices.includes(selectedService)) {
                        service.selectedIndex = 0;
                    }
                    
                    for (const s of service.options) {                        
                        if (s.value) {
                            
                            if (validServices.includes(s.value)) {
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
        <h1>Join A Queue</h1>
        <form method="POST" action="patientJoinQueue">
            <label for="clinic">Clinic: </label>
            <select id="clinic" name="clinic" required>
                <option value="">Select a Clinic</option>
                <c:forEach items="${clinicqueues}" var="cq">
                    <option value="${(cq.key).id}" 
                            data-services="<c:forEach items="${cq.value}" var="q">${q.getService().getId()} </c:forEach>">
                        ${(cq.key).name}                                                
                    </option>
                </c:forEach>
            </select>            
            <label for="Service">Service: </label>
            <select id="service" name="service" required>
                <option value="">Select a Service</option>
                <c:forEach items="${services}" var="s">
                    <option value="${s.id}">${s.name}</option>
                </c:forEach>
            </select>
            <br/>                        
            <input type="submit" value="Join Queue"/>            
        </form>
    </body>
</html>
