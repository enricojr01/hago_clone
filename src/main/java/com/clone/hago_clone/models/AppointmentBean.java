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

    private int id;    
    private Timestamp date;
    private TimeslotBean timeslot;
    private String cancellation;
    private PatientBean patient;
    private ClinicBean clinic;
    private ServiceBean service;
    
    public AppointmentBean() {
    }

    public AppointmentBean(int id, Timestamp date, TimeslotBean timeslot, String cancellation, PatientBean patient, ClinicBean clinic, ServiceBean service) {
        this.id = id;
        this.date = date;
        this.timeslot = timeslot;
        this.cancellation = cancellation;
        this.patient = patient;
        this.clinic = clinic;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public TimeslotBean getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(TimeslotBean timeslot) {
        this.timeslot = timeslot;
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

    public void setPatient(PatientBean patient) {
        this.patient = patient;
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
