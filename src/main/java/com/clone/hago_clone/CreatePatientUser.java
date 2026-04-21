/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone;

import com.clone.hago_clone.db.PatientDAO;
import java.sql.SQLException;

/**
 *
 * @author anonymous
 */
public class CreatePatientUser {
    private static final String url = "jdbc:mysql://localhost:3306/javaclass";
    private static final String uname = "root";
    private static final String pword = "";

 
    public static void main(String[] args) {
        try {
            PatientDAO p = new PatientDAO(url,uname,pword);
            
            p.dropPatientTable();
            p.createPatientTable();
            p.createPatient("Test", "email@email.com", "password");                    
            
        } catch(ClassNotFoundException e) {
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }        
    }
    
}
