/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.db.AppointmentDAO;
import java.io.IOException;
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
 * @author anonymous
 */
@WebServlet(name = "PatientAppointmentServlet", urlPatterns = {"/patients/appointments"})
public class PatientAppointmentServlet extends HttpServlet {
    private AppointmentDAO db;
    
    //Honestly, why not just make a superclass of HttpServlet, that can handle this fetching? 
    //And all we would need to do is provide the specific class and we can assign the ouput to a variable
    //Or is that bad because that would need reflection?
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
            return;
        }
        if(session.getAttribute("employeeBean") != null) {
            return;            
        }
        PatientBean pb = (PatientBean)session.getAttribute("patientBean");        
        if(pb == null) {
            return;            
        }
        
        try {
            ArrayList<AppointmentBean> appointments = db.findAppointmentsByPatient(pb);        
            request.setAttribute("appointments", appointments);
            RequestDispatcher rd = request.getRequestDispatcher("/patients/appointmenttable.jsp");
            rd.forward(request,response);
            return;
        } catch(SQLException e) {            
            e.printStackTrace();
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
        doPost(request,response);
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
