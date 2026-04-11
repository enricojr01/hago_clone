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
public class AppointmentBean /* implements Serializable */ {

    private int id; //READ
    private Timestamp date; //READ WRITE    
    private String cancellation; //READ WRITE
    private PatientBean patient; //READ
    private ClinicBean clinic; //READ
    private ServiceBean service; //READ
              
    public AppointmentBean(int id, Timestamp date,String cancellation,PatientBean patient, ClinicBean clinic, ServiceBean service) {
        this.id = id;
        this.date = date;
        this.cancellation = cancellation;
        this.patient = patient;
        this.clinic = clinic;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) { 
        this.date = date; 
    }

    public String getCancellation() {
        return cancellation;
    }
    
    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
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
