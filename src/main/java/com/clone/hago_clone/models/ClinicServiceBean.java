/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class ClinicServiceBean {
	private long id;	
	private ClinicBean clinic;
	private ServiceBean service;

	public ClinicServiceBean() {};
	public ClinicServiceBean(long id, ClinicBean cb, ServiceBean sb) {
		this.id = id;
		this.clinic = cb;
		this.service = sb;
	}

	public long getId() {
		return id;
	}

	public ClinicBean getClinic() {
		return clinic;
	}

	public void setClinic(ClinicBean clinic) {
		this.clinic = clinic;
	}

	public ServiceBean getService() {
		return service;
	}

	public void setService(ServiceBean service) {
		this.service = service;
	}


}
