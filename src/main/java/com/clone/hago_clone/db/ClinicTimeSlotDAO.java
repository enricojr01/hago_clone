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

    private ClinicDAO clinic;
    private TimeSlotDAO timeslot;

    public ClinicTimeSlotDAO(String url, String username, String password)
            throws ClassNotFoundException {
        super(url, username, password);
        this.clinic = new ClinicDAO(url, username, password);
        this.timeslot = new TimeSlotDAO(url, username, password);

    }

    @Override
    protected String createTableStatement() {
        return "CREATE TABLE IF NOT EXISTS ClinicTimeSlot(\n"
                + "id INT NOT NULL AUTO_INCREMENT,\n"
                + "clinicId INT NOT NULL,\n"
                + "timeslotId INT NOT NULL,\n"
				+ "capacity int not null default 0,"
                + "PRIMARY KEY (id),\n"
                + "FOREIGN KEY (clinicId) REFERENCES Clinic(id),\n"
                + "FOREIGN KEY (timeslotId) REFERENCES TimeSlot(id),\n"
				+ "UNIQUE (clinicId, timeslotId))";
    }

    @Override
    protected String dropTableStatement() {
        return "DROP TABLE ClinicTimeSlot";
    }
    
    public boolean createClinicTimeSlotTable() throws SQLException {
        return createTable();
    }
    
    public boolean dropClinicTimeSlotTable() throws SQLException {
        return dropTable();
    }
    

    //Create Functions
    public ClinicTimeSlotBean createClinicTimeSlot(ClinicBean cb, TimeSlotBean tb) throws SQLException {        
        ClinicTimeSlotBean retval = null;

        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO ClinicTimeSlot (clinicId,timeslotId) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, (int) cb.getId());

        ps.setLong(2, tb.getId());

        if (ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            retval = new ClinicTimeSlotBean(id, cb, tb);
            rs.close();
        }
        ps.close();
        c.close();
        return retval;
    }

    //Read Functions
    public ArrayList<ClinicTimeSlotBean> getAllClinicTimeSlots() throws SQLException {
        ArrayList<ClinicTimeSlotBean> retval = new ArrayList();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM ClinicTimeSlot as cts "
				+ "inner join Clinic as c on cts.clinicId=c.id "
				+ "inner join TimeSlot as ts on cts.timeslotId=ts.id "
				+ "order by ts.start"
		);
        while (rs.next()) {
			int id = rs.getInt("id"),
			clinicId = rs.getInt("clinicId"),
			timeslotId = rs.getInt("timeslotId");
            ClinicTimeSlotBean tmp = new ClinicTimeSlotBean(id,
                    clinic.findClinicById(clinicId),
                    timeslot.findTimeSlotById(timeslotId));
            
            retval.add(tmp);
        }
        rs.close();
        s.close();
        c.close();
        return retval;
    }

    public ClinicTimeSlotBean findClinicTimeSlotById(int id) throws SQLException {
        String sqlQuery = "select * from ClinicTimeSlot as cts "
                + "inner join Clinic as c on cts.clinicId=c.id "
                + "inner join TimeSlot as ts on cts.timeslotId=ts.id "
                + "where cts.id=? order by ts.start";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sqlQuery);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        ClinicBean cb;
        TimeSlotBean tsb;
        ClinicTimeSlotBean ctsb = null;
        while (rs.next()) {
            cb = new ClinicBean(
                    rs.getLong("c.id"),
                    rs.getString("c.name"),
                    rs.getString("c.address")
            );
            tsb = new TimeSlotBean(
                    rs.getLong("ts.id"),
                    rs.getTime("ts.start").toLocalTime(),
                    rs.getTime("ts.end").toLocalTime(),
                    rs.getInt("ts.capacity")
            );
            ctsb = new ClinicTimeSlotBean(
                    rs.getInt("cts.id"),
                    cb,
                    tsb
            );
        }

        return ctsb;
    }

    public ArrayList<ClinicTimeSlotBean> findClinicTimeSlotsByClinicId(ClinicBean cb) 
			throws SQLException {
        String sqlQuery = "select * from ClinicTimeSlot as cts inner join Clinic as c on cts.clinicId=c.id inner join TimeSlot as ts on cts.timeslotId=ts.id where cts.clinicId=?";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sqlQuery);
        ps.setLong(1, cb.getId());
        ResultSet rs = ps.executeQuery();

        ArrayList<ClinicTimeSlotBean> results = new ArrayList<>();
        while (rs.next()) {
            ClinicBean newCb = new ClinicBean(
                    rs.getLong("c.id"),
                    rs.getString("c.name"),
                    rs.getString("c.address")
            );
            TimeSlotBean newTsb = new TimeSlotBean(
                    rs.getLong("ts.id"),
                    rs.getTime("ts.start").toLocalTime(),
                    rs.getTime("ts.end").toLocalTime(),
                    rs.getInt("ts.capacity")
            );

            ClinicTimeSlotBean ctsb = new ClinicTimeSlotBean(
                    rs.getInt("id"),
                    newCb,
                    newTsb
            );

            results.add(ctsb);
        }
        return results;
    }

    public ArrayList<ClinicTimeSlotBean> findClinicTimeSlotsByTimeSlotId(TimeSlotBean ts) 
			throws SQLException {
        String sqlQuery = "select * from ClinicTimeSlot as cts inner join Clinic as c on cts.clinicId=c.id, inner join TimeSlot as ts on cts.timeslotId=ts.id where cts.timeslotId=?";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sqlQuery);
        ps.setLong(1, ts.getId());
        ResultSet rs = ps.executeQuery();

        ArrayList<ClinicTimeSlotBean> results = new ArrayList<>();
        while (rs.next()) {
            ClinicBean newCb = new ClinicBean(
                    rs.getLong("c.id"),
                    rs.getString("c.name"),
                    rs.getString("c.address")
            );
            TimeSlotBean newTsb = new TimeSlotBean(
                    rs.getLong("ts.id"),
                    rs.getTime("ts.start").toLocalTime(),
                    rs.getTime("ts.end").toLocalTime(),
                    rs.getInt("capacity")
            );

            ClinicTimeSlotBean ctsb = new ClinicTimeSlotBean(
                    rs.getInt("id"),
                    newCb,
                    newTsb
            );

            results.add(ctsb);
        }
        return results;
    }
    
    //Update Functions
    //i dont know tbh....
    
    //Delete Functions
    public boolean deleteClinicTimeSlot(ClinicTimeSlotBean ctb) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM ClinicTimeSlot WHERE id = ?");
        ps.setInt(1, ctb.getId());
        boolean retval = (ps.executeUpdate() > 0);
        ps.close();
        c.close();
        return retval;
    }
}
