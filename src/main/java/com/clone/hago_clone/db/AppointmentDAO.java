/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;
//Create Functions
    //Read Functions
    //Update Functions
    //Delete Functions
import java.sql.Timestamp;

import com.clone.hago_clone.models.TimeslotBean;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.ServiceBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author anonymous
 */
public class AppointmentDAO extends BaseDAO {

    private PatientDAO patient;
    private ClinicDAO clinic;
    private ServiceDAO service;
    private TimeslotDAO timeslot;
    
    public AppointmentDAO(String url, String username, String password)
            throws ClassNotFoundException {
        super(url, username, password);
        
        this.patient = new PatientDAO(url,username,password);
        this.clinic = new ClinicDAO(url,username,password);
        this.service = new ServiceDAO(url,username,password);        
        this.timeslot = new TimeslotDAO(url,username,password);
    }

    @Override
    protected String createTableStatement() {
        return "CREATE TABLE IF NOT EXISTS Appointment (\n"
                + "id INT NOT NULL AUTO_INCREMENT,\n"
                + "date DATETIME NOT NULL,\n"                
                + "status ENUM('AWAITING','CONFIRMED') NOT NULL,\n"
                + "patientId INT NOT NULL,\n"
                + "clinicId INT NOT NULL,\n"
                + "serviceId INT NOT NULL,\n"
                + "timeslotId INT NOT NULL,\n"
                + "PRIMARY KEY (id),\n"
                + "FOREIGN KEY (patientId) REFERENCES Patient(id),\n"
                + "FOREIGN KEY (clinicId,serviceId) REFERENCES ClinicService(clinicId,serviceId)),\n"
                + "FOREIGN KEY (timeslotId) REFERECES Timeslot(id))";
    }

    @Override
    protected String dropTableStatement() {
        return "DROP TABLE Appointment";
    }

    public boolean createAppointmentTable()
            throws SQLException {
        return createTable();
    }

    public boolean dropAppointmentTable()
            throws SQLException {
        return dropTable();
    }

    //Create Functions
    public AppointmentBean createAppointment(Timestamp appointmentDate, TimeslotBean timeslot, PatientBean patient, ClinicBean clinic, ServiceBean service) throws SQLException {        
        AppointmentBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Appointment (date,status,patientId,clinicId,serviceId,timeslotId) VALUES (?,\'AWAITING\',?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
        ps.setTimestamp(1, appointmentDate);
        ps.setInt(2,patient.getId());        
        ps.setInt(3,(int) clinic.getId());
        ps.setInt(4,(int) service.getId());
        ps.setInt(5,timeslot.getId());

        //Perhaps we should stick to using ints, because thats how they are stored in the database, so we dont have to worry about size issues

        
        if(ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            int id = rs.getInt(1);
            retval = new AppointmentBean(id,appointmentDate,timeslot,"awaiting",patient,clinic,service);
            rs.close();
        }
                        
        ps.close();
        c.close();        
        return retval;
    }
    
    //Read Functions
    
    //This function also needs the timeslot DAO...
    
    public ArrayList<AppointmentBean> getAllAppointments() throws SQLException {
        ArrayList<AppointmentBean> retval = new ArrayList();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT id,date,status,patientId,clinicId,serviceId,timeslotId FROM Appointment");
        while(rs.next()) {
            int id = rs.getInt("id");
            Timestamp date = rs.getTimestamp("date");
            String status = rs.getString("status");
            int patientId = rs.getInt("patientId"),
                clinicId = rs.getInt("clinicId"),
                serviceId = rs.getInt("serviceId"),
                timeslotId = rs.getInt("timeslotId");
            
            AppointmentBean tmp = new AppointmentBean(id,date,
                    timeslot.findTimeslotById(timeslotId),
                    status,
                    patient.findPatientById(patientId),
                    clinic.findClinicById(clinicId),
                    service.findServiceById(serviceId)
            );
            
            retval.add(tmp);                        
        }
        
        rs.close();
        s.close();
        c.close();
        
        return retval;
        
    }
        
    public AppointmentBean findAppointmentById(int id) throws SQLException {
        AppointmentBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT date,status,patientId,clinicId,serviceId,timeslotId FROM Appointment WHERE id = ?");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {            
            Timestamp date = rs.getTimestamp("date");
            String status = rs.getString("status");
            int patientId = rs.getInt("patientId"),
                clinicId = rs.getInt("clinicId"),
                serviceId = rs.getInt("serviceId"),
                timeslotId = rs.getInt("timeslotId");
            
            retval = new AppointmentBean(id,date,
                    timeslot.findTimeslotById(timeslotId),
                    status,
                    patient.findPatientById(patientId),
                    clinic.findClinicById(clinicId),
                    service.findServiceById(serviceId)
            );            
        }
        rs.close();
        ps.close();
        c.close();
        return retval;        
    }
    
    //Update Functions
    //I dunno...
    
    //Delete Functions
    public boolean deleteAppointment(AppointmentBean appointment) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM Appointment WHERE id = ?");
        ps.setInt(1,appointment.getId());
        boolean retval = (ps.executeUpdate() > 0);
        ps.close();
        c.close();
        return retval;        
    }
    
    
    

}
