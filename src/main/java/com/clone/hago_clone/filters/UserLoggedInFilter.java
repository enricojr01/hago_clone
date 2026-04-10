/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.filters;

import com.clone.hago_clone.models.EmployeeBean;
import com.clone.hago_clone.models.PatientBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class UserLoggedInFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response, 
			FilterChain out
	) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		PatientBean pb = 
				(PatientBean) session.getAttribute("patientBean");
		EmployeeBean eb = 
				(EmployeeBean) session.getAttribute("employeeBean");

		if (pb == null || eb == null) {
			String path = req.getContextPath() + "/error/loginRequired.jsp";
			resp.sendRedirect(path);
		} else {
			out.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {}
}
