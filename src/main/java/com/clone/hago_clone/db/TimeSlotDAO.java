/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;

import com.clone.hago_clone.models.TimeSlotBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TimeSlotDAO extends BaseDAO {

	public TimeSlotDAO(String url, String username, String password) 
			throws ClassNotFoundException {
		super(url, username, password);
	}

	@Override
	protected String createTableStatement() {
		return "create table if not exists TimeSlot("
				+ "id int not null auto_increment,"
				+ "start time not null,"
				+ "end time not null,"
				+ "capacity int not null,"
				+ "primary key (id)"
				+ ")";
	}

	@Override
	protected String dropTableStatement() {
		return "drop table TimeSlot";
	}

	public boolean createTimeSlotTable() throws SQLException {
		return createTable();
	}

	public boolean dropTimeSlotTable() throws SQLException {
		return dropTable();
	}

	public TimeSlotBean createTimeSlot(
			LocalTime start, 
			LocalTime end, 
			int capacity
	) throws SQLException {
		String sqlQuery = 
				"insert into TimeSlot (start, end, capacity) "
				+ "values (?, ?, ?)";
		Connection c = getConnection();	
		PreparedStatement ps = c.prepareStatement(
				sqlQuery, 
				Statement.RETURN_GENERATED_KEYS
		);

		ps.setTime(1, Time.valueOf(start));
		ps.setTime(2, Time.valueOf(end));
		ps.setInt(3, capacity);

		ps.executeUpdate();
		
		ResultSet results = ps.getGeneratedKeys();
		if (results.first()) {
			TimeSlotBean tsb = new TimeSlotBean(
					results.getLong(1),
					start,
					end,
					capacity
			);

			results.close();
			ps.close();
			c.close();
			
			return tsb;
		} else {
			return null;
		}
	}

	public ArrayList<TimeSlotBean> findTimeSlotByStartTime(LocalTime start) 
			throws SQLException {
		String sqlQuery = "select * from TimeSlot as ts where ts.start=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		ArrayList<TimeSlotBean> results = new ArrayList<>();

		ps.setTime(1, Time.valueOf(start));
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			TimeSlotBean tsb = new TimeSlotBean(
					rs.getLong("id"),
					rs.getTime("start").toLocalTime(),
					rs.getTime("end").toLocalTime(),
					rs.getInt("capacity")
			);
			results.add(tsb);
		}

		rs.close();
		ps.close();
		c.close();

		return results;
	}

	public ArrayList<TimeSlotBean> findTimeSlotByEndTime(LocalTime end) 
			throws SQLException {
		String sqlQuery = "select * from TimeSlot as ts where ts.end=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		ArrayList<TimeSlotBean> results = new ArrayList<>();

		ps.setTime(1, Time.valueOf(end));
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			TimeSlotBean tsb = new TimeSlotBean(
					rs.getLong("id"),
					rs.getTime("start").toLocalTime(),
					rs.getTime("end").toLocalTime(),
					rs.getInt("capacity")
			);
			results.add(tsb);
		}

		rs.close();
		ps.close();
		c.close();

		return results;
	}

	public ArrayList<TimeSlotBean> findTimeSlotByCapacity(int capacity) 
			throws SQLException {
		String sqlQuery = "select * from TimeSlot as ts where ts.capacity=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		ArrayList<TimeSlotBean> results = new ArrayList<>();

		ps.setInt(1, capacity);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			TimeSlotBean tsb = new TimeSlotBean(
					rs.getLong("id"),
					rs.getTime("start").toLocalTime(),
					rs.getTime("end").toLocalTime(),
					rs.getInt("capacity")
			);
			results.add(tsb);
		}

		rs.close();
		ps.close();
		c.close();

		return results;
	}
		
	public int updateTimeSlot(TimeSlotBean tsb) throws SQLException {
		String sqlQuery = 
				"update TimeSlot set start=?, end=?, capacity=? "
				+ "where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		
		ps.setTime(1, Time.valueOf(tsb.getStart()));
		ps.setTime(2, Time.valueOf(tsb.getEnd()));
		ps.setInt(3, tsb.getCapacity());
		ps.setLong(4, tsb.getId());

		int results = ps.executeUpdate();

		ps.close();
		c.close();
		
		return results;
	}

	public int deleteTimeSlot(TimeSlotBean tsb) throws SQLException {
		String sqlQuery = "delete from TimeSlot where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);

		ps.setLong(1, tsb.getId());

		int results = ps.executeUpdate();
		
		ps.close();
		c.close();
		
		return results;
	}

	public int deleteTimeSlot(long id) throws SQLException {
		String sqlQuery = "delete from TimeSlot where id=?";
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement(sqlQuery);
		
		ps.setLong(1, id);

		int results = ps.executeUpdate();
		
		ps.close();
		c.close();
		
		return results;
	}
}
