/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class DBConnections {
	public static ConnectionDetails prod() {
		return new ConnectionDetails(
				"root",
				"",
				"jdbc:mysql://localhost:3306/javaclass"
		);
	}	

	public static ConnectionDetails test() {
		return new ConnectionDetails(
				"root",
				"",
				"jdbc:mysql://localhost:3306/javaclass_test"
		);
	}
}
