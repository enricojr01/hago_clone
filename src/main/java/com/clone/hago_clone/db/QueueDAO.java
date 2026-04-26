/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.QueueBean;
import com.clone.hago_clone.models.ServiceBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author anonymous
 */
public class QueueDAO extends BaseDAO {
    private ClinicDAO clinics;
    private ServiceDAO services;
    
    
    public QueueDAO(String url, String username, String password)
            throws ClassNotFoundException {
        super(url, username, password);
        this.clinics = new ClinicDAO(url,username,password);
        this.services = new ServiceDAO(url,username,password);        
    }    
    
        @Override
    protected String createTableStatement() {
        return "CREATE TABLE IF NOT EXISTS Queue (\n" +
                "id INT NOT NULL AUTO_INCREMENT,\n" +
                "clinicId INT NOT NULL,\n" +
                "serviceId INT NOT NULL,\n" +
                "capacity INT NOT NULL,\n" +
                "PRIMARY KEY (id),\n" + 
                "FOREIGN KEY (clinicId) REFERENCES Clinic(id),\n" +
                "FOREIGN KEY (serviceId) REFERENCES Service(id))";
        
    }
    
    @Override
    protected String dropTableStatement() {
        return "DROP TABLE IF EXISTS Queue";
    }
        
    public boolean createQueueTable() throws SQLException {
        return createTable();
    }

    public boolean dropQueueTable() throws SQLException {
        return dropTable();
    }        
    
    public QueueBean createNewQueue(ClinicBean clinic,ServiceBean service,int capacity) 
            throws SQLException {
        QueueBean retval = null;
        Connection c = getConnection();        
        PreparedStatement ps = c.prepareStatement("INSERT INTO Queue (clinicId,serviceId,capacity) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, clinic.getId());
        ps.setLong(2, service.getId());
        ps.setInt(3, capacity);
        
        if(ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            long id = rs.getLong(1);
            retval = new QueueBean(id,clinic,service,capacity);
            rs.close();
        }
        
        ps.close();
        c.close();
        
        return retval;
    }
    
    public ArrayList<QueueBean> findQueuesForClinic(ClinicBean clinic) 
            throws SQLException {
        ArrayList<QueueBean> retval = new ArrayList();        
        Connection c = getConnection();                
        PreparedStatement ps = c.prepareStatement("SELECT id, serviceId, capacity FROM Queue WHERE clinicId = ?");
        ps.setLong(1, clinic.getId());
        ResultSet rs = ps.executeQuery();        
        while(rs.next()) {
            QueueBean tmp = new QueueBean(rs.getLong("id"),                    
                                          clinic,
                                          services.findServiceById(rs.getLong("serviceId")),
                                          rs.getInt("capacity")
            );
            retval.add(tmp);
        }
        rs.close();
        ps.close();
        c.close();                
        return retval;        
    }
    
    public ArrayList<QueueBean> findQueuesForClinicExcludePatient(ClinicBean clinic,PatientBean patient) 
            throws SQLException {
        ArrayList<QueueBean> retval = new ArrayList();        
        Connection c = getConnection();                
        PreparedStatement ps = c.prepareStatement("SELECT Queue.id, Queue.serviceId, Queue.capacity FROM Queue JOIN PatientQueue ON Queue.id = PatientQueue.queueId WHERE (q.clinicId = ?) AND (PatientQueue.patientId != ?)");
        ps.setLong(1, clinic.getId());
        ps.setLong(2, patient.getId());
        
        ResultSet rs = ps.executeQuery();        
        while(rs.next()) {
            QueueBean tmp = new QueueBean(rs.getLong("Queue.id"),                    
                                          clinic,
                                          services.findServiceById(rs.getLong("Queue.serviceId")),
                                          rs.getInt("Queue.capacity"));
            retval.add(tmp);
        }
        rs.close();
        ps.close();
        c.close();                
        return retval;        
    }    
    
    
    public QueueBean findQueueByClinicAndService(ClinicBean clinic,ServiceBean service) throws SQLException {
        QueueBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM Queue WHERE (clinicId = ?) AND (serviceId = ?)");
        ps.setLong(1, clinic.getId());
        ps.setLong(2, service.getId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            retval = new QueueBean(rs.getLong("id"),
                    clinic,
                    service,
                    rs.getInt("capacity")
            );
        }
        rs.close();
        ps.close();
        c.close();
        return retval;
        
    }
    
    public QueueBean findQueueByClinicAndServiceId(long clinicId,long serviceId) throws SQLException {
        return findQueueByClinicAndService(clinics.findClinicById(clinicId),services.findServiceById(serviceId));
    }

    
    public HashMap<ClinicBean,ArrayList<QueueBean>> findAllQueuesMap()
            throws SQLException {
        HashMap<ClinicBean,ArrayList<QueueBean>> retval = new HashMap();
        ArrayList<ClinicBean> cliniclist = clinics.findAllClinics();
        
        for(ClinicBean c : cliniclist) {
            ArrayList<QueueBean> q = findQueuesForClinic(c);
            retval.put(c, q);
        }
                        
        return retval;
    }
    
    public HashMap<ClinicBean,ArrayList<QueueBean>> findAllQueuesMapExcludePatient(PatientBean patient) throws SQLException {
        HashMap<ClinicBean,ArrayList<QueueBean>> retval = new HashMap();
        ArrayList<ClinicBean> cliniclist = clinics.findAllClinics();
        
        for(ClinicBean c : cliniclist) {
            ArrayList<QueueBean> q = findQueuesForClinicExcludePatient(c,patient);
            if(!q.isEmpty()) {
                retval.put(c,q);
            }
        }
                
        return retval;
    }    
    
    
    
    
}

