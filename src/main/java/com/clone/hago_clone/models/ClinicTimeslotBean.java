/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;
/**
 *
 * @author anonymous
 */
public class ClinicTimeslotBean {
    private int id; //READ
    private ClinicBean clinic; //READ
    private TimeSlotBean timeslot; //READ

    public ClinicTimeslotBean() {
    }

    public ClinicTimeslotBean(int id, ClinicBean clinic, TimeSlotBean timeslot) {
        this.id = id;
        this.clinic = clinic;
        this.timeslot = timeslot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClinicBean getClinic() {
        return clinic;
    }
    
    public TimeSlotBean getTimeSlot() {
        return timeslot;
    }
           
}
