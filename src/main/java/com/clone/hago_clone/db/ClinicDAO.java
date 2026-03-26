/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.ClinicBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class ClinicDAO {
	private final BaseDAO db;	

	public ClinicDAO(BaseDAO db) {
		this.db = db;
	}

	public boolean createClinicTable() throws SQLException {
		String createStatement = 
				"create table Clinic (" +
					"id int not null auto_increment," +
					"name varchar(64) not null," +
					"address varchar(256) not null," +
					"PRIMARY KEY (id)," +
					"UNIQUE (name, address)" +
				")";
		try(Connection c = db.getConnection()) {
			Statement s = c.createStatement();
			s.executeUpdate(createStatement);

			s.close();
			c.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public boolean dropClinicTable() throws SQLException {
		try (Connection c = db.getConnection()) {
			Statement ps = c.createStatement();
			ps.execute("drop table Clinic");

			ps.close();
			c.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ClinicBean createClinic(String name, String address) 
			throws SQLException 
	{
		String insertStatement = 
				"insert into Clinic (name, address) " + 
				"values (?, ?)";
		try (Connection c = db.getConnection()) {
			PreparedStatement ps = c.prepareStatement(
					insertStatement, 
					Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, name);
			ps.setString(2, address);
			ps.executeUpdate(	);
			ResultSet r = ps.getGeneratedKeys();
			if (r.next()) {
				long id = r.getLong(1);
				ClinicBean cb = new ClinicBean(
						id,
						name,
						address
				);
				
				ps.close();
				c.close();
				
				return cb;
			} else {
				ps.close();
				c.close();

				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
