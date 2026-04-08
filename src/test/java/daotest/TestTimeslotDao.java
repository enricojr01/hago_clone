/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.TimeslotDAO;
import com.clone.hago_clone.models.TimeslotBean;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author anonymous
 */
public class TestTimeslotDao {
private final String url = "jdbc:mysql://localhost:3306/javaclass_test",
            uname = "root",
            pword = "";

    public TimeslotDAO createBase() {
        try {
            TimeslotDAO cd = new TimeslotDAO(url, uname, pword);
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
            TimeslotDAO db = createBase();
            db.createTimeslotTable();
        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @AfterEach
    public void finiPatientDB() {
        try {
            TimeslotDAO db = createBase();
            db.dropTimeslotTable();
        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }        
    }
    
    @Test
    public void createTimeslot() {
        TimeslotDAO db = createBase();
        try {
            TimeslotBean timeslot = db.createTimeslot(
                    Time.valueOf(LocalTime.of(8, 30, 0)),
                    Time.valueOf(LocalTime.of(10, 0, 0)),
                        10);
                        
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());            
        }
    }
    
    
    @Test
    public void updateTimeslot() {            
        TimeslotDAO db = createBase();
        try {
            TimeslotBean a = db.createTimeslot(
                    Time.valueOf(LocalTime.of(8, 30, 0)),
                    Time.valueOf(LocalTime.of(10, 0, 0)),
                        10);
            
            a.setCapacity(20);
            assertTrue(db.updateTimeslot(a));
            
            TimeslotBean b = db.findTimeslotById(a.getId());
            assertEquals(20,b.getCapacity());
            
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    
    @Test
    public void deleteTimeslot() {
        TimeslotDAO db = createBase();
        try {
            TimeslotBean a = db.createTimeslot(
                    Time.valueOf(LocalTime.of(8, 30, 0)),
                    Time.valueOf(LocalTime.of(10, 0, 0)),
                        10);
            
            TimeslotBean b = db.createTimeslot(
                    Time.valueOf(LocalTime.of(8, 30, 0)),
                    Time.valueOf(LocalTime.of(10, 0, 0)),
                        10);
            
            assertTrue(db.deleteTimeslot(b));            
            
            
                        
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());            
        }        
    }
    
    
    
    

}
