/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.filters;

import com.clone.hago_clone.models.EmployeeBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class UserHasAdminRoleFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(
			ServletRequest sr, ServletResponse sr1, 
			FilterChain fc
	) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sr;
		HttpServletResponse response = (HttpServletResponse) sr1;
		HttpSession session = request.getSession();
		EmployeeBean eb = (EmployeeBean) session.getAttribute("employeeBean");
		if (eb.getRole() == "admin") {
			fc.doFilter(sr, sr1);
		} else {
			String path = request.getContextPath() + "/error/accessDenied.jsp";
			response.sendRedirect(path);
		}

	}

	@Override
	public void destroy() {}
	
}
