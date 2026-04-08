/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.PatientDAO;
import com.clone.hago_clone.models.PatientBean;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author anonymous
 */
public class TestPatientDao {

    private final String url = "jdbc:mysql://localhost:3306/javaclass_test",
            uname = "root",
            pword = "";

    public PatientDAO createBase() {
        try {
            PatientDAO cd = new PatientDAO(url, uname, pword);
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
            PatientDAO db = createBase();
            db.createPatientTable();
        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @AfterEach
    public void finiPatientDB() {
        try {
            PatientDAO db = createBase();
            db.dropPatientTable();
        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }        
    }
        
  
    @Test
    public void createPatient() {
        PatientDAO db = createBase();
        try {            
            PatientBean patient = db.createPatient("First","first@email.com","12345");            
            //assertEquals("First",patient.getName());
            //assertEquals("first@gmail.com",patient.getEmail());            
        } catch(SQLException e) {
            System.out.println("createPatient() SQLException");
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void getPatientsById() {
        PatientDAO db = createBase();
        try {
            PatientBean a,b,c,d;
            a = db.createPatient("Peter Jane","peterjane@yahoo.com", "password");
            b = db.createPatient("Peter Lamington","dandruffcaek@hotmail.net", "password");
            c = db.createPatient("Patrick Peter","yes@bbnet.gov.uk","password");
            
            d = db.findPatientById(b.getId());                                                
            assertEquals(b.getId(),d.getId());
            
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }       

    @Test
    public void getPatientsByIdFail() {
        PatientDAO db = createBase();
        try {
            PatientBean a,b,c,d;
            a = db.createPatient("Peter Jane","peterjane@yahoo.com", "password");
            b = db.createPatient("Peter Lamington","dandruffcaek@hotmail.net", "password");
            c = db.createPatient("Patrick Peter","yes@bbnet.gov.uk","password");
            
            d = db.findPatientById(1000);                                                
            assertNull(d);            
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }       
    
    
    @Test
    public void getPatientsByName() {
        PatientDAO db = createBase();
        try {
            PatientBean a,b,c;
            a = db.createPatient("Peter Jane","peterjane@yahoo.com", "password");
            b = db.createPatient("Peter Lamington","dandruffcaek@hotmail.net", "password");
            c = db.createPatient("Patrick Peter","yes@bbnet.gov.uk","password");
            
            ArrayList<PatientBean> tmp = db.findPatientsByName("peter");            
            assertEquals(3,tmp.size());                        
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }        
    }
    
    @Test
    public void getPatientsByNameFail() {
        PatientDAO db = createBase();
        try {
            PatientBean a,b,c;
            a = db.createPatient("Peter Jane","peterjane@yahoo.com", "password");
            b = db.createPatient("Peter Lamington","dandruffcaek@hotmail.net", "password");
            c = db.createPatient("Patrick Peter","yes@bbnet.gov.uk","password");
            
            ArrayList<PatientBean> tmp = db.findPatientsByName("andrew");
            assertEquals(0,tmp.size());
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }        
    }
            
    
    @Test
    public void updatePatient() {
        PatientDAO db = createBase();
        try {
            PatientBean a,b;
            a = db.createPatient("Peter Jane","peterjane@yahoo.com", "password");
            a.setEmail("newemail@gmail.uk");
            //a.setPword("12345");
            assertTrue(db.updatePatientData(a));
            b = db.findPatientById(a.getId());            
            assertEquals("newemail@gmail.uk",b.getEmail());
            
            a.setEmail("another@email.net");
            a.setPword("12345");
            a.setName("even the name");
            assertTrue(db.updatePatientData(a));
            
            b = db.findPatientById(a.getId());            
            assertEquals("another@email.net",b.getEmail());
            assertEquals("12345",b.getPword());
            assertEquals("even the name",b.getName());
            
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }        
    }
    
    @Test
    public void updatePatientFail() {
        PatientDAO db = createBase();
        try {
            PatientBean a,b;
            a = db.createPatient("Peter Jane","peterjane@yahoo.com", "password");
            b = db.createPatient("Also Jane","alsojane@yahoo.com", "password");
            b.setEmail("peterjane@yahoo.com");            
            assertFalse(db.updatePatientData(b));            
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.toString());
        }        
    } 
  
}
