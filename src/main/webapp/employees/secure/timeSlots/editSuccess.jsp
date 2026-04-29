<%-- 
    Document   : editSuccess
    Created on : Apr 17, 2026, 3:01:55 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully edited Time Slot</title>
    </head>
    <body>
        <h1>Time Slot Successfully Edited</h1>
		<ul>
			<li>Id: ${timeSlotBean.id}</li>
			<li>Start: ${timeSlotBean.start}</li>
			<li>End: ${timeSlotBean.end}</li>
			<li>Capacity: ${timeSlotBean.capacity}</li>
		</ul>
		<a href="<%= request.getContextPath() + "/timeSlotBeanServlet?action=list" %>">Back to Time Slot List</a>
    </body>
</html>
