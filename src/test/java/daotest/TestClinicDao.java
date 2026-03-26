/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.BaseDAO;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.models.ClinicBean;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TestClinicDao extends TestBase {

	@BeforeEach
	public void createDatabaseTable() {
		try {
			BaseDAO bd = createBase();
			ClinicDAO cd = new ClinicDAO(bd);
			cd.createClinicTable();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@AfterEach
	public void destroyDatabaseTable() {
		try {
			BaseDAO bd = createBase();
			ClinicDAO cd = new ClinicDAO(bd);
			cd.dropClinicTable();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Test
	public void testCreateClinic() {
		BaseDAO bd = createBase();
		ClinicDAO cd = new ClinicDAO(bd);
	
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
	public void testUpdateClinic() {
		BaseDAO bd = createBase();	
		ClinicDAO cd = new ClinicDAO(bd);

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
		BaseDAO bd = createBase();
		ClinicDAO cd = new ClinicDAO(bd);

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
		BaseDAO bd = createBase();
		ClinicDAO cd = new ClinicDAO(bd);

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
