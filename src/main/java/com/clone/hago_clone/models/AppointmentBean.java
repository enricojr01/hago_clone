/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

import java.sql.Timestamp;

/**
 *
 * @author anonymous
 */
public class AppointmentBean {

    private long id; //READ
    private Timestamp date; //READ WRITE    
    private AppointmentStatus status; //READ WRITE
    private PatientBean patient; //READ
    private ClinicBean clinic; //READ
    private ServiceBean service; //READ
              
    public AppointmentBean(long id, Timestamp date,AppointmentStatus status,PatientBean patient, ClinicBean clinic, ServiceBean service) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.patient = patient;
        this.clinic = clinic;
        this.service = service;
    }

    public long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) { 
        this.date = date; 
    }

    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public PatientBean getPatient() {
        return patient;
    }

    public ClinicBean getClinic() {
        return clinic;
    }

    public ServiceBean getService() {
        return service;
    }
           
    
}
