/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.PatientQueueBean;
import com.clone.hago_clone.models.QueueBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//TODO: Get and Filter out all Queues that the patient is already in

/**
 *
 * @author anonymous
 */
public class PatientQueueDAO extends BaseDAO {    
    private PatientDAO patients;
    private QueueDAO queues;
    private ClinicDAO clinics;
    private ServiceDAO services;
    
    public PatientQueueDAO(String url, String username, String password)
            throws ClassNotFoundException {
        super(url, username, password);
        this.queues = new QueueDAO(url,username,password);
        this.patients = new PatientDAO(url,username,password);        
        this.clinics = new ClinicDAO(url,username,password);
        this.services = new ServiceDAO(url,username,password);
        
    }    
    
        @Override
    protected String createTableStatement() {
        return "CREATE TABLE IF NOT EXISTS PatientQueue (\n" +
                "id INT NOT NULL AUTO_INCREMENT,\n" +
                "patientId INT NOT NULL,\n" +
                "queueId INT NOT NULL,\n" +                
                "PRIMARY KEY (id),\n" + 
                "FOREIGN KEY (patientId) REFERENCES Patient(id),\n" +
                "FOREIGN KEY (queueId) REFERENCES Queue(id))";        
    }
    
    @Override
    protected String dropTableStatement() {
        return "DROP TABLE IF EXISTS PatientQueue";
    }
        
    public boolean createPatientQueueTable() throws SQLException {
        return createTable();
    }

    public boolean dropPatientQueueTable() throws SQLException {
        return dropTable();
    }
    
    
    public PatientQueueBean CreatePatientQueue(PatientBean patient,QueueBean queue)             
    throws SQLException {
        PatientQueueBean retval = null;
        Connection c = getConnection();        
        PreparedStatement ps = c.prepareStatement("INSERT INTO PatientQueue (patientId,queueId) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);                
        ps.setLong(1,patient.getId());
        ps.setLong(2,queue.getId());
        
        if(ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            long id = rs.getLong(1);
            retval = new PatientQueueBean(id,patient,queue);
            rs.close();
        }
        ps.close();
        c.close();
        return retval;        
    }
    
    public ArrayList<QueueBean> findAllQueuesByPatient(PatientBean patient) 
    throws SQLException {
        ArrayList<QueueBean> retval = new ArrayList();
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT q.id, q.clinicId, q.serviceId, q.capacity FROM Queue AS q JOIN PatientQueue ON q.id = PatientQueue.queueId WHERE PatientQueue.patientId = ?");
        ps.setLong(1,patient.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            QueueBean tmp = new QueueBean(rs.getLong("q.id"),
                        clinics.findClinicById(rs.getLong("q.clinicId")),
                        services.findServiceById(rs.getLong("q.serviceId")),
                        rs.getInt("q.capacity"));
            retval.add(tmp);
        }
        
        rs.close();
        ps.close();
        c.close();        
        return retval;
    }
}
