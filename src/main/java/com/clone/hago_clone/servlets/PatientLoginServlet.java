/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.PatientDAO;
import com.clone.hago_clone.models.PatientBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mysql.jdbc.StringUtils;
import java.sql.SQLException;

/**
 *
 * @author anonymous
 */
@WebServlet(name = "PatientLoginServlet", urlPatterns = {"/patientLogin"})
public class PatientLoginServlet extends HttpServlet {

    private PatientDAO db;

    //Honestly, why not just make a superclass of HttpServlet, that can handle this fetching? 
    //And all we would need to do is provide the specific class and we can assign the ouput to a variable
    //Or is that bad because that would need reflection?
    @Override
    public void init() {
        ConnectionDetails cdt = DBConnections.prod();
        try {
            db = new PatientDAO(
                    cdt.getUrl(),
                    cdt.getUsername(),
                    cdt.getPassword()
            );
        } catch (ClassNotFoundException e) {
            // TODO: figure out how to correctly handle exceptions at the
            // 		 servlet level.
            e.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method. Get the login page if we are
     * not logged in, or if we are then we skip to the dashboard as is
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                
        System.out.println("PatientLoginServer doGet()");
        
        RequestDispatcher rd;        
        HttpSession session = request.getSession(false);                                       
        if(session != null) {            
            if(session.getAttribute("patientBean") != null)  {
                //skip to the dashboard                
                System.out.println("PatientLoginServer doGet() -> patientviews/dashboard.jsp");
                response.sendRedirect("patientDashboard");
                return;
            }
            
            //Are they an employee?
            if(session.getAttribute("employeeBean") != null) {                
                System.out.println("PatientLoginServer doGet() -> index.html");
                response.sendRedirect("index.html");                 
                return;
            }                                                            
            session.invalidate();
        }         
        
        //go to login page
        
        System.out.println("PatientLoginServer doGet() -> patientviews/loginform.jsp");
        rd = request.getRequestDispatcher("patientviews/loginform.jsp");            
        rd.forward(request,response);        
    }

    /**
     * Handles the HTTP <code>POST</code> method. Validates the login page form.
     * If successful, then go to the dashboard, else return back to the login
     * page with the error info
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        //verify that there isn't already a session associated with the user
        if(session != null) {
            if(session.getAttribute("patientBean") != null)  {
                //skip to the dashboard                
                response.sendRedirect("patientDashboard");
                return;
            }            
            //Are they an employee?
            if(session.getAttribute("employeeBean") != null) {                
                response.sendRedirect("index.html");                 
                return;
            }      
            //if there is nothing there, then just invalidate the session
            session.invalidate();            
        }
        
        RequestDispatcher rd;        
        
        String email = request.getParameter("email"),        
               pwd = request.getParameter("password");
        
        String errStr = "";                
        boolean err = false;
        
        if(StringUtils.isNullOrEmpty(email)) {
            err = true;
            errStr += "Missing Email";
        }
        if(StringUtils.isNullOrEmpty(pwd)) {
            
            if(err) { 
                errStr += " and Password"; 
            } else {                 
                errStr += "Missing Password"; 
            }        
            err = true;
        }
        
        if(err) {            
            request.setAttribute("error", errStr);
            rd = request.getRequestDispatcher("patientviews/loginform.jsp");            
            rd.forward(request,response);        
        }
        
        //now validate
        try {
            PatientBean user = db.validateCredentials(email,pwd);            
            if(user != null) {                                         
                session = request.getSession(true);
                session.setAttribute("patientBean", user);
                response.sendRedirect("patientDashboard");
                return;
            }
        } catch(SQLException e) {            
            e.printStackTrace();
        }
        
        request.setAttribute("error", "Invalid Credentials");
        rd = request.getRequestDispatcher("patientviews/loginform.jsp");            
        rd.forward(request,response);        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "For Patient Login";
    }// </editor-fold>

}
