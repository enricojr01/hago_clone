/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.TimeslotBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author anonymous
 */
public class TimeslotDAO extends BaseDAO {
    public TimeslotDAO(String url,String username,String password) throws ClassNotFoundException{
        super(url,username,password);
    }
    
    
    @Override
    protected String createTableStatement() {
        return "CREATE TABLE IF NOT EXISTS Timeslot(\n"
                + "id INT NOT NULL AUTO_INCREMENT,\n"
                + "start TIME NOT NULL,\n"
                + "end TIME NOT NULL,\n"
                + "capacity INT NOT NULL,\n"
                + "PRIMARY KEY (id))";
                
    }

    @Override
    protected String dropTableStatement() {
        return "DROP TABLE Timeslot";
    }
    
    public boolean createTimeslotTable() throws SQLException {
        return createTable();
    }

    public boolean dropTimeslotTable() throws SQLException {
        return dropTable();
    }
    
    //Create Functions
    public TimeslotBean createTimeslot(Time start, Time end, int capacity) throws SQLException {                       
        TimeslotBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Timeslot (start,end,capacity) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
        
        ps.setTime(1,start);
        ps.setTime(2,end);
        ps.setInt(3,capacity);
        
        if(ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            retval = new TimeslotBean(id,capacity,start,end);            
            rs.close();
        }
        ps.close();
        c.close();
        
        return retval;
    }    
    //Read Functions
    
    public ArrayList<TimeslotBean> getAllTimeslots() throws SQLException {
        ArrayList<TimeslotBean> retval = new ArrayList();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT id,capacity,start,end FROM Timeslot");
        while(rs.next()) {
            int id = rs.getInt("id"),
                capacity = rs.getInt("capacity");
            Time start = rs.getTime("start"),
                 end = rs.getTime("end");
                                            
            TimeslotBean tmp = new TimeslotBean(id,capacity,start,end);                        
            retval.add(tmp);
        }   
        rs.close();
        s.close();
        c.close();
        return retval;        
        
    }
    
    public TimeslotBean findTimeslotById(int id) throws SQLException {
        TimeslotBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT capacity,start,end FROM Timeslot WHERE id = ?");
        ps.setInt(1,id);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            int capacity = rs.getInt("capacity");
            Time start = rs.getTime("start"),
                 end = rs.getTime("end");        
            
            retval = new TimeslotBean(id,capacity,start,end);
        }
        
        rs.close();
        ps.close();
        c.close();
        
        return retval;                
    }
        
    
    //Update Functions
    public boolean updateTimeslot(TimeslotBean timeslot) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE Timeslot SET capacity = ?, start = ?, end = ? WHERE id = ?");
        
        ps.setInt(1,timeslot.getCapacity());
        ps.setTime(2, timeslot.getStart());
        ps.setTime(3, timeslot.getEnd());
        ps.setInt(4,timeslot.getId());
        
        boolean retval = (ps.executeUpdate() > 0);
        
        ps.close();
        c.close();
        
        return retval;
    }
    
    
    //Delete Functions
    
    public boolean deleteTimeslot(TimeslotBean timeslot) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM Timeslot WHERE id = ?");
        ps.setInt(1,timeslot.getId());
        
        boolean retval = (ps.executeUpdate() > 0);
        
        ps.close();
        c.close();
        
        return retval;        
    }
    
    
}
