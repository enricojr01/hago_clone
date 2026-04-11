/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.AppointmentDAO;
import com.clone.hago_clone.db.ClinicDAO;
import com.clone.hago_clone.db.ClinicServiceDAO;
import com.clone.hago_clone.db.PatientDAO;
import com.clone.hago_clone.db.ServiceDAO;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicServiceBean;
import com.clone.hago_clone.models.PatientBean;
import com.clone.hago_clone.models.ServiceBean;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.time.*;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author anonymous
 */
public class TestAppointmentDao {

    private final String url = "jdbc:mysql://localhost:3306/javaclass_test",
            uname = "root",
            pword = "";

    private final TestPatientDao tpd = new TestPatientDao();
    private final TestClinicDao tcd = new TestClinicDao();
    private final TestServiceDao tsd = new TestServiceDao();
    private final TestClinicServiceDao tcsd = new TestClinicServiceDao();

    public AppointmentDAO createBase() {
        try {
            AppointmentDAO cd = new AppointmentDAO(url, uname, pword);
            return cd;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail(e.toString());
        }
        return null;
    }

    @BeforeEach
    public void initPatientDB() {
        try {
            AppointmentDAO app = createBase();
            PatientDAO patient = tpd.createBase();
            ClinicDAO clinic = tcd.createBase();
            ServiceDAO service = tsd.createBase();
            ClinicServiceDAO cs = tcsd.createBase();

            patient.createPatientTable();
            clinic.createClinicTable();
            service.createServiceTable();
            cs.createClinicServiceTable();
            app.createAppointmentTable();

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @AfterEach
    public void finiPatientDB() {
        try {
            AppointmentDAO app = createBase();
            PatientDAO patient = tpd.createBase();
            ClinicDAO clinic = tcd.createBase();
            ServiceDAO service = tsd.createBase();
            ClinicServiceDAO cs = tcsd.createBase();

            app.dropAppointmentTable();
            cs.dropClinicServiceTable();
            service.dropServiceTable();
            clinic.dropClinicTable();
            patient.dropPatientTable();

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void testCreateAppointment() {
        AppointmentDAO app = createBase();
        PatientDAO patient = tpd.createBase();
        ClinicDAO clinic = tcd.createBase();
        ServiceDAO service = tsd.createBase();
        ClinicServiceDAO cs = tcsd.createBase();
        try {
            ClinicBean cb = clinic.createClinic("Clinic Name", "Address");
            ServiceBean sb = service.createService("Service Name", "Description");
            ClinicServiceBean csb = cs.createClinicService(cb, sb);
            PatientBean pb = patient.createPatient("Patient Name", "Email", "Password");
            AppointmentBean ab = app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 2, 8, 30)),
                    pb, cb, sb);

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void findAppointmentById() {
        AppointmentDAO app = createBase();
        PatientDAO patient = tpd.createBase();
        ClinicDAO clinic = tcd.createBase();
        ServiceDAO service = tsd.createBase();
        ClinicServiceDAO cs = tcsd.createBase();
        try {
            ClinicBean cb = clinic.createClinic("Clinic Name", "Address");
            ServiceBean sb = service.createService("Service Name", "Description");
            ClinicServiceBean csb = cs.createClinicService(cb, sb);
            PatientBean pb = patient.createPatient("Patient Name", "Email", "Password");
            AppointmentBean ab = app.createAppointment(new Timestamp(1000), pb, cb, sb);
            app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 2, 8, 30)),
                    pb, cb, sb);
            app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 6, 8, 30)),
                    pb, cb, sb);

            AppointmentBean bb = app.findAppointmentById(ab.getId());
            assertEquals(ab.getId(), bb.getId());

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }

    }

    @Test
    public void findAppointmentsByPatient() {
        AppointmentDAO app = createBase();
        PatientDAO patient = tpd.createBase();
        ClinicDAO clinic = tcd.createBase();
        ServiceDAO service = tsd.createBase();
        ClinicServiceDAO cs = tcsd.createBase();
        try {
            ClinicBean cb = clinic.createClinic("Clinic Name", "Address");
            ServiceBean sb = service.createService("Service Name", "Description");
            ClinicServiceBean csb = cs.createClinicService(cb, sb);
            PatientBean pb = patient.createPatient("Patient Name", "Email", "Password");
            AppointmentBean ab = app.createAppointment(new Timestamp(1000), pb, cb, sb);
            app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 2, 8, 30)),
                    pb, cb, sb);
            app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 6, 8, 30)),
                    pb, cb, sb);

            ArrayList<AppointmentBean> tmp = app.findAppointmentsByPatient(pb);
            assertEquals(3, tmp.size());

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void findAppointmentsByClinic() {
        AppointmentDAO app = createBase();
        PatientDAO patient = tpd.createBase();
        ClinicDAO clinic = tcd.createBase();
        ServiceDAO service = tsd.createBase();
        ClinicServiceDAO cs = tcsd.createBase();
        try {
            ClinicBean cb = clinic.createClinic("Clinic Name", "Address");
            ServiceBean sb = service.createService("Service Name", "Description");
            ClinicServiceBean csb = cs.createClinicService(cb, sb);
            PatientBean pb = patient.createPatient("Patient Name", "Email", "Password");
            AppointmentBean ab = app.createAppointment(new Timestamp(1000), pb, cb, sb);
            app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 2, 8, 30)),
                    pb, cb, sb);
            app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 6, 8, 30)),
                    pb, cb, sb);

            ArrayList<AppointmentBean> tmp = app.findAppointmentsByClinic(cb);
            assertEquals(3, tmp.size());

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void findAppointmentsByService() {
        AppointmentDAO app = createBase();
        PatientDAO patient = tpd.createBase();
        ClinicDAO clinic = tcd.createBase();
        ServiceDAO service = tsd.createBase();
        ClinicServiceDAO cs = tcsd.createBase();
        try {
            ClinicBean cb = clinic.createClinic("Clinic Name", "Address");
            ServiceBean sb = service.createService("Service Name", "Description");
            ClinicServiceBean csb = cs.createClinicService(cb, sb);
            PatientBean pb = patient.createPatient("Patient Name", "Email", "Password");
            AppointmentBean ab = app.createAppointment(new Timestamp(1000), pb, cb, sb);
            app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 4, 8, 30)),
                    pb, cb, sb);
            app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 8, 8, 30)),
                    pb, cb, sb);

            ArrayList<AppointmentBean> tmp = app.findAppointmentsByService(sb);
            assertEquals(3, tmp.size());

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void updateAppointment() {
        AppointmentDAO app = createBase();
        PatientDAO patient = tpd.createBase();
        ClinicDAO clinic = tcd.createBase();
        ServiceDAO service = tsd.createBase();
        ClinicServiceDAO cs = tcsd.createBase();
        try {
            ClinicBean cb = clinic.createClinic("Clinic Name", "Address");
            ServiceBean sb = service.createService("Service Name", "Description");
            ClinicServiceBean csb = cs.createClinicService(cb, sb);
            PatientBean pb = patient.createPatient("Patient Name", "Email", "Password");
            AppointmentBean ab = app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 2, 8, 30)),
                    pb, cb, sb);
            ab.setCancellation("CONFIRMED");
            assertTrue(app.updateAppointment(ab));
            AppointmentBean bb = app.findAppointmentById(ab.getId());
            assertEquals("CONFIRMED", bb.getCancellation());

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void updateAppointmentFail() {
        AppointmentDAO app = createBase();
        PatientDAO patient = tpd.createBase();
        ClinicDAO clinic = tcd.createBase();
        ServiceDAO service = tsd.createBase();
        ClinicServiceDAO cs = tcsd.createBase();
        try {
            ClinicBean cb = clinic.createClinic("Clinic Name", "Address");
            ServiceBean sb = service.createService("Service Name", "Description");
            ClinicServiceBean csb = cs.createClinicService(cb, sb);
            PatientBean pb = patient.createPatient("Patient Name", "Email", "Password");
            AppointmentBean ab = app.createAppointment(
                    Timestamp.valueOf(LocalDateTime.of(2026, Month.FEBRUARY, 2, 8, 30)),
                    pb, cb, sb);
            ab.setCancellation("MADE UP STATUS");
            assertFalse(app.updateAppointment(ab));

        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

}
