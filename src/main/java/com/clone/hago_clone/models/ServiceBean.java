/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

import java.util.ArrayList;
import java.util.Objects;
/**
 *
 * @author Enrico Tuvera Jr
 */
public class ServiceBean {
	private long id;	
	private String name;
	private String description;

	public ServiceBean() {}	
	public ServiceBean(long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object otherObject) {
		ServiceBean other = (ServiceBean) otherObject;
		if (this.id == other.getId()) {
			return true;
		}

		if (this.name.equals(other.getName())) {
			return true;
		}
		
		if (otherObject == null) {
			return false;
		}

		if (this.getClass() != otherObject.getClass()) {
			return false;
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
		hash = 59 * hash + Objects.hashCode(this.name);
		hash = 59 * hash + Objects.hashCode(this.description);
		return hash;
	}
}
