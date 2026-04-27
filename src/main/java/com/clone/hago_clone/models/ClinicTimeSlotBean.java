/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;
/**
 *
 * @author anonymous
 */
public class ClinicTimeSlotBean {
    private long id; //READ
    private ClinicBean clinic; //READ
    private TimeSlotBean timeslot; //READ

    public ClinicTimeSlotBean() {}

    public ClinicTimeSlotBean(long id, ClinicBean clinic, TimeSlotBean timeslot) {
        this.id = id;
        this.clinic = clinic;
        this.timeslot = timeslot;
    }

    public long getId() {
        return id;
    }

    public ClinicBean getClinic() {
        return clinic;
    }
    
    public TimeSlotBean getTimeSlot() {
        return timeslot;
    }
           
}
