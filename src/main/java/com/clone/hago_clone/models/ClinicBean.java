/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

import java.util.ArrayList;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class ClinicBean {
	private long id;
	private String name;
	private String address;

	private ArrayList<EmployeeBean> employees = new ArrayList<>();
	private ArrayList<ServiceBean> services = new ArrayList<>();

	public ClinicBean() {}
	public ClinicBean(long id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public ArrayList<ServiceBean> getServices() {
		return services;
	}

	public void setServices(ArrayList<ServiceBean> services) {
		this.services = services;
	}

	public ArrayList<EmployeeBean> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<EmployeeBean> employees) {
		this.employees = employees;
	}
}
