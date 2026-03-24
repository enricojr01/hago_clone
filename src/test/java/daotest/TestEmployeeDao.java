/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import com.clone.hago_clone.models.EmployeeBean;
import com.clone.hago_clone.db.BaseDAO;
import com.clone.hago_clone.db.EmployeeDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author Enrico Tuvera Jr
 */
//@Disabled
public class TestEmployeeDao {
	public BaseDAO createBase() {
		System.out.println("CREATING BASEDAO INSTANCE!");
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
	
	@BeforeEach
	public void createDatabaseTable() {
		System.out.println("CREATING DATABASE TABLE!");
		try {
			BaseDAO bd = createBase();
			EmployeeDAO ed = new EmployeeDAO(bd);
			ed.createEmployeeTable();
		} catch (SQLException e) {
			System.out.println("COULDN'T CREATE EMPLOYEE TABLE!");
			e.printStackTrace();
			System.exit(1);
		}
	}

	@AfterEach
	public void destroyDatabaseTable() {
		System.out.println("DROPPING DATABASE TABLE!");
		try {
			BaseDAO bd = createBase();
			EmployeeDAO ed = new EmployeeDAO(bd);
			ed.dropEmployeeTable();
		} catch (SQLException e) {
			System.out.println("COULDN'T DROP EMPLOYEE TABLE!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Test
	public void testCreateEmployee() {
		BaseDAO bd = createBase();
		EmployeeDAO ed = new EmployeeDAO(bd);
		System.out.println("DOES THIS SHOW UP IN THE CONSOLE?!");
		try {
			EmployeeBean r = ed.addEmployee(
					"admin", 
					"enrico", 
					"admin@admin.com", 
					"hunter2"
			);
			assertEquals("enrico", r.getName());
			assertNotNull(r.getId());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testUpdateEmployee() {
		BaseDAO bd = createBase();
		EmployeeDAO ed = new EmployeeDAO(bd);
		try {
			EmployeeBean r = ed.addEmployee(
					"admin", 
					"enrico", 
					"admin@admin.com", 
					"hunter2"
			);
			r.setName("john");
			int result = ed.updateEmployee(r);
			assertEquals(1, result);
		} catch (SQLException e) {
			fail(e.toString());
		}
	} 

	@Test
	public void testDeleteEmployeeByBean() {
		BaseDAO bd = createBase();
		EmployeeDAO ed = new EmployeeDAO(bd);
		try {
			EmployeeBean r = ed.addEmployee(
					"admin", 
					"enrico", 
					"admin@admin.com", 
					"hunter2"
			);
			int result = ed.deleteEmployee(r);
			assertEquals(1, result);
		} catch(SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteEmployeeByID() {
		BaseDAO bd = createBase();
		EmployeeDAO ed = new EmployeeDAO(bd);
		try {
			EmployeeBean r = ed.addEmployee(
					"admin", 
					"enrico", 
					"admin@admin.com", 
					"hunter2"
			);
			int result = ed.deleteEmployee(r);
			assertEquals(999, result);
		} catch(SQLException e) {
			fail(e.toString());
		}
	}
}
//	@Test
//	public void testUpdateEmployee() {
//		try {
//			ed.createEmployeeTable();
//			EmployeeBean r = ed.addEmployee(
//					"admin", 
//					"enrico", 
//					"admin@admin.com", 
//					"hunter2"
//			);
//			r.setName("john wick");
//			int results = ed.updateEmployee(r);
//			assertEquals(999, results);
//			ed.dropEmployeeTable();
//		} catch (SQLException e) {
//			fail(e.toString());	
//		} 	
//	}
//
//	@Test
//	public void testDeleteEmployeeBean() {
//		try {
//			assertEquals(true, ed.createEmployeeTable());
//			EmployeeBean r = ed.addEmployee(
//					"admin", 
//					"enrico", 
//					"admin@admin.com", 
//					"hunter2"
//			);
//			int results = ed.deleteEmployee(r);
//			assertEquals(true, ed.dropEmployeeTable());
//			assertEquals(1, results);
//		} catch (SQLException e) {
//			fail(e.toString());
//		}
//	}

//	@Test
//	public void testDeleteEmployeeById() {
//		try {
//			assertEquals(true, ed.createEmployeeTable());
//			EmployeeBean r = ed.addEmployee(
//					"admin", 
//					"enrico", 
//					"admin@admin.com", 
//					"hunter2"
//			);
//			int results = ed.deleteEmployee(r.getId());
//			assertEquals(true, ed.dropEmployeeTable());
//			assertEquals(1, results);
//		} catch (SQLException e) {
//			fail(e.toString());
//		}
//	}
//}
