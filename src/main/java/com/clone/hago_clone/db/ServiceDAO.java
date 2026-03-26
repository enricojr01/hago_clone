/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class ServiceDAO {
	private final BaseDAO db;

	public ServiceDAO(BaseDAO db) {
		this.db = db;
	}

	public boolean createServiceTable() throws SQLException {
		String createStatement = 
				"create table Service(" +
					"id int not null auto_increment," +
					"name varchar(128) not null," +
					"description varchar(256) not null," +
					"clinic_id int not null," +
					"PRIMARY KEY (id)," +
					"UNIQUE (name)," +
					"FOREIGN KEY (clinic_id) REFERENCES Clinic(id)" +
				")";
		
		Connection c = db.getConnection();
		Statement s = c.createStatement();
		s.executeUpdate(createStatement);

		s.close();
		c.close();
		
		return true;
	}

	public boolean dropServiceTable() throws SQLException {
		String dropStatement = "drop table Service";
		Connection c = db.getConnection();
		Statement s = c.createStatement();

		s.executeUpdate(dropStatement);
		s.close();			
		c.close();

		return true;
	}
		
}
