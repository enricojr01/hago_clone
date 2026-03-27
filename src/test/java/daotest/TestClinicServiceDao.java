/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.ClinicServiceDAO;
import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicServiceBean;
import com.clone.hago_clone.models.ServiceBean;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TestClinicServiceDao {
	private final TestServiceDao tsd = new TestServiceDao();
	private final TestClinicDao tcd = new TestClinicDao();
	
	public ClinicServiceDAO createBase() {
		try {
			ClinicServiceDAO csd = new ClinicServiceDAO(
					"jdbc:mysql://localhost:3306/javaclass_test",
					"root",
					""
			);
			return csd;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		return null;
	}

	@BeforeEach
	public void createDatabaseTable() {
		try {
			ClinicServiceDAO csd = createBase();
			ClinicDAO cd = tcd.createBase();	
			ServiceDAO sd = tsd.createBase();
		
			cd.createClinicTable();
			sd.createServiceTable();
			csd.createClinicServiceTable();
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	// NOTE: drop order should be ClinicServiceTable, Service, Clinic
	@AfterEach
	public void destroyDatabaseTable() {
		try {
			ClinicServiceDAO csd = createBase();
			ClinicDAO cd = tcd.createBase();	
			ServiceDAO sd = tsd.createBase();
			
			csd.dropClinicServiceTable();
			sd.dropServiceTable();
			cd.dropClinicTable();
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testCreateClinicService() {
		try {
			ClinicServiceDAO csd = createBase();
			ServiceDAO sd = tsd.createBase();
			ClinicDAO cd = tcd.createBase();

			ClinicBean cb = cd.createClinic("testClinic", "a");
			ServiceBean sb = sd.createService("testService", "b");
			ClinicServiceBean csb = csd.createClinicService(cb, sb);
			assertNotNull(csb);
			assertEquals("testClinic", csb.getClinic().getName());
		} catch (SQLException e) {
			e.printStackTrace();	
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteClinicServiceByBean() {
		try {
			ClinicServiceDAO csd = createBase();
			ServiceDAO sd = tsd.createBase();
			ClinicDAO cd = tcd.createBase();

			ClinicBean cb = cd.createClinic("testClinic", "a");
			ServiceBean sb = sd.createService("testService", "b");
			ClinicServiceBean csb = csd.createClinicService(cb, sb);

			int result = csd.deleteClinicService(csb);
			assertEquals(1, result);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteClinicServiceByBeanFail() {
		try {
			ClinicServiceDAO csd = createBase();
			ServiceDAO sd = tsd.createBase();
			ClinicDAO cd = tcd.createBase();

			ClinicBean cb = cd.createClinic("testClinic", "a");
			ServiceBean sb = sd.createService(
					"testService", "b"
			);
			ClinicServiceBean csb = new ClinicServiceBean(21, cb, sb);

			int result = csd.deleteClinicService(csb);

			assertEquals(0, result);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void testDeleteClinicServiceById() {
		try {
			ClinicServiceDAO csd = createBase();
			ServiceDAO sd = tsd.createBase();
			ClinicDAO cd = tcd.createBase();

			ClinicBean cb = cd.createClinic("testClinic", "a");
			ServiceBean sb = sd.createService("testService", "b");
			ClinicServiceBean csb = csd.createClinicService(cb, sb);

			int result = csd.deleteClinicService(csb.getId());

			assertEquals(1, result);
		} catch (SQLException e){
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteClinicServiceByIdFail() {
		try {
			ClinicServiceDAO csd = createBase();
			ServiceDAO sd = tsd.createBase();
			ClinicDAO cd = tcd.createBase();

			ClinicBean cb = cd.createClinic("testClinic", "a");
			ServiceBean sb = sd.createService("testService", "b");
			ClinicServiceBean csb = new ClinicServiceBean(21, cb, sb);

			int result = csd.deleteClinicService(csb);

			assertEquals(0, result);
		} catch (SQLException e){
			e.printStackTrace();
			fail(e.toString());
		}
	}
}
