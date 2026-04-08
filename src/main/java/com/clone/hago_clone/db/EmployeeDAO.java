/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
public class EmployeeDAO extends BaseDAO {

	public EmployeeDAO(String url, String username, String password) 
			throws ClassNotFoundException {
		super(url, username, password);
	}

	@Override
	protected String createTableStatement() {
		return "create table if not exists Employee ("
				+ "id int not null auto_increment,"
				+ "role varchar(64) not null,"
				+ "name varchar(128) not null,"
				+ "email varchar(128) not null,"
				+ "password varchar(128) not null,"
				+ "clinic_id int,"
				+ "PRIMARY KEY (id),"
				+ "UNIQUE (email),"
				+ "FOREIGN KEY (clinic_id) REFERENCES Clinic(id)"
				+ ")";
	}
	
	@Override
	protected String dropTableStatement() {
		return "drop table Employee";
	}
	
	public boolean createEmployeeTable() throws SQLException {
		return createTable();
	}
	
	public boolean dropEmployeeTable() throws SQLException {
		return dropTable();
	}

	/** 
	 * Creates row in Employee table with the provided arguments. If the
	 * operation fails will return null.
	 * 
	 * @param role String, representing the role of the employee.
	 * @param name String, representing employee name.
	 * @param email String, representing work email of the employee.
	 * @param password String, representing password of employee.
	 * @returns EmployeeBean with a valid ID or null.
	 */
	public EmployeeBean addEmployee(
			String role, 
			String name, 
			String email, 
			String password
	) throws SQLException {
		Connection c = getConnection();
		String sql = 
				"insert into Employee (role, name, email, password) " +
				"values (?, ?, ?, ?)";
		String hashedPassword = 
				BCrypt
				.withDefaults()
				.hashToString(12, password.toCharArray());
		PreparedStatement ps = c.prepareStatement(
				sql,
				Statement.RETURN_GENERATED_KEYS
		);
		
		ps.setString(1, role);
		ps.setString(2, name);
		ps.setString(3, email);
		ps.setString(4, hashedPassword);
		
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
					hashedPassword
			);

			result.close();
			ps.close();
			c.close();

			return eb;
		} else {
			result.close();
			ps.close();
			c.close();
			
			return null;
		}
	}

	/** 
	 * Finds all Employees matching the name provided. There may be more than
	 * one employee with the same name. Returns an empty ArrayList<EmployeeBean>
	 * if no matching rows are found.
	 * 
	 * @param name String, representing employee name.
	 * @returns ArrayList<EmployeeBean>
	 * @throws SQLException
	 */
	public ArrayList<EmployeeBean> findEmployeesByName(String name) 
			throws SQLException {
		ArrayList<EmployeeBean> results = new ArrayList<>();
		Connection c = getConnection();
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

		rs.close();
		ps.close();
		c.close();
		return results;
	}
	
	/**
	 * Finds Employee matching the ID provided. IDs are auto_increment and 
	 * primary key within the database, so it should return only one row.
	 * Returns null if there are no matches.
	 * 
	 * @param id long int representing the id number of the Employee.
	 * @returns EmployeeBean
	 * @throws SQLException
	 */
	public EmployeeBean findEmployeeByID(long id) throws SQLException {
		Connection c = getConnection();
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
			
			rs.close();
			ps.close();
			c.close();

			return eb;
		} else {
			return null;
		}
	} 

	/** 
	 * Returns the Employee row matching the given email. Email is unique, so 
	 * this operation will only return one row. Returns null if no rows found.
	 * 
	 * @param email String, representing an employee's email address.
	 * @returns EmployeeBean
	 * @throws SQLException
	 */
	public EmployeeBean findEmployeeByEmail(String email) throws SQLException {
		Connection c = getConnection();
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
			rs.close();
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
	 * @throws SQLException
	 */
	public ArrayList<EmployeeBean> findEmployeesByRole(String role)
			throws SQLException {
		ArrayList<EmployeeBean> results = new ArrayList<>();
		Connection c = getConnection();
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
		rs.close();
		ps.close();
		c.close();
		
		return results;
	}

	/** 
	 * Updates Employee row using the provided EmployeeBean. Returns 0 if update
	 * fails, 1 otherwise.
	 * 
	 * @param eb EmployeeBean, must be valid otherwise function will return 0.
	 * @returns int returns 1 if row was successfully updated, 0 otherwise.
	 * @throws SQLException
	 */
	public int updateEmployee(EmployeeBean eb) throws SQLException {
		Connection c = getConnection();
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

	/** 
	 * Deletes Employee row using the provided EmployeeBean. Returns 0 if delete 
	 * fails, 1 otherwise.
	 * 
	 * @param eb EmployeeBean, must be valid otherwise function will return 0.
	 * @returns int returns 1 if row was successfully deleted, 0 otherwise.
	 * @throws SQLException
	 */
	public int deleteEmployee(EmployeeBean eb) throws SQLException {
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(
				"delete from Employee where id=?"
		);
		ps.setLong(1, eb.getId());

		int result = ps.executeUpdate();

		ps.close();
		c.close();
		
		return result;
	}

	/** 
	 * Deletes Employee row using the provided id. Returns 0 if delete 
	 * fails, 1 otherwise.
	 * 
	 * @param id long int matching target employee id.
	 * @returns int returns 1 if row was successfully deleted, 0 otherwise.
	 * @throws SQLException
	 */
	public int deleteEmployee(long id) throws SQLException {
		Connection c = getConnection();
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
