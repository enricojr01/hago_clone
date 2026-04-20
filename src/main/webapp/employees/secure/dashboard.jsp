<%-- 
    Document   : dashboard
    Created on : Apr 5, 2026, 7:03:04 PM
    Author     : Enrico Tuvera Jr
--%>

<%@page import="com.clone.hago_clone.models.EmployeeBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HAGO Clone Employee Dashboard</title>
    </head>
    <body>
		<% 
			EmployeeBean eb = (EmployeeBean) session.getAttribute("employeeBean");
			if (eb.getRole().equals("superadmin")) {
				String adminEmployeePath = request.getContextPath() + "/employeeBeanServlet?action=list";
				String adminPatientsPath = request.getContextPath() + "/patientBeanServlet?action=list";
				String adminClinicPath = request.getContextPath() + "/clinicBeanServlet?action=list";

				String adminEmployeeLink = "<a href='" + adminEmployeePath + "'>Manage Employee Accounts</a>";
				String adminPatientsLink = "<a href='" + adminPatientsPath + "'>Manage Patient Accounts</a>";
				String adminClinicLink = "<a href='" + adminClinicPath + "'>Manage Clinics</a>";
				
				out.println("<h1>Admin Controls</h1>");
				out.println("<ul>");
				out.println("<li>" + adminEmployeeLink + "</li>");
				out.println("<li>" + adminPatientsLink + "</li>");
				out.println("<li>" + adminClinicLink + "</li>");
				out.println("</ul>");
			} else {
				out.println("<h1>Employee Controls</h1>");
				out.println("<ul>");
				out.println("<li>Coming Soon!</li>");
				out.println("</ul>");

			}
		%>
    </body>
</html>
