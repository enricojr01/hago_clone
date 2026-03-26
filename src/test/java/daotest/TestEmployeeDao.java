/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import com.clone.hago_clone.models.EmployeeBean;
import com.clone.hago_clone.db.BaseDAO;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.EmployeeDAO;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TestEmployeeDao {
	public EmployeeDAO createBase() {
		try {
			EmployeeDAO ed = new EmployeeDAO(
					"jdbc:mysql://localhost:3306/javaclass_test",
					"root",
					""
			);
			return ed;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	@BeforeEach
	public void createDatabaseTable() {
		try {
			EmployeeDAO ed = createBase();
			TestClinicDao tcd = new TestClinicDao();
			tcd.createDatabaseTable();
			ed.createEmployeeTable();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@AfterEach
	public void destroyDatabaseTable() {
		try {
			EmployeeDAO ed = createBase();
			TestClinicDao tcd = new TestClinicDao();
			ed.dropEmployeeTable();
			tcd.destroyDatabaseTable();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Test
	public void testCreateEmployee() {
		EmployeeDAO ed = createBase();
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
		EmployeeDAO ed = createBase();
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
		EmployeeDAO ed = createBase();
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
		EmployeeDAO ed = createBase();
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
	public void testFindEmployeeByID() {
		EmployeeDAO ed = createBase();
		try {
			EmployeeBean r = ed.addEmployee(
					"admin", 
					"enrico", 
					"admin@admin.com", 
					"hunter2"
			);

			EmployeeBean t = ed.findEmployeeByID(r.getId());
			assertEquals(r.getName(), t.getName());
		} catch(SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFindEmployeeByIDFail() {
		EmployeeDAO ed = createBase();
		try {
			EmployeeBean r = ed.addEmployee(
					"admin", 
					"enrico", 
					"admin@admin.com", 
					"hunter2"
			);

			EmployeeBean t = ed.findEmployeeByID(5);
			assertNull(t);
		} catch(SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFindEmployeesByRole() {
		EmployeeDAO ed = createBase();
		try { 
			ed.addEmployee(
					"admin", 
					"enrico", 
					"admin1@admin.com", 
					"hunter2"
			);
			ed.addEmployee(
					"admin", 
					"john", 
					"admin2@admin.com", 
					"hunter2"
			);
			ed.addEmployee(
					"staff", 
					"jack", 
					"admin3@admin.com", 
					"hunter3"
			);
			ArrayList<EmployeeBean> result = 
					ed.findEmployeesByRole("admin");
			assertEquals(2, result.size());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFindEmployeesByRoleFail() {
		EmployeeDAO ed = createBase();
		try { 
			ed.addEmployee(
					"admin", 
					"enrico", 
					"admin1@admin.com", 
					"hunter2"
			);
			ed.addEmployee(
					"admin", 
					"john", 
					"admin2@admin.com", 
					"hunter2"
			);
			ed.addEmployee(
					"staff", 
					"jack", 
					"admin3@admin.com", 
					"hunter3"
			);
			ArrayList<EmployeeBean> result = 
					ed.findEmployeesByRole("patient");
			assertEquals(0, result.size());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testFindEmployeesByName() {
		EmployeeDAO ed = createBase();
		try {
			ed.addEmployee(
					"admin", 
					"enrico", 
					"enrico@admin.com", 
					"hunter2"
			);
			ed.addEmployee(
					"staff", 
					"enrico", 
					"enrico@staff.com", 
					"hunter2"
			);
			ed.addEmployee(
					"patient", 
					"enrico", 
					"enrico@gmail.com",
					"hunter2"
			);
			ed.addEmployee(
					"staff", 
					"john", 
					"john@staff.com",
					"hunter2"
			);
			ArrayList<EmployeeBean> result = 
					ed.findEmployeesByName("enrico");
			assertEquals(3, result.size());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFindEmployeesByNameFail() {
		EmployeeDAO ed = createBase();
		try {
			ed.addEmployee(
					"admin", 
					"enrico", 
					"enrico@admin.com", 
					"hunter2"
			);
			ed.addEmployee(
					"staff", 
					"enrico", 
					"enrico@staff.com", 
					"hunter2"
			);
			ed.addEmployee(
					"patient", 
					"enrico", 
					"enrico@gmail.com",
					"hunter2"
			);
			ed.addEmployee(
					"staff", 
					"john", 
					"john@staff.com",
					"hunter2"
			);
			ArrayList<EmployeeBean> result = 
					ed.findEmployeesByName("james");
			assertEquals(0, result.size());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testFindEmployeeByEmail() {
		EmployeeDAO ed = createBase();
		try {
			EmployeeBean r = ed.addEmployee(
					"admin", 
					"enrico", 
					"admin@admin.com", 
					"hunter2"
			);

			EmployeeBean t = ed.findEmployeeByEmail(r.getEmail());
			assertEquals(r.getName(), t.getName());
		} catch(SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testFindEmployeeByEmailFail() {
		EmployeeDAO ed = createBase();
		try {
			EmployeeBean r = ed.addEmployee(
					"admin", 
					"enrico", 
					"admin@admin.com", 
					"hunter2"
			);

			EmployeeBean t = ed.findEmployeeByEmail("test@fake.com");
			assertNull(t);
		} catch(SQLException e) {
			fail(e.toString());
		}
	}
}
