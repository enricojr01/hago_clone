/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clone.hago_clone.servlets.patient;

import com.clone.hago_clone.ConnectionDetails;
import com.clone.hago_clone.DBConnections;
import com.clone.hago_clone.db.AppointmentDAO;
import com.clone.hago_clone.db.ClinicServiceDAO;
import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.PatientBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
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
 * @author a1
 */
@WebServlet(name = "PatientBookAppointment", urlPatterns = {"/patientBookAppointment"})
public class PatientBookAppointment extends HttpServlet {
    private AppointmentDAO adb;
    private ClinicServiceDAO csdb;        
    private ServiceDAO sdb;
    @Override
    public void init() {
        ConnectionDetails cdt = DBConnections.prod();
        
        try {
            csdb = new ClinicServiceDAO(
                    cdt.getUrl(),
                    cdt.getUsername(),
                    cdt.getPassword()
            );
            sdb = new ServiceDAO(
                    cdt.getUrl(),
                    cdt.getUsername(),
                    cdt.getPassword()
            );
            adb = new AppointmentDAO(
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
            HashMap cs = csdb.findClinicServicesMap();        
            ArrayList s = sdb.findAllServices();
            request.setAttribute("clinicservices",cs);
            request.setAttribute("services",s);
            RequestDispatcher rd = request.getRequestDispatcher("patientviews/bookappointment.jsp");
            rd.forward(request,response);
        } catch(SQLException e) {
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
        if(session == null) {
            throw new ServletException("HttpSession not found");            
        }
        
                
        try {
            long clinic = Long.parseLong(request.getParameter("clinic"));
            long service = Long.parseLong(request.getParameter("service"));
            String timeStr = request.getParameter("time");
            timeStr += ":00";
            System.out.println(timeStr);
            Timestamp time = Timestamp.valueOf(timeStr.replace("T"," "));                                    
            PatientBean pb = (PatientBean)session.getAttribute("patientBean");                                       
            AppointmentBean ab = adb.createAppointmentById(time,pb,clinic,service);
            request.setAttribute("appointmentInfo", ab);
            RequestDispatcher rd = request.getRequestDispatcher("patientviews/bookappointmentconf.jsp");
            rd.forward(request, response);            
        } catch(SQLException e) {
            throw new ServletException(e.getMessage());
        } catch(NumberFormatException e) {
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
