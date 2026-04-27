/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class TimeSlotBean {
	private long id;
	private LocalTime start;
	private LocalTime end;
	private int capacity;

	public TimeSlotBean() {}
	public TimeSlotBean(long id, LocalTime start, LocalTime end, int capacity) {
		this.id = id;
		this.start = start;
		this.end = end;
		this.capacity = capacity;
	}

	public long getId() {
		return id;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean equals(Object other) {
		TimeSlotBean tsb = (TimeSlotBean) other;
		if (other == null) {
			return false;
		}

		if (this.getClass() != other.getClass()) {
			return false;
		}
		
		if (this.id == tsb.getId()) {
			return true;	
		}

		if (this.start.equals(tsb.getStart()) 
				&& this.end.equals(tsb.getEnd())) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
		hash = 59 * hash + Objects.hashCode(this.start);
		hash = 59 * hash + Objects.hashCode(this.end);
		hash = 59 * hash + this.capacity;
		return hash;
	}
}
