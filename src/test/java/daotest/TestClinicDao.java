/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.BaseDAO;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.models.ClinicBean;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TestClinicDao {
	public ClinicDAO createBase() {
		try {
			ClinicDAO cd = new ClinicDAO(
						"jdbc:mysql://localhost:3306/javaclass_test",
						"root",
						""
			);
			return cd;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		return null;
	}

	@BeforeEach
	public void createDatabaseTable() {
		try {
			ClinicDAO cd = createBase();
			cd.createClinicTable();
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@AfterEach
	public void destroyDatabaseTable() {
		try {
			ClinicDAO cd = createBase();
			cd.dropClinicTable();
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testCreateClinic() {
		ClinicDAO cd = createBase();
	
		try {
			ClinicBean cb = cd.createClinic(
					"test clinic 1", 
					"123 Fake St."
			);
			assertEquals("test clinic 1", cb.getName());
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testFindByClinicId() {
		ClinicDAO cd = createBase();

		try {
			ClinicBean cb1 = cd.createClinic("test1", "address1");
			ClinicBean cb2 = cd.createClinic("test2", "address1");
			ClinicBean cb3 = cd.createClinic("test3", "address2");
	
			ClinicBean res = cd.findClinicById(cb1.getId());
			assertEquals(1, res.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testFindClinicByName() {
		ClinicDAO cd = createBase();
		
		try {
			ClinicBean cb1 = cd.createClinic("test1", "address1");
			ClinicBean cb2 = cd.createClinic("test2", "address1");
			ClinicBean cb3 = cd.createClinic("test3", "address2");

			ClinicBean res = cd.findClinicByName(cb2.getName());
			assertEquals(cb2.getName(), res.getName());
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testFindClinicByAddress() {
		ClinicDAO cd = createBase();

		try {
			ClinicBean cb1 = cd.createClinic("test1", "address1");
			ClinicBean cb2 = cd.createClinic("test2", "address1");
			ClinicBean cb3 = cd.createClinic("test3", "address2");
			
			ArrayList<ClinicBean> res = cd.findClinicByAddress("add");
			assertEquals(3, res.size());
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testUpdateClinic() {
		ClinicDAO cd = createBase();

		try {
			ClinicBean cb = cd.createClinic(
					"test 1", 
					"123 Fake St."
			);
			cb.setName("modified test 1");
			int results = cd.updateClinic(cb);
			assertEquals(1, results);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteClinicWithBean() {
		ClinicDAO cd = createBase();

		try {
			ClinicBean cb = cd.createClinic(
					"test 1", 
					"123 Fake St."
			);
			int results = cd.deleteClinic(cb);	
			assertEquals(1, results);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteClinicWithId() {
		ClinicDAO cd = createBase();

		try {
			ClinicBean cb = cd.createClinic(
					"test 1", 
					"123 Fake St."
			);
			int results = cd.deleteClinic(cb.getId());	
			assertEquals(1, results);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
}
