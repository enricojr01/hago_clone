/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.ClinicTimeslotDAO;
import com.clone.hago_clone.db.TimeSlotDAO;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicTimeslotBean;
import com.clone.hago_clone.models.TimeslotBean;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author anonymous
 */
public class TestClinicTimeSlotDao {

    private final String url = "jdbc:mysql://localhost:3306/javaclass_test",
            uname = "root",
            pword = "";

    private TestClinicDao clinicDao = new TestClinicDao();
    private TestTimeSlotDao timeslotDao = new TestTimeSlotDao();

    public ClinicTimeslotDAO createBase() {
        try {
            ClinicTimeslotDAO db = new ClinicTimeslotDAO(url, uname, pword);
            return db;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail(e.toString());
        }
        return null;
    }

    @BeforeEach
    public void initClinicTimeslotDB() {
        try {
            ClinicTimeslotDAO ctd = createBase();
            TimeSlotDAO td = timeslotDao.createBase();
            ClinicDAO cd = clinicDao.createBase();
            td.createTimeslotTable();
            cd.createClinicTable();
            ctd.createClinicTimeslotTable();

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @AfterEach
    public void finiClinicTimeslotDB() {
        try {
            ClinicTimeslotDAO ctd = createBase();
            TimeSlotDAO td = timeslotDao.createBase();
            ClinicDAO cd = clinicDao.createBase();

            ctd.dropClinicTimeslotTable();
            td.dropTimeslotTable();
            cd.dropClinicTable();
        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void createClinicTimeslot() {
        try {
            ClinicTimeslotDAO ctd = createBase();
            TimeSlotDAO td = timeslotDao.createBase();
            ClinicDAO cd = clinicDao.createBase();

            ClinicBean c = cd.createClinic("Clinic Name", "Address");
            TimeSlotBean t = td.createTimeSlot(
                    Time.valueOf(LocalTime.of(9, 0, 0)),
                    Time.valueOf(LocalTime.of(12, 30, 0)),
                    10);

            ctd.createClinicTimeSlot(c, t);

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void deleteClinicTimeslot() {
        try {
            ClinicTimeslotDAO ctd = createBase();
            TimeSlotDAO td = timeslotDao.createBase();
            ClinicDAO cd = clinicDao.createBase();

            ClinicBean c = cd.createClinic("Clinic Name", "Address");
            TimeSlotBean t = td.createTimeSlot(
                    Time.valueOf(LocalTime.of(9, 0, 0)),
                    Time.valueOf(LocalTime.of(12, 30, 0)),
                    10);

            ClinicTimeSlotBean ct = ctd.createClinicTimeSlot(c, t);
            assertTrue(ctd.deleteClinicTimeSlot(ct));

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

}
