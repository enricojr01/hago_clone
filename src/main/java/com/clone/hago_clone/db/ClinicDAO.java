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
import java.util.ArrayList;

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
					"UNIQUE (name)" +
				")";
		Connection c = db.getConnection();
		Statement s = c.createStatement();
		s.executeUpdate(createStatement);

		s.close();
		c.close();
		
		return true;
	}

	public boolean dropClinicTable() throws SQLException {
		Connection c = db.getConnection();
		Statement ps = c.createStatement();
		ps.execute("drop table Clinic");

		ps.close();
		c.close();
		
		return true;
	}

	public ClinicBean createClinic(String name, String address) 
			throws SQLException {
		String insertStatement = 
				"insert into Clinic (name, address) " + 
				"values (?, ?)";
		Connection c = db.getConnection();
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
	}

	public ClinicBean findClinicById(long id) throws SQLException {
		String query = "select * from Clinic where id=?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(query);

		ps.setLong(1, id);
		ResultSet cursor = ps.executeQuery();

		if (cursor.first()) {
			ClinicBean cb = new ClinicBean(
					cursor.getLong("id"),
					cursor.getString("name"),
					cursor.getString("address")
			);

			cursor.close();
			ps.close();
			c.close();

			return cb;
		} else {
			return null;
		}
	}

	public ClinicBean findClinicByName(String name) 
			throws SQLException {
		String query = "select * from Clinic where name=?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(query);

		ps.setString(1, name);
		ResultSet cursor = ps.executeQuery();

		if (cursor.first()) {
			ClinicBean cb = new ClinicBean(
					cursor.getLong("id"),
					cursor.getString("name"),
					cursor.getString("address")
			);
			cursor.close();
			ps.close();
			c.close();
			return cb;
		} else {
			return null;
		}
	}

	public ArrayList<ClinicBean> findClinicByAddress(String address) 
			throws SQLException {
		String query = "select * from Clinic where address like ?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		ArrayList<ClinicBean> results = new ArrayList<>();

		ps.setString(1, "%" + address + "%");
		ResultSet cursor = ps.executeQuery();

		while (cursor.next()) {
			ClinicBean cb = new ClinicBean(
					cursor.getLong("id"),
					cursor.getString("name"),
					cursor.getString("address")
			);
			results.add(cb);
		}
		
		cursor.close();
		ps.close();
		c.close();

		return results;
	}

	public int updateClinic(ClinicBean cb) throws SQLException {
		String updateStatement = 
				"update Clinic set name=?, address=? where id=?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(updateStatement);
		
		ps.setString(1, cb.getName());
		ps.setString(2, cb.getAddress());
		ps.setLong(3, cb.getId());
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}
	

	public int deleteClinic(ClinicBean cb) throws SQLException {
		String deleteStatement = "delete from Clinic where id=?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(deleteStatement);

		ps.setLong(1, cb.getId());
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}

	public int deleteClinic(long id) throws SQLException {
		String deleteStatement = "delete from Clinic where id=?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(deleteStatement);

		ps.setLong(1, id);
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}
}
