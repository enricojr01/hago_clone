/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.EmployeeBean;
import java.io.IOException;
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
public class EmployeeDAO {
	private final BaseDAO db;

	public EmployeeDAO(BaseDAO db) {
		this.db = db;
	}

	public boolean createEmployeeTable() throws SQLException {
		String createStatement = 
				"create table Employee (" + 
						"id int not null auto_increment," +
						"role varchar(64) not null," +
						"name varchar(128) not null," +
						"email varchar(128) not null," +
						"password varchar(128) not null," +
						"PRIMARY KEY (id)," +
						"UNIQUE (email)" +
				")";
		
		try (Connection c = db.getConnection()) {
			Statement s = c.prepareStatement(createStatement);
			s.executeUpdate(createStatement);

			s.close();
			c.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public boolean dropEmployeeTable() throws SQLException {
		try (Connection c = db.getConnection()) {
			Statement ps = c.createStatement();
			ps.execute("drop table Employee");

			ps.close();
			c.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public EmployeeBean addEmployee(
			String role, 
			String name, 
			String email, 
			String password
	) throws SQLException {
		Connection c = db.getConnection();
		String sql = 
				"insert into Employee (role, name, email, password) " +
				"values (?, ?, ?, ?)";
		PreparedStatement ps = c.prepareStatement(
				sql,
				Statement.RETURN_GENERATED_KEYS
		);
		ps.setString(1, role);
		ps.setString(2, name);
		ps.setString(3, email);
		ps.setString(4, password);
		
		// Should return `1` if successful, `0` otherwise.
		ps.executeUpdate();

		ResultSet result = ps.getGeneratedKeys();
		if (result.next()) {
			long id = result.getLong(1);
			EmployeeBean eb = new EmployeeBean(
					id, 
					role, 
					name, 
					email, 
					password
			);

			ps.close();
			c.close();

			return eb;
		} else {
			ps.close();
			c.close();
			return null;
		}
	}

	public ArrayList<EmployeeBean> findEmployeesByName(String name) 
			throws SQLException {
		ArrayList<EmployeeBean> results = new ArrayList<>();
		Connection c = db.getConnection();
		String sql = "select * from Employee where name=?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, name);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			EmployeeBean eb = new EmployeeBean(
					rs.getLong("id"),
					rs.getString("role"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("password")
			);
			results.add(eb);
		}

		ps.close();
		c.close();
		return results;
	}

	public EmployeeBean findEmployeeByID(long id) throws SQLException {
		Connection c = db.getConnection();
		String sql = "select * from Employee where id=?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setLong(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.first()) {
			long eid = rs.getLong("id");
			String role = rs.getString("role");
			String name = rs.getString("name");
			String email = rs.getString("email");
			String password = rs.getString("password");

			EmployeeBean eb = new EmployeeBean(
					eid, 
					role, 
					name, 
					email, 
					password
			);
				
			ps.close();
			c.close();

			return eb;
		} else {
			return null;
		}
	} 

	public EmployeeBean findEmployeeByEmail(String email) throws SQLException {
		Connection c = db.getConnection();
		String sql = "select * from Employee where email=?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, email);

		ResultSet rs = ps.executeQuery();
		if (rs.first()) {
			EmployeeBean eb = new EmployeeBean(
					rs.getLong("id"),
					rs.getString("role"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("password")
			);
			ps.close();
			c.close();
			return eb;
		} else {
			return null;
		}
	}

	/** 
	 * Returns a list of employees with the given role. 
	 * If no employees matching that role are found, returns an empty ArrayList.
	 * <p>
	 * @param role a String representing a given role.
	 * @return an ArrayList<EmployeeBean> with 0 or more items in it. 
	 */
	public ArrayList<EmployeeBean> findEmployeesByRole(String role)
			throws SQLException {
		ArrayList<EmployeeBean> results = new ArrayList<>();
		Connection c = db.getConnection();
		String sql = "select * from Employee where role=?";
		PreparedStatement ps = c.prepareStatement(sql);

		ps.setString(1, role);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			EmployeeBean eb = new EmployeeBean(
					rs.getLong("id"),
					rs.getString("role"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("password")
			);
			results.add(eb);
		} 
		
		ps.close();
		c.close();
		
		return results;
	}

	public int updateEmployee(EmployeeBean eb) throws SQLException {
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(
				"update Employee set " +
				"name=?, email=?, role=?, password=? " + 
				"where id=?"
		);
		ps.setString(1, eb.getName());
		ps.setString(2, eb.getEmail());
		ps.setString(3, eb.getRole());
		ps.setString(4, eb.getPassword());
		ps.setLong(5, eb.getId());

		int results = ps.executeUpdate();

		ps.close();
		c.close();
		
		return results;
	}

	public int deleteEmployee(EmployeeBean eb) throws SQLException {
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(
				"delete from Employee where id=?"
		);
		ps.setLong(1, eb.getId());

		int result = ps.executeUpdate();

		ps.close();
		c.close();
		
		return result;
	}

	public int deleteEmployee(long id) throws SQLException {
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(
				"delete from Employee where id=?"
		);
		ps.setLong(1, id);

		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}
}
