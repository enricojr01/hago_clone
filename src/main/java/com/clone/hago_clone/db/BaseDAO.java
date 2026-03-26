/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Enrico Tuvera Jr
 */
public abstract class BaseDAO {
	private String dbURL;
	private String dbUsername;
	private String dbPassword;

	public BaseDAO(String url, String username, String password) 
			throws ClassNotFoundException 
	{
		this.dbURL = url;
		this.dbUsername = username;
		this.dbPassword = password;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw e;
		}
	}

	public Connection getConnection() throws SQLException {
		try {
			return DriverManager.getConnection(
					this.dbURL, 
					this.dbUsername, 
					this.dbPassword
			);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected abstract String createTableStatement();
	protected abstract String dropTableStatement();

	private void executeStatement(String query) throws SQLException {
		Connection c = 	getConnection();
		Statement s = c.createStatement();
		s.executeUpdate(query);
		s.close();
		c.close();
	}

	public boolean createTable() throws SQLException { 
		String stmt = createTableStatement();
		executeStatement(stmt);
		return true;
	}
	
	public boolean dropTable() throws SQLException {
		String stmt = dropTableStatement();
		executeStatement(stmt);
		return true;
	}
}
