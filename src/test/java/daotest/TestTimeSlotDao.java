/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.TimeSlotDAO;
import com.clone.hago_clone.models.TimeSlotBean;
import java.sql.SQLException;
import java.time.LocalTime;
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
public class TestTimeSlotDao {
	public TimeSlotDAO createBase() {
		try {
			TimeSlotDAO tsd = new TimeSlotDAO(
					"jdbc:mysql://localhost:3306/javaclass_test",
					"root",
					""
			);
			return tsd;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		return null;
	}	

	@BeforeEach
	public void createDatabaseTable() {
		try {
			TimeSlotDAO tsd = createBase();
			tsd.createTimeSlotTable();

		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@AfterEach
	public void destroyDatabaseTable() {
		try {
			TimeSlotDAO tsd = createBase();
			tsd.dropTimeSlotTable();
		} catch (SQLException e) {
			
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testCreateTimeSlot() {
		try {
			TimeSlotDAO tsd = createBase();
			LocalTime start = LocalTime.parse("09:00");
			LocalTime end = LocalTime.parse("12:00");
			TimeSlotBean tsb = tsd.createTimeSlot(
					start, 
					end, 
					5
			);
			assertNotNull(tsb);
			assertEquals("09:00", tsb.getStart().toString());
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}	

	@Test
	public void testUpdateTimeSlot() {
		try {
			TimeSlotDAO tsd = createBase();
			LocalTime start = LocalTime.parse("09:00");
			LocalTime end = LocalTime.parse("12:00");
			TimeSlotBean tsb = tsd.createTimeSlot(
					LocalTime.MAX, 
					LocalTime.MIN, 
					0
			);
			tsb.setStart(LocalTime.parse("10:00"));
			int results = tsd.updateTimeSlot(tsb);
			assertEquals(1, results);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testUpdateTimeSlotFail() {
		try {
			TimeSlotDAO tsd = createBase();
			LocalTime start = LocalTime.parse("09:00");
			LocalTime end = LocalTime.parse("12:00");
			TimeSlotBean tsb = new TimeSlotBean(
					1,
					start,
					end,
					5
			);
			tsb.setStart(LocalTime.parse("10:00"));
			int results = tsd.updateTimeSlot(tsb);
			assertEquals(0, results);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteTimeSlotByBean() {
		try {
			TimeSlotDAO tsd = createBase();
			LocalTime start = LocalTime.parse("09:00");
			LocalTime end = LocalTime.parse("12:00");
			TimeSlotBean tsb = tsd.createTimeSlot(
					start,
					end,
					5	
			);
			int results = tsd.updateTimeSlot(tsb);
			assertEquals(1, results);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteTimeSlotByBeanFail() {
		try {
			TimeSlotDAO tsd = createBase();
			LocalTime start = LocalTime.parse("09:00");
			LocalTime end = LocalTime.parse("12:00");
			TimeSlotBean tsb = new TimeSlotBean(
					1,
					start,
					end,
					5
			);
			int results = tsd.deleteTimeSlot(tsb);
			assertEquals(0, results);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteTimeSlotById() {
		try {
			TimeSlotDAO tsd = createBase();
			LocalTime start = LocalTime.parse("09:00");
			LocalTime end = LocalTime.parse("12:00");
			TimeSlotBean tsb = tsd.createTimeSlot(
					start,
					end,
					5	
			);
			int results = tsd.updateTimeSlot(tsb);
			assertEquals(1, results);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testDeleteTimeSlotByIdFail() {
		try {
			TimeSlotDAO tsd = createBase();
			int results = tsd.deleteTimeSlot(5);
			assertEquals(0, results);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
}
