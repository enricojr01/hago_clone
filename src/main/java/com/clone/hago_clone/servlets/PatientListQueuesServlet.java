/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.AppointmentDAO;
import com.clone.hago_clone.db.PatientQueueDAO;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.QueueBean;
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
 * @author anonymous
 */
@WebServlet(name = "PatientListQueuesServlet", urlPatterns = {"/patientListQueues"})
public class PatientListQueuesServlet extends HttpServlet {
    private PatientQueueDAO db;    
    
    @Override
        public void init() {        
        ConnectionDetails cdt = DBConnections.prod();
        try {
            db = new PatientQueueDAO(cdt.getUrl(),cdt.getUsername(),cdt.getPassword());                              
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
        try {
            PatientBean pb = (PatientBean)session.getAttribute("patientBean");                        
            ArrayList<QueueBean> l = db.findAllQueuesByPatient(pb);                       
            request.setAttribute("queues",l);
            RequestDispatcher rd = request.getRequestDispatcher("patientviews/viewqueues.jsp");
            rd.forward(request,response);
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
