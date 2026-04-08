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

    private PatientDAO patientDao;
    private ClinicDAO clinicDao;
    private ServiceDAO serviceDao;

    public AppointmentDAO(String url, String username, String password)
            throws ClassNotFoundException {
        super(url, username, password);

        this.patientDao = new PatientDAO(url, username, password);
        this.clinicDao = new ClinicDAO(url, username, password);
        this.serviceDao = new ServiceDAO(url, username, password);
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
                + "PRIMARY KEY (id),\n"
                + "FOREIGN KEY (patientId) REFERENCES Patient(id),\n"               
                + "FOREIGN KEY (clinicId) REFERENCES Clinic(id),\n"
                + "FOREIGN KEY (serviceId) REFERENCES Service(id))";
        
    }

    @Override
    protected String dropTableStatement() {
        return "DROP TABLE Appointment";
    }

    public boolean createAppointmentTable() throws SQLException {
        return createTable();
    }

    public boolean dropAppointmentTable() throws SQLException {
        return dropTable();
    }

    //Create Functions
    public AppointmentBean createAppointment(Timestamp appointmentDate, PatientBean patient, ClinicBean clinic, ServiceBean service) throws SQLException {
        AppointmentBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Appointment (date,status,patientId,clinicId,serviceId) VALUES (?,\'AWAITING\',?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setTimestamp(1, appointmentDate);        
        ps.setInt(2, patient.getId());
        ps.setInt(3, (int) clinic.getId());
        ps.setInt(4, (int) service.getId());

        //Perhaps we should stick to using ints, because thats how they are stored in the database, so we dont have to worry about size issues
        if (ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            retval = new AppointmentBean(id, appointmentDate, "awaiting", patient, clinic, service);
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
        ResultSet rs = s.executeQuery("SELECT id,date,status,patientId,clinicId,serviceId FROM Appointment");
        while (rs.next()) {
            int id = rs.getInt("id");
            Timestamp date = rs.getTimestamp("date");
            String status = rs.getString("status");
            int patientId = rs.getInt("patientId"),
                    clinicId = rs.getInt("clinicId"),
                    serviceId = rs.getInt("serviceId");

            AppointmentBean tmp = new AppointmentBean(id,
                    date,
                    status,
                    patientDao.findPatientById(patientId),
                    clinicDao.findClinicById(clinicId),
                    serviceDao.findServiceById(serviceId));

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
        PreparedStatement ps = c.prepareStatement("SELECT date,status,patientId,clinicId,serviceId FROM Appointment WHERE id = ?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Timestamp date = rs.getTimestamp("date");
            String status = rs.getString("status");
            int patientId = rs.getInt("patientId"),
                    clinicId = rs.getInt("clinicId"),
                    serviceId = rs.getInt("serviceId");

            retval = new AppointmentBean(id, date,
                    status,
                    patientDao.findPatientById(patientId),
                    clinicDao.findClinicById(clinicId),
                    serviceDao.findServiceById(serviceId)
            );
        }
        rs.close();
        ps.close();
        c.close();
        return retval;
    }
    
    public ArrayList<AppointmentBean> findAppointmentsByPatient(PatientBean patient) throws SQLException {
        ArrayList<AppointmentBean> retval = new ArrayList();
        Connection c = getConnection();        
        PreparedStatement ps = c.prepareStatement("SELECT id,date,status,clinicId,serviceId FROM Appointment WHERE patientId = ?");
        ps.setInt(1,patient.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Timestamp date = rs.getTimestamp("date");
            String status = rs.getString("status");
            int id = rs.getInt("id"),
                    clinicId = rs.getInt("clinicId"),
                    serviceId = rs.getInt("serviceId");

            AppointmentBean tmp = new AppointmentBean(id, date,
                    status,
                    patient,
                    clinicDao.findClinicById(clinicId),
                    serviceDao.findServiceById(serviceId)
            );            
            retval.add(tmp);
        }
        
        rs.close();
        ps.close();
        c.close();
        
        return retval;
    }            
    
    public ArrayList<AppointmentBean> findAppointmentsByClinic(ClinicBean clinic) throws SQLException {
        ArrayList<AppointmentBean> retval = new ArrayList();
        Connection c = getConnection();        
        PreparedStatement ps = c.prepareStatement("SELECT id,date,status,patientId,serviceId FROM Appointment WHERE clinicId = ?");
        ps.setInt(1,(int) clinic.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Timestamp date = rs.getTimestamp("date");
            String status = rs.getString("status");
            int id = rs.getInt("id"),
                    patientId = rs.getInt("patientId"),
                    serviceId = rs.getInt("serviceId");

            AppointmentBean tmp = new AppointmentBean(id, date,
                    status,
                    patientDao.findPatientById(patientId),
                    clinic,
                    serviceDao.findServiceById(serviceId)
            );            
            retval.add(tmp);
        }
        
        rs.close();
        ps.close();
        c.close();
        
        return retval;
    }
    
    public ArrayList<AppointmentBean> findAppointmentsByService(ServiceBean service) throws SQLException {
        ArrayList<AppointmentBean> retval = new ArrayList();
        Connection c = getConnection();        
        PreparedStatement ps = c.prepareStatement("SELECT id,date,status,patientId,clinicId FROM Appointment WHERE serviceId = ?");
        ps.setInt(1,(int) service.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Timestamp date = rs.getTimestamp("date");
            String status = rs.getString("status");
            int id = rs.getInt("id"),
                    patientId = rs.getInt("patientId"),
                    clinicId = rs.getInt("clinicId");

            AppointmentBean tmp = new AppointmentBean(id, date,
                    status,
                    patientDao.findPatientById(patientId),
                    clinicDao.findClinicById(clinicId),
                    service
            );            
            retval.add(tmp);
        }
        
        rs.close();
        ps.close();
        c.close();
        
        return retval;
    }
    
    //Update Functions
    //I dunno...
    public boolean updateAppointment(AppointmentBean appointment) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE Appointment SET date = ?, status = ? WHERE id = ?");
        ps.setTimestamp(1, appointment.getDate());
        ps.setString(2, appointment.getCancellation());
        ps.setInt(3, appointment.getId());
        boolean retval;
        try {
            retval = (ps.executeUpdate() > 0);
        } catch (SQLException e) {            
            
            retval = false;
        }
        ps.close();
        c.close();
        return retval;
    }

    //Delete Functions
    public boolean deleteAppointment(AppointmentBean appointment) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM Appointment WHERE id = ?");
        ps.setInt(1, appointment.getId());
        boolean retval = (ps.executeUpdate() > 0);
        ps.close();
        c.close();
        return retval;
    }

}
