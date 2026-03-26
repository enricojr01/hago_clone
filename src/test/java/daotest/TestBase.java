/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.BaseDAO;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TestBase {
	public BaseDAO createBase() {
		try {
			BaseDAO bd = new BaseDAO(
					"jdbc:mysql://localhost:3306/javaclass_test",
					"root",
					""
			);
			return bd;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("IF YOU SEE THIS SOMETHING'S GONE WRONG!");
		return null;
	}
}
