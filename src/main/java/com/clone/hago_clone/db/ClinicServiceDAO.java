/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.ClinicBean;
import com.clone.hago_clone.models.ClinicServiceBean;
import com.clone.hago_clone.models.ServiceBean;
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
public class ClinicServiceDAO extends BaseDAO {
	private ClinicDAO clinicDAO;
	private ServiceDAO serviceDAO;

	public ClinicServiceDAO(String url, String username, String password) 
			throws ClassNotFoundException {
		super(url, username, password);
		this.clinicDAO = new ClinicDAO(url, username, password);
		this.serviceDAO = new ServiceDAO(url, username, password);
	}

	@Override
	protected String createTableStatement() {
		return "create table if not exists ClinicService("
				+ "id int not null auto_increment,"
				+ "clinic_id int,"
				+ "service_id int,"
				+ "primary key (id),"
				+ "foreign key (clinic_id) references Clinic(id),"
				+ "foreign key (service_id) references Service(id)"
				+ ")";
	}

	@Override
	protected String dropTableStatement() {
		return "drop table ClinicService";
	}

	public boolean createClinicServiceTable() throws SQLException {
		return createTable();
	}

	public boolean dropClinicServiceTable() throws SQLException {
		return dropTable();
	}

	public ClinicServiceBean createClinicService(ClinicBean cb, ServiceBean sb) 
			throws SQLException {
		String sqlQuery = "insert into ClinicService (clinic_id, service_id) "
				+ "values (?, ?)";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(
				sqlQuery, 
				Statement.RETURN_GENERATED_KEYS
		);

		ps.setLong(1, cb.getId());
		ps.setLong(2, sb.getId());
		ps.executeUpdate();

		ResultSet result = ps.getGeneratedKeys();
		if (result.first()) {
			ClinicServiceBean csb = new ClinicServiceBean(
					result.getLong(1),
					cb,
					sb
			);

			result.close();
			ps.close();
			c.close();
			
			return csb;
		} else {
			return null;	
		}
	}

	public ClinicServiceBean findClinicServiceById(long id) 
			throws SQLException {
		String sqlQuery = "select * from ClinicService as cs"
				+ "inner join Clinic as c on cs.clinic_id = c.id "
				+ "inner join Service as s on cs.service_id = s.id"
				+ "where cs.id=?";
		Connection c =  getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		ps.setLong(1, id);
		ResultSet result = ps.executeQuery();
		if (result.first()) {
			ClinicBean cb = new ClinicBean(
					result.getLong("c.id"),
					result.getString("c.name"),
					result.getString("c.description")
			);	
			ServiceBean sb = new ServiceBean(
					result.getLong("s.id"),
					result.getString("s.name"),
					result.getString("s.description")
			);
			ClinicServiceBean csb = new ClinicServiceBean(
					result.getLong("cs.id"),
					cb,
					sb
			);
			return csb;
		} else {
			return null;
		}
	}

	public ArrayList<ClinicServiceBean> findClinicServiceByClinicId(long id) 
			throws SQLException {
		String sqlQuery = "select * from ClinicService as cs"
				+ "inner join Clinic as c on cs.clinic_id = c.id "
				+ "inner join Service as s on cs.service_id = s.id"
				+ "where c.id=?";
		Connection c =  getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		ArrayList<ClinicServiceBean> results = new ArrayList<>();

		ps.setLong(1, id);

		ResultSet result = ps.executeQuery();
		while (result.next()) {
			ClinicBean cb = new ClinicBean(
					result.getLong("c.id"),
					result.getString("c.name"),
					result.getString("c.description")
			);	
			ServiceBean sb = new ServiceBean(
					result.getLong("s.id"),
					result.getString("s.name"),
					result.getString("s.description")
			);
			ClinicServiceBean csb = new ClinicServiceBean(
					result.getLong("cs.id"),
					cb,
					sb
			);
			results.add(csb);
		}
		return results;
	}

	public ArrayList<ClinicServiceBean> findClinicServiceByServiceId(long id) 
			throws SQLException {
		String sqlQuery = "select * from ClinicService as cs"
				+ "inner join Clinic as c on cs.clinic_id = c.id "
				+ "inner join Service as s on cs.service_id = s.id"
				+ "where s.id=?";
		Connection c =  getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		ArrayList<ClinicServiceBean> results = new ArrayList<>();

		ps.setLong(1, id);

		ResultSet result = ps.executeQuery();
		while (result.next()) {
			ClinicBean cb = new ClinicBean(
					result.getLong("c.id"),
					result.getString("c.name"),
					result.getString("c.description")
			);	
			ServiceBean sb = new ServiceBean(
					result.getLong("s.id"),
					result.getString("s.name"),
					result.getString("s.description")
			);
			ClinicServiceBean csb = new ClinicServiceBean(
					result.getLong("cs.id"),
					cb,
					sb
			);
			results.add(csb);
		}
		return results;
	}

	public int deleteClinicService(ClinicServiceBean csb) throws SQLException {
		String sqlQuery = "delete form ClinicService where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);

		ps.setLong(1, csb.getId());

		int result = ps.executeUpdate();
	
		ps.close();
		c.close();
		
		return result;
	} 

	public int deleteClinicService(long id) throws SQLException {
		String sqlQuery = "delete form ClinicService where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);

		ps.setLong(1, id);

		int result = ps.executeUpdate();
	
		ps.close();
		c.close();
		
		return result;
	} 
}
