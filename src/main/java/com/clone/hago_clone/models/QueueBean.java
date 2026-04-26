/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

/**
 *
 * @author anonymous
 */
public class QueueBean {
    private long id;
    private ClinicBean clinic;
    private ServiceBean service;
    private int capacity;

    public QueueBean(long id, ClinicBean clinic, ServiceBean service, int capacity) {
        this.id = id;
        this.clinic = clinic;
        this.service = service;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public ClinicBean getClinic() {
        return clinic;
    }

    public ServiceBean getService() {
        return service;
    }

    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }            
}
