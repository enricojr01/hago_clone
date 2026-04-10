<%-- 
    Document   : dashboard
    Created on : Apr 5, 2026, 7:03:04 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HAGO Clone Employee Dashboard</title>
    </head>
    <body>
        <h1>Admin Controls</h1>
		<ul>
			<!-- Create new employees and assign them to clinics -->
			<li><a href="employeeServlet?action=add">Manage Employees system-wide</a></li>
			<!-- Create new clinics. -->
			<li><a href="clinicServlet?action=add">Manage Clinics system-wide</a></li>
			<!-- Create new services. -->
			<li><a href="serviceServlet?action=add">Manage Service Types system-wide</a></li>
		</ul>
		<h1>Employee Controls</h1>
		<ul>
			<li>View Appointments</li>
			<li>Today's Queue</li>
			<li>Clinic Service Availability</li>
		</ul>

    </body>
</html>
