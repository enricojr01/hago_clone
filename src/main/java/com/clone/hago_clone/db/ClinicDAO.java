/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.ClinicBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class ClinicDAO extends BaseDAO {

	public ClinicDAO(String url, String username, String password) 
			throws ClassNotFoundException {
		super(url, username, password);
	}


	/** 
	 * Returns a String containing the SQL needed to create
	 * the table for this model in the database. Should not need
	 * to be called manually in the client code.
	 * @returns A string containing SQL (MariaDB / MySQL dialect).
	 */
	@Override
	protected String createTableStatement() {
		return "create table if not exists Clinic (" 
				+ "id int not null auto_increment,"
				+ "name varchar(64) not null,"
				+ "address varchar(256) not null,"
				+ "PRIMARY KEY (id),"
				+ "UNIQUE (name)"
				+ ")";
	}

	/** 
	 * Returns a String containing the SQL needed to drop the
	 * table in the database. Should not need to be called in client
	 * code.
	 * @returns A string containing SQL (MariaDB / MySQL dialect)
	 */
	@Override
	protected String dropTableStatement() {
		return "drop table if exists Clinic";
	}

	public boolean createClinicTable() throws SQLException {
		return createTable();
	}

	public boolean dropClinicTable() throws SQLException {
		return dropTable();
	}

	/**
	 * Creates a row in the Clinic table, and returns a ClinicBean containing
	 * the id and data of the newly created row.
	 * @param name A string representing the clinic name.
	 * @param address A string representing the clinic address.
	 * @returns A ClinicBean containing the id of the row, name, and address.
	 * @throws SQLException
	 */
	public ClinicBean createClinic(String name, String address) 
			throws SQLException {
		String insertStatement = 
				"insert into Clinic (name, address) " + 
				"values (?, ?)";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(
				insertStatement, 
				Statement.RETURN_GENERATED_KEYS
		);
		ps.setString(1, name);
		ps.setString(2, address);
		ps.executeUpdate(	);
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			long id = rs.getLong(1);
			ClinicBean cb = new ClinicBean(
					id,
					name,
					address
			);
			rs.close();
			ps.close();
			c.close();
			
			return cb;
		} else {
			rs.close();
			ps.close();
			c.close();

			return null;
		}
	}
	
        public ArrayList<ClinicBean> getAllClinics() throws SQLException {
            ArrayList<ClinicBean> retval = new ArrayList();
            Connection c = getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Clinic");
            while(rs.next()) {
                ClinicBean tmp = new ClinicBean(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("address")
			);
                retval.add(tmp);
            }
            rs.close();
            s.close();
            c.close();
            return retval;
        }
        
	/** 
	 * Returns a single ClinicBean matching the provided id.
	 * @param id a `long` representing the id of a row.
	 * @returns a ClinicBean containing the row matching the provided id, 
	 * or null if no result.
	 */
	public ClinicBean findClinicById(long id) throws SQLException {
		String query = "select * from Clinic where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(query);

		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.first()) {
			ClinicBean cb = new ClinicBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("address")
			);

			rs.close();
			ps.close();
			c.close();

			return cb;
		} else {
			return null;
		}
	}

	/** 
	 * Returns a ClinicBean matching the provided name. Needs to be an exact match,
	 * otherwise will return null.
	 * @param name A string representing the name of a clinic.
	 * @returns ClinicBean, or null if no match was found.
	 * @throws SQLException
	 */
	public ClinicBean findClinicByName(String name) 
			throws SQLException {
		String query = "select * from Clinic where name=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(query);

		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		if (rs.first()) {
			ClinicBean cb = new ClinicBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("address")
			);
			rs.close();
			ps.close();
			c.close();
			
			return cb;
		} else {
			return null;
		}
	}

	/** 
	 * Returns an ArrayList<ClinicBean> containing all Clinics whose name
	 * is LIKE %address%. If no matches are found, ArrayList will be empty. 
	 * @param address String representing the address of a clinic.
	 * @throws SQLException
	 */
	public ArrayList<ClinicBean> findClinicByAddress(String address) 
			throws SQLException {
		String query = "select * from Clinic where address like ?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		ArrayList<ClinicBean> results = new ArrayList<>();

		ps.setString(1, "%" + address + "%");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ClinicBean cb = new ClinicBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("address")
			);
			results.add(cb);
		}
		
		rs.close();
		ps.close();
		c.close();

		return results;
	}

	/** 
	 * Updates a row in the Clinic table based on the information in a ClinicBean.
	 * All fields except for ID will be updated. Fields unchanged can remain
	 * in the ClinicBean without consequence.
	 * @param cb a ClinicBean containing updated Clinic informatoin
	 * @returns an integer representing the number of rows affected by the update.
	 * @throws SQLException
	 */
	public int updateClinic(ClinicBean cb) throws SQLException {
		String updateStatement = 
				"update Clinic set name=?, address=? where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(updateStatement);
		
		ps.setString(1, cb.getName());
		ps.setString(2, cb.getAddress());
		ps.setLong(3, cb.getId());
		
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}
	
	/** 
	 * Deletes the row in che Clinic table corresponding to the id of the 
	 * provided ClinicBean.
	 * @param cb a ClinicBean containing the id of the row to be deleted.
	 * @returns an integer representing the number of rows deleted.
	 * @throws SQLException
	 */
	public int deleteClinic(ClinicBean cb) throws SQLException {
		String deleteStatement = "delete from Clinic where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(deleteStatement);

		ps.setLong(1, cb.getId());
		
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}

	/** 
	 * Deletes the row in the Clinic table corresponding to the provided id.
	 * @param id a long int representing the Clinic row.
	 * @returns an integer representing the number of rows deleted.
	 * @throws SQLException
	 */
	public int deleteClinic(long id) throws SQLException {
		String deleteStatement = "delete from Clinic where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(deleteStatement);

		ps.setLong(1, id);
		
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}
}
