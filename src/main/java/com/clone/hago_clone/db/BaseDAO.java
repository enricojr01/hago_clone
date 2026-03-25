/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class BaseDAO {
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
}
