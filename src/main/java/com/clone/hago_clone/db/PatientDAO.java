/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.PatientBean;
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
public class PatientDAO extends BaseDAO {

    public PatientDAO(String url, String username, String password)
            throws ClassNotFoundException {
        super(url, username, password);
    }

    /**
     * Returns a String containing the SQL needed to create the table for this
     * model in the database. Should not need to be called manually in the
     * client code.
     *
     * @returns A string containing SQL (MariaDB / MySQL dialect).
     */
    @Override
    protected String createTableStatement() {
        return "CREATE TABLE IF NOT EXISTS Patient (\n" +
                "id INT NOT NULL AUTO_INCREMENT,\n" +
                "name VARCHAR(50) NOT NULL,\n" +
                "email VARCHAR(50) NOT NULL UNIQUE,\n" +
                "pword VARCHAR(16) NOT NULL,\n" +
                "PRIMARY KEY (id))";
    }

    /**
     * Returns a String containing the SQL needed to drop the table in the
     * database. Should not need to be called in client code.
     *
     * @returns A string containing SQL (MariaDB / MySQL dialect)
     */
    @Override
    protected String dropTableStatement() {
        return "DROP TABLE Patient";
    }
    
    public boolean createPatientTable() throws SQLException {
        return createTable();
    }

    public boolean dropPatientTable() throws SQLException {
        return dropTable();
    }
    
    /**
     * Inserts a new row in Patient
     * @param name
     * @param email 
     * @param password A plaintext version to be encrypted with bcrypt
     * @return A PatientBean object, or null
     * @throws SQLException 
     */    
    public PatientBean createPatient(String name,String email,String password) throws SQLException {
        PatientBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Patient (name,email,pword) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,name);
        ps.setString(2,email);        
        ps.setString(3, password);        
        if(ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            retval = new PatientBean(id,name,email,password);
            rs.close();
        }
        
                
        ps.close();
        c.close();
        
        return retval;            
    }
    
    
    /**
     * Collect all Patients
     * @return An ArrayList of all Patients, can be empty
     * @throws SQLException 
     */
    public ArrayList<PatientBean> getAllPatients() throws SQLException {        
        ArrayList<PatientBean> retval = new ArrayList<PatientBean>();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT id, name, email, pword FROM Patients");
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name"),
                   email = rs.getString("email"),
                   pword = rs.getString("pword");
            PatientBean tmp = new PatientBean(id,name,email,pword);
            retval.add(tmp);
        }
        
        return retval;
    }
    
    
    /**
     * 
     * @param id      
     * @return A PatientBean, or null
     * @throws SQLException 
     */
    public PatientBean findPatientById(int id) throws SQLException {
        PatientBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT name,email,pword FROM Patient WHERE id = ?");                
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            String name = rs.getString("name"),
                   email = rs.getString("email"),
                   pword = rs.getString("pword");
            
            retval = new PatientBean(id,name,email,pword);
        }
        
        rs.close();
        ps.close();
        c.close();        
        return retval;
    }
    
    
    /**
     * Returns all Patients that match the fuzzy string given
     * @param name
     * @return An ArrayList of PatientBeans, can be empty
     * @throws SQLException 
     */
    public ArrayList<PatientBean>findPatientsByName(String name) throws SQLException {
        ArrayList<PatientBean> retval = new ArrayList();        
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT id,name,email,pword FROM Patient WHERE name LIKE ?");
        ps.setString(1,'%' + name + '%');
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int _id = rs.getInt("id");
            String _name = rs.getString("name"),
                   _email = rs.getString("email"),
                   _pword = rs.getString("pword");
            
            PatientBean tmp = new PatientBean(_id,_name,_email,_pword);            
            retval.add(tmp);
        }        
        rs.close();
        ps.close();
        c.close();                
        return retval;
    }
    
    
    //Update Functions
    /**
     * Updates a Patient's record
     * @param patient 
     * @return True on success, False on failure
     * @throws SQLException 
     */
    public boolean updatePatientData(PatientBean patient) throws SQLException {        
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE Patient SET name = ?, email = ?, pword = ? WHERE id = ?");        
        ps.setString(1,patient.getName());
        ps.setString(2,patient.getEmail());
        ps.setString(3,patient.getPword());        
        ps.setInt(4, patient.getId());
        boolean retval;
        
        try {
            retval = (ps.executeUpdate() > 0);
        } catch(SQLException e) {
            retval = false;
        } 
        
        ps.close();
        c.close();
        
        return retval;
    }
    
    
    
    //Delete Functions    
    /**
     * 
     * @param patient
     * @return True on success, False on failure
     * @throws SQLException 
     */
    public boolean deletePatient(PatientBean patient) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM Patient WHERE id = ?");               
        ps.setInt(1, patient.getId());
        boolean retval = (ps.executeUpdate() > 0);
        ps.close();
        c.close();
        return retval;
    }
    
        
    /** 
     * Returns a PatientBean if the credentials provided belong to any user     
     * @param password is the patients password in plaintext
     * @Returns A PatientBean object, or null    
    */
    public PatientBean validateCredentials(String email,String password) 
            throws SQLException
    {
        PatientBean retval = null;
        Connection c = getConnection();        
        PreparedStatement ps = c.prepareStatement("SELECT id,email,name,pword FROM Patient WHERE (email = ? AND  pword = ?)");        
        ps.setString(1,email);
        ps.setString(2,password); 
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            int _id = rs.getInt("id");
            String _email = rs.getString("email"),
                   _name = rs.getString("name"),
                   _pword = rs.getString("pword");  
            retval = new PatientBean(_id,_name,_pword,_email);                        
        }
        rs.close();
        ps.close();
        c.close();        
        return retval;        
    }                                   
}