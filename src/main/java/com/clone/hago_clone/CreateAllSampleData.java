/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone;

import com.clone.hago_clone.db.AppointmentDAO;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.ClinicServiceDAO;
import com.clone.hago_clone.db.ClinicTimeSlotDAO;
import com.clone.hago_clone.db.EmployeeDAO;
import com.clone.hago_clone.db.PatientDAO;
import com.clone.hago_clone.db.PatientQueueDAO;
import com.clone.hago_clone.db.QueueDAO;
import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.db.TimeSlotDAO;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.AppointmentStatus;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicServiceBean;
import com.clone.hago_clone.models.ClinicTimeSlotBean;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.ServiceBean;
import com.clone.hago_clone.models.TimeSlotBean;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            QueueDAO q = new QueueDAO(url,uname,pword);            
            PatientQueueDAO pq = new PatientQueueDAO(url,uname,pword);
            TimeSlotDAO ts = new TimeSlotDAO(url,uname,pword);
            ClinicTimeSlotDAO cts = new ClinicTimeSlotDAO(url,uname,pword);            
            
            
            a.dropAppointmentTable();
            cts.dropClinicTimeSlotTable();
            cs.dropClinicServiceTable();                                    
            e.dropEmployeeTable();         
            pq.dropPatientQueueTable();
            q.dropQueueTable();
            s.dropServiceTable();                        
            c.dropClinicTable();
            p.dropPatientTable();            
            ts.dropTimeSlotTable();
            

            ts.createTimeSlotTable();
            p.createPatientTable();            
            c.createClinicTable();                                    
            s.createServiceTable();           
            q.createQueueTable();
            pq.createPatientQueueTable();            
            e.createEmployeeTable();
            cs.createClinicServiceTable();            
            cts.createClinicTimeSlotTable();
            a.createAppointmentTable();                        
            
            
            ClinicBean cb,cb1,cb2;
            ServiceBean sb,sb1,sb2,sb3;
            //ClinicServiceBean csb,csb1,csb2,csb3,csb4,csb5,csb6;
            
            cb = c.createClinic("Onett Clinic","Hong Kong");
            cb1 = c.createClinic("Twosome Clinic","Somewhere in Asia");
            cb2 = c.createClinic("Threed Clinic","Camelot");
            
            sb = s.createService("General Check-Up", "General");
            sb1 = s.createService("ENT Check-Up", "Ears, Nose, Throat");
            sb2 = s.createService("Foot Check-Up", "Feet");
            sb3 = s.createService("X-Ray", "X-Ray");
            
            cs.createClinicService(cb,sb);
            cs.createClinicService(cb,sb1);
            cs.createClinicService(cb,sb2);
            
            cs.createClinicService(cb1,sb1);
            cs.createClinicService(cb1,sb3);
            
            cs.createClinicService(cb2,sb);
            cs.createClinicService(cb2,sb2);
            cs.createClinicService(cb2,sb3);
            
            TimeSlotBean tb,tb1,tb2,tb3;            
            tb = ts.createTimeSlot(LocalTime.of(9, 0, 0), LocalTime.of(12,0,0), 10);
            tb1 = ts.createTimeSlot(LocalTime.of(13, 30, 0), LocalTime.of(18,0,0), 10);
            
            tb2 = ts.createTimeSlot(LocalTime.of(10, 30, 0), LocalTime.of(21,30,0), 10);
            
            tb3 = ts.createTimeSlot(LocalTime.of(6, 0, 0), LocalTime.of(8,30,0), 10);            
            
            
            //ClinicTimeSlotBean ctb;
            
            cts.createClinicTimeSlot(cb,tb);
            cts.createClinicTimeSlot(cb,tb1);
            
            cts.createClinicTimeSlot(cb1,tb2);
            
            cts.createClinicTimeSlot(cb2,tb3);
            
                        
            q.createNewQueue(cb, sb, 10);
            q.createNewQueue(cb, sb1, 10);
            q.createNewQueue(cb, sb2, 10);
            
            q.createNewQueue(cb1, sb1, 10);
            q.createNewQueue(cb1, sb3, 10);
            
            q.createNewQueue(cb2, sb, 10);
            q.createNewQueue(cb2, sb2, 10);
            q.createNewQueue(cb2, sb3,10);
            
            
            
            e.addEmployee("superadmin", "sadmin", "sadmin", "123456", cb.getId());
            
            PatientBean pb = p.createPatient("Patient", "email@email.com", "password");
            
            AppointmentBean ab0,ab1,ab2,ab3,ab4;
            
            ab0 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.MAY, 1, 10, 0)), 
                    pb, cb, sb);
            
            ab1 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.MAY, 2, 14, 30)), 
                    pb, cb, sb1);
            
            ab2 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.MAY, 3, 13, 0)), 
                    pb, cb1, sb3);

            ab3 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.MAY, 4, 12, 0)), 
                    pb, cb2, sb);

            ab4 = a.createAppointment(Timestamp.valueOf(
                    LocalDateTime.of(2026, Month.MAY, 5, 19, 0)), 
                    pb, cb2, sb3);
                        
            ab0.setStatus(AppointmentStatus.CONFIRMED);            
            ab1.setStatus(AppointmentStatus.CONFIRMED);
            ab2.setStatus(AppointmentStatus.CONFIRMED);
            
            a.updateAppointment(ab0);
            a.updateAppointment(ab1);
            a.updateAppointment(ab2);
            
            
/*            
            e.addEmployee("superadmin", "sadmin", "admin@admin.com", "123456");
            
            PatientBean pb = p.createPatient("Patient", "patient@patient.com", "password");
            ClinicBean cb = c.createClinic("Clinic","Clinic");
            ClinicBean cb1 = c.createClinic("Clinic 2","Clinic 2");
            
            ServiceBean sb = s.createService("Service", "Service");                                    
            ServiceBean sb1 = s.createService("Service 2", "Service 2");                                    
            ServiceBean sb2 = s.createService("Service 3", "Service 3");                                    
                                    
            ClinicServiceBean csb = cs.createClinicService(cb, sb);            
            cs.createClinicService(cb, sb1);
            cs.createClinicService(cb, sb2);            
            cs.createClinicService(cb1, sb);
            cs.createClinicService(cb1, sb1);            
            
            //cs.createClinicService(cb1, sb1);

            q.createNewQueue(cb, sb, 10);
            q.createNewQueue(cb, sb1, 10);
            q.createNewQueue(cb, sb2, 10);
            
            q.createNewQueue(cb1, sb, 20);
            q.createNewQueue(cb1, sb1, 5);
                                    
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
            
            
            ab0.setStatus(AppointmentStatus.CONFIRMED);            
            ab1.setStatus(AppointmentStatus.CONFIRMED);
            ab2.setStatus(AppointmentStatus.CONFIRMED);
            
            a.updateAppointment(ab0);
            a.updateAppointment(ab1);
            a.updateAppointment(ab2);
*/
            
            
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
