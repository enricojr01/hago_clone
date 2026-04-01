/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.StatusBean;
import com.clone.hago_clone.models.TimeslotBean;
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
public class StatusDAO extends BaseDAO {
    
    public StatusDAO(String url, String username, String password)
            throws ClassNotFoundException {
        super(url, username, password);
    }

    @Override
    protected String createTableStatement() {
        return "CREATE TABLE IF NOT EXISTS Status( \n"
                + "id INT NOT NULL AUTO_INCREMENT,\n"
                + "name VARCHAR(50) NOT NULL,\n"
                + "desc VARCHAR(50) NOT NULL,\n"
                + "PRIMARY KEY (id))";
                
    }

    @Override
    protected String dropTableStatement() {
        return "DROP TABLE Status";
    }
    
    //Create Functions
    public StatusBean createStatus(String name, String desc) throws SQLException {
        StatusBean retval = null;        
        Connection c = getConnection();        
        PreparedStatement ps = c.prepareStatement("INSERT INTO Status (name,desc) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
        
        ps.setString(1,name);
        ps.setString(2,name);
        
        if(ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            int id = rs.getInt(1);
            retval = new StatusBean(id,name,desc);
            rs.close();
        }
        
        ps.close();
        c.close();
        
        
        return retval;
        
    }   
    
    //Read Functions
    public ArrayList<StatusBean> getAllStatus() throws SQLException {
        ArrayList<StatusBean> retval = new ArrayList();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT id, name, desc FROM Status");
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name"),
                   desc = rs.getString("desc");
            
            StatusBean tmp = new StatusBean(id,name,desc);            
            retval.add(tmp);
        }        
        return retval;                
    }
    
    public StatusBean findStatusById(int id) throws SQLException {
        StatusBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT name,desc FROM Status WHERE id = ?");
        ps.setInt(1,id);        
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {                        
            String name = rs.getString("name"),
                   desc = rs.getString("desc");
            retval = new StatusBean(id,name,desc);            
        }
        
        rs.close();
        ps.close();
        c.close();
        return retval;        
    }
    
    //Update Functions
    public boolean updateStatus(StatusBean status) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE Status SET name = ?, desc = ? WHERE id = ?");
        ps.setString(1,status.getName());
        ps.setString(2,status.getDesc());
        ps.setInt(3,status.getId());
        
        boolean retval = (ps.executeUpdate() > 0);
        
        ps.close();
        c.close();
        
        return retval;
        
    }
    //Delete Functions
    public boolean deleteStatus(StatusBean status) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM Status WHERE id = ?");
        ps.setInt(1,status.getId());
        
        boolean retval = (ps.executeUpdate() > 0);
        
        ps.close();
        c.close();
        
        return retval;        
    }

    //Create Functions
    //Read Functions
    //Update Functions
    //Delete Functions
    
}
