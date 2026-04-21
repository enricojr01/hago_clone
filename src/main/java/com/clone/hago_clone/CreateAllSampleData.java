/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone;

import com.clone.hago_clone.db.AppointmentDAO;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.ClinicServiceDAO;
import com.clone.hago_clone.db.EmployeeDAO;
import com.clone.hago_clone.db.PatientDAO;
import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicServiceBean;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.ServiceBean;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

/**
 *
 * @author anonymous
 */

//To create all the sample data one could need
public class CreateAllSampleData {
    private static final String url = "jdbc:mysql://localhost:3306/javaclass";
    private static final String uname = "root";
    private static final String pword = "";    
    
    
    public static void main(String[] args) {
        try {
            PatientDAO p = new PatientDAO(url,uname,pword);
            ClinicDAO c = new ClinicDAO(url,uname,pword);
            ServiceDAO s = new ServiceDAO(url,uname,pword);            
            AppointmentDAO a = new AppointmentDAO(url,uname,pword);
            EmployeeDAO e = new EmployeeDAO(url,uname,pword);            
            ClinicServiceDAO cs = new ClinicServiceDAO(url,uname,pword);
            
            
            a.dropAppointmentTable();
            cs.dropClinicServiceTable();                                    
            e.dropEmployeeTable();         
            s.dropServiceTable();                        
            c.dropClinicTable();
            p.dropPatientTable();            

            p.createPatientTable();            
            c.createClinicTable();                                    
            s.createServiceTable();           
            e.createEmployeeTable();
            cs.createClinicServiceTable();            
            a.createAppointmentTable();                        
            
            
            
            
                                               
            
            e.addEmployee("superadmin", "sadmin", "admin@admin.com", "123456");
            
            PatientBean pb = p.createPatient("Patient", "patient@patient.com", "password");
            ClinicBean cb = c.createClinic("Clinic","Clinic");
            ServiceBean sb = s.createService("Service", "Service");                                    
            
            
            ClinicServiceBean csb = cs.createClinicService(cb, sb);
            AppointmentBean ab0,ab1,ab2,ab3,ab4;
            
            ab0 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.JANUARY, 1, 10, 0)), 
                    pb, cb, sb);
            
            ab1 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.JANUARY, 2, 14, 30)), 
                    pb, cb, sb);
            
            ab2 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.JANUARY, 3, 13, 0)), 
                    pb, cb, sb);

            ab3 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.JANUARY, 4, 12, 0)), 
                    pb, cb, sb);

            ab4 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.JANUARY, 5, 19, 0)), 
                    pb, cb, sb);
            
            
            ab0.setCancellation("CONFIRMED");            
            ab1.setCancellation("CONFIRMED");
            ab2.setCancellation("CONFIRMED");
            
            a.updateAppointment(ab0);
            a.updateAppointment(ab1);
            a.updateAppointment(ab2);
            
            
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
