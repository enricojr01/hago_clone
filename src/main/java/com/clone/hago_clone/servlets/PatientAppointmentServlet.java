/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.AppointmentDAO;
import com.clone.hago_clone.db.PatientDAO;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.PatientBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "PatientAppointmentServlet", urlPatterns = {"/patient/appointments"})
public class PatientAppointmentServlet extends HttpServlet {
    
    private AppointmentDAO db;
        
    @Override
    public void init() {
        ConnectionDetails cdt = DBConnections.prod();
        try {
            db = new AppointmentDAO(
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
     * GETS all appointments that the patient (a.k.a. the user) has
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
        
        PatientBean pb = (PatientBean)session.getAttribute("patientBean");
        
        if(pb == null) {
            throw new ServletException("PatientBean not found");            
        }
        try {
            ArrayList<AppointmentBean> list = db.findAppointmentsByPatient(pb);                                
            request.setAttribute("appointments",list);
            RequestDispatcher rd = request.getRequestDispatcher("patientview/appointmenttable.jsp");
            rd.forward(request, response);                        
        } catch(SQLException e) {            
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * POSTS a new appointment for the user, does error checks to ensure validity...
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
        return "Proof of Concept for a REST-ful API without using Spring Boot";
    }// </editor-fold>

}
