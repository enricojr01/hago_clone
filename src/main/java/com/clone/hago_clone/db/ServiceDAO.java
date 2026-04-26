/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

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
public class ServiceDAO extends BaseDAO {

	public ServiceDAO(String url, String username, String password) 
			throws ClassNotFoundException {
		super(url, username, password);
	}
	
	@Override
	protected String createTableStatement() {
		return "create table if not exists Service("
			+ "id int not null auto_increment,"
			+ "name varchar(128) not null,"
			+ "description varchar(256) not null,"
			+ "clinic_id int,"
			+ "PRIMARY KEY (id),"
			+ "UNIQUE (name),"
			+ "FOREIGN KEY (clinic_id) REFERENCES Clinic(id)"
			+ ")";
	}

	@Override
	protected String dropTableStatement() {
		return "drop table if exists Service";
	}

	public boolean createServiceTable() throws SQLException {
		return createTable();
	}

	public boolean dropServiceTable() throws SQLException {
		return dropTable();
	}

	public ServiceBean createService(String name, String description) 
			throws SQLException {
		String sqlQuery = 
				"insert into Service (name, description) values (?, ?)";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(
				sqlQuery, 
				Statement.RETURN_GENERATED_KEYS
		);
		ps.setString(1, name);
		ps.setString(2, description);
		ps.executeUpdate();
		ResultSet results = ps.getGeneratedKeys();
		if (results.first()) {
			ServiceBean sb = new ServiceBean(
					results.getLong(1),
					name,
					description
			);
			// NOTE: there's gotta be a nicer way to do this
			ps.close();
			c.close();

			return sb;
		} else {
			ps.close();
			c.close();
			
			return null;
		}
	}

        public ArrayList<ServiceBean> getAllServices()
                throws SQLException {
            ArrayList<ServiceBean> retval = new ArrayList();
            Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM Service");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ServiceBean tmp = new ServiceBean(
                                        rs.getLong("id"),
					rs.getString("name"),
					rs.getString("description")
                );
                retval.add(tmp);
            }
            rs.close();
            ps.close();
            c.close();
            return retval;
            
        }
        
	public ServiceBean findServiceById(long id) throws SQLException {
		String sqlQuery = "select * from Service where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.first()) {
			ServiceBean sb = new ServiceBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("description")
			);
			return sb;
		} else {
			return null;
		}
	}

	public ServiceBean findServiceByName(String name) throws SQLException {
		String sqlQuery = "select * from Service where name=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		if (rs.first()) {
			ServiceBean sb = new ServiceBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("description")
			);
			return sb;
		} else {
			return null;
		}
	}

	public ArrayList<ServiceBean> findServiceByDescription(String desc) 
			throws SQLException {
		String sqlQuery = "select * from Service where description like ?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		String fullDesc = "%" + desc + "%";
		ArrayList<ServiceBean> results = new ArrayList<>();

		ps.setString(1, fullDesc);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ServiceBean sb = new ServiceBean(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("description")
			);
			results.add(sb);
		} 
		
		return results;
	}

	public int updateService(ServiceBean sb) throws SQLException {
		String sqlQuery = "update Service set name=?, description=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);

		ps.setString(1, sb.getName());
		ps.setString(2, sb.getDescription());
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}

	public int deleteService(ServiceBean sb) throws SQLException {
		String sqlQuery = "delete from Service where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		
		ps.setLong(1, sb.getId());
		int result = ps.executeUpdate();

		ps.close();
		c.close();
		
		return result;
	}

	public int deleteService(long id) throws SQLException {
		String sqlQuery = "delete from Service where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);

		ps.setLong(1, id);
		int result = ps.executeUpdate();

		ps.close();
		c.close();

		return result;
	}
}
