/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone;

import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.EmployeeDAO;
import com.clone.hago_clone.models.ClinicBean;
import java.sql.SQLException;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class CreateAdminUser {

    private static final String url = "jdbc:mysql://localhost:3306/javaclass";
    private static final String uname = "root";
    private static final String pword = "";

    public static EmployeeDAO createDAO() {
        try {
            return new EmployeeDAO(
                    "jdbc:mysql://localhost:3306/javaclass",
                    "root",
                    ""
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            EmployeeDAO ed = new EmployeeDAO(url, uname, pword);
            ClinicDAO cd = new ClinicDAO(url, uname, pword);
            
            cd.createClinicTable();
            ed.createEmployeeTable();

			ClinicBean cb = cd.createClinic("MASTER CLINIC", "for internal use only");
            ed.addEmployee("superadmin", "sadmin", "sadmin", "123456", cb.getId());
            
            System.out.println("Created new admin user");            
        } catch (ClassNotFoundException e) {            
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
