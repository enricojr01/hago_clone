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

	/** 
	 * Creates a TimeSlot row with the provided arguments. Returns a 
	 * TimeSlotBean containing the id of the created row, and associated data.
	 * 
	 * @param start LocalTime representing 24hr start time of the time slot.
	 * @param end LocalTime representing the 24hr end time of the time slot.
	 * @returns TimeSlotBean with the id of the created row, or null.
	 * @throws SQLException
	 */
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

                
	/**
	 * Returns all TimeSlots matching the given start time.
	 * 
	 * @param start LocalTime representing a time slot's (24hr) start time.
	 * @returns ArrayList<TimeSlotBean>
	 * @throws SQLException
	 */
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

	/**
	 * Returns all TimeSlots matching the given end time.
	 * 
	 * @param end LocalTime representing a time slot's (24hr) start time.
	 * @returns ArrayList<TimeSlotBean>
	 * @throws SQLException
	 */
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

	/**
	 * Returns all TimeSlots matching the given capacity.
	 * 
	 * @param capacity int represents capacity of the TimeSlot.
	 * @returns ArrayList<TimeSlotBean>
	 * @throws SQLException
	 */
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

	/** 
	 * Updates the TimeSlot row matching the id of the provided TimeSlotBean.
	 * Returns 0 if the operation fails, 1 otherwise.
	 * 
	 * @param tsb TimeSlotBean with valid ID and updated fields.
	 * @returns 1 on success, 0 otherwise.
	 * @throws SQLException
	 */
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

	/** 
	 * Deletes the TimeSlot row matching the id of the provided TimeSlotBean.
	 * Returns 0 if the operation fails, 1 otherwise.
	 * 
	 * @param tsb TimeSlotBean with valid ID.
	 * @returns 1 on success, 0 otherwise.
	 * @throws SQLException
	 */
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

	/** 
	 * Deletes the TimeSlot row matching the provide id. Returns 0 if the 
	 * operation fails, 1 otherwise.
	 * 
	 * @param id long int, matching the ID of the target row.
	 * @returns 1 on success, 0 otherwise.
	 * @throws SQLException
	 */
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
        
        
        
        /**
         * Returns the TimeSlot with the same id
         * @param id long, matching the ID of the target row
         * @returns A TimeSlotBean, or null if the id does not belong to a existing time slot
         * @throws SQLException 
         */
        public TimeSlotBean findTimeSlotById(long id) throws SQLException {
            TimeSlotBean retval = null;
            Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement("select * from TimeSlot where id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                retval = new TimeSlotBean(id,
                        rs.getTime("start").toLocalTime(),
                        rs.getTime("end").toLocalTime(),
                            rs.getInt("capacity"));
            }
            rs.close();
            ps.close();
            c.close();
            return retval;
        }
        
        
}
