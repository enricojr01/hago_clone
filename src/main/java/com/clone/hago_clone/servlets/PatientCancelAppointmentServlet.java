/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.AppointmentDAO;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.AppointmentStatus;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author a1
 */
@WebServlet(name = "PatientCancelAppointmentServlet", urlPatterns = {"/patientCancelAppointment"})
public class PatientCancelAppointmentServlet extends HttpServlet {
    private AppointmentDAO db;
    
    @Override
    public void init() {
        
        ConnectionDetails cdt = DBConnections.prod();
        try {
            db = new AppointmentDAO(cdt.getUrl(),cdt.getUsername(),cdt.getPassword());                  
        } catch (ClassNotFoundException e) {            
            // TODO: figure out how to correctly handle exceptions at the
            // 		 servlet level.
            e.printStackTrace();
        }        
    }
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);    
        if(session == null) {
            throw new ServletException("HttpSession not found");            
        }
        
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));                                        
            String confirm = request.getParameter("confirm");        
            if(!StringUtils.isNullOrEmpty(confirm)) {            
                if(confirm.equalsIgnoreCase("YES")) {                    
                    AppointmentBean tmp = db.findAppointmentById(id);                    
                    tmp.setStatus(AppointmentStatus.CANCELLED_USER);
                    db.updateAppointment(tmp);            
                } else if (confirm.equalsIgnoreCase("NO")){
                    
                } else {
                    //invalid state
                }
                
                response.sendRedirect("patientListAppointments");
            }
            else {                                
                RequestDispatcher rd = request.getRequestDispatcher("patientviews/cancelappointmentconf.jsp");
                request.setAttribute("app", db.findAppointmentById(id));
                rd.forward(request, response);                
            }
        } catch(SQLException e) {      
            throw new ServletException(e.getMessage());            
        } catch(NumberFormatException e) {
            throw new ServletException(e.getMessage());
        }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
