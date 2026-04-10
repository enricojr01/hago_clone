/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.models.ServiceBean;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TestServiceDao {
	public ServiceDAO createBase() {
		try {
			ServiceDAO ed = new ServiceDAO(
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
			ServiceDAO ed = createBase();
			TestClinicDao tcd = new TestClinicDao();
			tcd.createDatabaseTable();
			ed.createServiceTable();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@AfterEach
	public void destroyDatabaseTable() {
		try {
			ServiceDAO ed = createBase();
			TestClinicDao tcd = new TestClinicDao();
			ed.dropServiceTable();
			tcd.destroyDatabaseTable();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Test
	public void testCreateService() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb = sd.createService(
					"testService", 
					"testDescription"
			);
			assertEquals("testService", sb.getName());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFindServiceById() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb1 = sd.createService(
			"testService1", "testDescription"
			);
			ServiceBean sb2 = sd.createService(
			"testService2", "testDescription"
			);
			ServiceBean sb3 = sd.createService(
			"testService3", "testDescription"
			);
			ServiceBean res = sd.findServiceById(sb2.getId());
			assertEquals(sb2.getId(), res.getId());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFindServiceByIdFail() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean res = sd.findServiceById(999);
			assertNull(res);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFindServiceByName() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb1 = sd.createService(
				"testService1", "testDescription"
			);
			ServiceBean sb2 = sd.createService(
				"testService2", "testDescription"
			);
			ServiceBean sb3 = sd.createService(
				"testService3", "testDescription"
			);
			ServiceBean res = sd.findServiceByName(sb2.getName());
			assertEquals(sb2.getName(), res.getName());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testFindServiceByNameFail() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean res = sd.findServiceByName("apple");
			assertNull(res);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testFindServiceByDescription() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb1 = sd.createService(
				"testService1", "testDescription"
			);
			ServiceBean sb2 = sd.createService(
			"testService2", "testDescription"
			);
			ServiceBean sb3 = sd.createService(
			"testService3", "testDescription"
			);
			ArrayList<ServiceBean> res = 
					sd.findServiceByDescription("test");
			assertEquals(3, res.size());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testFindServiceByDescriptionFail() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb1 = sd.createService(
				"testService1", "testDescription"
			);
			ServiceBean sb2 = sd.createService(
			"testService2", "testDescription"
			);
			ServiceBean sb3 = sd.createService(
			"testService3", "testDescription"
			);
			ArrayList<ServiceBean> res = 
					sd.findServiceByDescription("zebra");
			assertEquals(0, res.size());
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testUpdateService() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb = sd.createService(
					"testService", 
					"testDescription"
			);
			sb.setName("newTestService");
			int result = sd.updateService(sb);
			assertEquals(1, result);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testUpdateServiceFail() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb = new ServiceBean(
					12, 
					"test", 
					"test"
			);
			sb.setName("newTestService");
			int result = sd.updateService(sb);
			assertEquals(0, result);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteServiceByBean() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb = sd.createService(
					"test", 
					"test"
			);

			int result = sd.deleteService(sb);
			assertEquals(1, result);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testDeleteServiceByBeanFail() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb = new ServiceBean(25, "test", "test");

			int result = sd.deleteService(sb);
			assertEquals(0, result);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testDeleteServiceById() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb = sd.createService(
					"test", 
					"test"
			);
			int result = sd.deleteService(sb.getId());
			assertEquals(1, result);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testDeleteServiceByIdFail() {
		try {
			ServiceDAO sd = createBase();
			ServiceBean sb = new ServiceBean(
					99, 
					"test", 
					"test"
			);
			int result = sd.deleteService(sb.getId());
			assertEquals(0, result);
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
}
