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
public class ClinicDAO extends BaseDAO {

	public ClinicDAO(String url, String username, String password) 
			throws ClassNotFoundException {
		super(url, username, password);
	}


	@Override
	protected String createTableStatement() {
		String createStatement = 
				"create table if not exists Clinic (" +
					"id int not null auto_increment," +
					"name varchar(64) not null," +
					"address varchar(256) not null," +
					"PRIMARY KEY (id)," +
					"UNIQUE (name)" +
				")";
		return createStatement;
	}

	@Override
	protected String dropTableStatement() {
		return "drop table Clinic";
	}

	public boolean createClinicTable() throws SQLException {
		return createTable();
	}

	public boolean dropClinicTable() throws SQLException {
		return dropTable();
	}

	public ClinicBean createClinic(String name, String address) 
			throws SQLException {
		String insertStatement = 
				"insert into Clinic (name, address) " + 
				"values (?, ?)";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(
				insertStatement, 
				Statement.RETURN_GENERATED_KEYS
		);
		ps.setString(1, name);
		ps.setString(2, address);
		ps.executeUpdate(	);
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			long id = rs.getLong(1);
			ClinicBean cb = new ClinicBean(
					id,
					name,
					address
			);
			rs.close();
			ps.close();
			c.close();
			
			return cb;
		} else {
			rs.close();
			ps.close();
			c.close();

			return null;
		}
	}

	public ClinicBean findClinicById(long id) throws SQLException {
		String query = "select * from Clinic where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(query);

		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.first()) {
			ClinicBean cb = new ClinicBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("address")
			);

			rs.close();
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
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(query);

		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		if (rs.first()) {
			ClinicBean cb = new ClinicBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("address")
			);
			rs.close();
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
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		ArrayList<ClinicBean> results = new ArrayList<>();

		ps.setString(1, "%" + address + "%");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ClinicBean cb = new ClinicBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("address")
			);
			results.add(cb);
		}
		
		rs.close();
		ps.close();
		c.close();

		return results;
	}

	public int updateClinic(ClinicBean cb) throws SQLException {
		String updateStatement = 
				"update Clinic set name=?, address=? where id=?";
		Connection c = getConnection();
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
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(deleteStatement);

		ps.setLong(1, cb.getId());
		
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}

	public int deleteClinic(long id) throws SQLException {
		String deleteStatement = "delete from Clinic where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(deleteStatement);

		ps.setLong(1, id);
		
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}
}
