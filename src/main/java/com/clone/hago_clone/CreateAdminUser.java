/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone;

import com.clone.hago_clone.db.EmployeeDAO;
import java.sql.SQLException;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class CreateAdminUser {

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
			EmployeeDAO ed = createDAO();
                        ed.createEmployeeTable();
			ed.addEmployee(
					"superadmin", 
					"sadmin", 
					"sadmin", 
					"123456"
			);
			System.err.println("Created new admin user");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
