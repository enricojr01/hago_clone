/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.TimeSlotBean;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicTimeSlotBean;
import com.clone.hago_clone.models.TimeSlotBean;
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
public class ClinicTimeSlotDAO extends BaseDAO {

    private ClinicDAO clinics;
    private TimeSlotDAO timeslots;

    public ClinicTimeSlotDAO(String url, String username, String password)
            throws ClassNotFoundException {
        super(url, username, password);
        this.clinics = new ClinicDAO(url, username, password);
        this.timeslots = new TimeSlotDAO(url, username, password);

    }

    @Override
    protected String createTableStatement() {
        return "CREATE TABLE IF NOT EXISTS ClinicTimeSlot(\n"
                + "id INT NOT NULL AUTO_INCREMENT,\n"
                + "clinicId INT NOT NULL,\n"
                + "timeslotId INT NOT NULL,\n"
                + "PRIMARY KEY (id),\n"
                + "FOREIGN KEY (clinicId) REFERENCES Clinic(id),\n"
                + "FOREIGN KEY (timeslotId) REFERENCES TimeSlot(id))";
    }

    @Override
    protected String dropTableStatement() {
        return "DROP TABLE IF EXISTS ClinicTimeSlot";
    }
    
    public boolean createClinicTimeSlotTable() throws SQLException {
        return createTable();
    }
    
    public boolean dropClinicTimeSlotTable() throws SQLException {
        return dropTable();
    }
    

    //Create Functions
    public ClinicTimeSlotBean createClinicTimeSlot(ClinicBean clinic, TimeSlotBean timeslot) throws SQLException {        
        ClinicTimeSlotBean retval = null;

        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO ClinicTimeSlot (clinicId,timeslotId) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, (int) clinic.getId());

        ps.setLong(2, timeslot.getId());

        if (ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            long id = rs.getLong(1);
            retval = new ClinicTimeSlotBean(id, clinic, timeslot);
            rs.close();
        }
        ps.close();
        c.close();
        return retval;
    }

    //Read Functions
    public ArrayList<ClinicTimeSlotBean> findAllClinicTimeSlots() throws SQLException {
        ArrayList<ClinicTimeSlotBean> retval = new ArrayList();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT id, clinicId,timeslotId FROM ClinicTimeSlot");
        while (rs.next()) {
            long id = rs.getLong("id"),
                    clinicId = rs.getLong("clinicId"),
                    timeslotId = rs.getLong("timeslotId");
            ClinicTimeSlotBean tmp = new ClinicTimeSlotBean(id,
                    clinics.findClinicById(clinicId),
                    timeslots.findTimeSlotById(timeslotId));
            
            retval.add(tmp);
        }
        rs.close();
        s.close();
        c.close();
        return retval;
    }

    public ArrayList<ClinicTimeSlotBean> findClinicTimeSlotsByClinicId(ClinicBean clinic) throws SQLException {
        ArrayList<ClinicTimeSlotBean> retval = new ArrayList();
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM ClinicTimeSlot WHERE clinicId = ?");
        ps.setLong(1,clinic.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            ClinicTimeSlotBean tmp = new ClinicTimeSlotBean(rs.getLong("id"),
                    clinic,
                    timeslots.findTimeSlotById(rs.getLong("timeslotId")));                    
            
            retval.add(tmp);
        }
        rs.close();
        ps.close();
        c.close();
        return retval;
    }
    
    //Update Functions
    //i dont know tbh....
    
    //Delete Functions
    public boolean deleteClinicTimeSlot(ClinicTimeSlotBean clinicTimeslot) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM ClinicTimeSlot WHERE id = ?");
        ps.setLong(1, clinicTimeslot.getId());
        boolean retval = (ps.executeUpdate() > 0);
        ps.close();
        c.close();
        return retval;
    }
}
