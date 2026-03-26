/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import com.clone.hago_clone.db.BaseDAO;
import com.clone.hago_clone.db.ClinicDAO;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TestClinicDao {
	public BaseDAO createBase() {
		System.out.println("CREATING BASEDAO INSTANCE!");
		try {
			BaseDAO bd = new BaseDAO(
					"jdbc:mysql://localhost:3306/javaclass_test",
					"root",
					""
			);
			return bd;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("IF YOU SEE THIS SOMETHING'S GONE WRONG!");
		return null;
	}

	@BeforeAll
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

	@AfterAll
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
		
	}
}
