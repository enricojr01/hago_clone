/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.ServiceBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class ServiceDAO extends BaseDAO {

	public ServiceDAO(String url, String username, String password) 
			throws ClassNotFoundException {
		super(url, username, password);
	}
	
	@Override
	protected String createTableStatement() {
		return "create table if not exists Service(" +
			"id int not null auto_increment," +
			"name varchar(128) not null," +
			"description varchar(256) not null," +
			"clinic_id int," +
			"PRIMARY KEY (id)," +
			"UNIQUE (name)," +
			"FOREIGN KEY (clinic_id) REFERENCES Clinic(id)" +
		")";
	}

	@Override
	protected String dropTableStatement() {
		return "drop table Service";
	}

	public boolean createServiceTable() throws SQLException {
		return createTable();
	}

	public boolean dropServiceTable() throws SQLException {
		return dropTable();
	}

	public ServiceBean createService(String name, String description) 
			throws SQLException {
		String sqlQuery = 
				"insert into Service (name, description) values (?, ?)";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(
				sqlQuery, 
				Statement.RETURN_GENERATED_KEYS
		);
		ps.setString(1, name);
		ps.setString(2, description);
		ps.executeUpdate();
		ResultSet results = ps.getGeneratedKeys();
		if (results.first()) {
			ServiceBean sb = new ServiceBean(
					results.getLong("id"),
					name,
					description
			);
			// NOTE: there's gotta be a nicer way to do this
			ps.close();
			c.close();

			return sb;
		} else {
			ps.close();
			c.close();
			
			return null;
		}
	}

	public int updateService(ServiceBean sb) throws SQLException {
		String sqlQuery = "update Service set name=?, description=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);

		ps.setString(1, sb.getName());
		ps.setString(2, sb.getDescription());
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}
}
