/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets.patient;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.PatientQueueDAO;
import com.clone.hago_clone.db.QueueDAO;
import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.PatientQueueBean;
import com.clone.hago_clone.models.QueueBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
@WebServlet(name = "PatientJoinQueueServlet", urlPatterns = {"/patientJoinQueue"})
public class PatientJoinQueueServlet extends HttpServlet {

    private PatientQueueDAO pqdb;
    private QueueDAO qdb;
    private ServiceDAO sdb;

    @Override
    public void init() {
        ConnectionDetails cdt = DBConnections.prod();
        try {
            pqdb = new PatientQueueDAO(
                    cdt.getUrl(),
                    cdt.getUsername(),
                    cdt.getPassword()
            );
            qdb = new QueueDAO(
                    cdt.getUrl(),
                    cdt.getUsername(),
                    cdt.getPassword()
            );

            sdb = new ServiceDAO(
                    cdt.getUrl(),
                    cdt.getUsername(),
                    cdt.getPassword()
            );

        } catch (ClassNotFoundException e) {
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
        try {
            HashMap cq = qdb.findAllQueuesMap();
            ArrayList s = sdb.findAllServices();
            request.setAttribute("clinicqueues", cq);
            request.setAttribute("services", s);
            RequestDispatcher rd = request.getRequestDispatcher("patientviews/joinqueueform.jsp");
            rd.forward(request, response);
        } catch (SQLException e) {            
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
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ServletException("HttpSession not found");
            
        }
        PatientBean pb = (PatientBean) session.getAttribute("patientBean");
        if (pb == null) {
            throw new ServletException("PatientBean not found");            
        }

        try {
            long clinicId = Long.parseLong(request.getParameter("clinic"));
            long serviceId = Long.parseLong(request.getParameter("service"));
            QueueBean queue = qdb.findQueueByClinicAndServiceId(clinicId, serviceId);
            PatientQueueBean patientQueue = pqdb.CreatePatientQueue(pb, queue);
            request.setAttribute("pq", patientQueue);
            RequestDispatcher rd = request.getRequestDispatcher("patientviews/joinqueueconf.jsp");
            rd.forward(request, response);            
        } catch (NumberFormatException e) {
            throw new ServletException(e.getMessage());                        
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());                        
        }

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
