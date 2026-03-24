
import daotest.TestEmployeeDao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TestRunnerTest {
	public static void main(String[] args) {
		TestEmployeeDao ted = new TestEmployeeDao();
		TestEmployeeDao.createDatabaseTable();
		TestEmployeeDao.destroyDatabaseTable();
	}	
}
