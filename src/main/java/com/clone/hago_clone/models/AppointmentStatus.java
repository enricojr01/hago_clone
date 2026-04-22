/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

/**
 *
 * @author a1
 */
public enum AppointmentStatus {
    AWAITING ("Awaiting"),
    CANCELLED_CLINIC ("Cancelled by Clinic"),
    CANCELLED_USER ("Cancelled by You"),    
    MISSED ("Missed Appointment"),
    DONE ("Finished Appointment"),    
    CONFIRMED ("Confirmed");
    
    private final String niceString;
    
    AppointmentStatus(String niceString) {
        this.niceString = niceString;   
    }
    
    public static String getSQLType() {      
        return "ENUM('AWAITING','CANCELLED_CLINIC','CANCELLED_USER','MISSED','DONE','CONFIRMED')";
    }
    
    public String getNiceString() { return this.niceString; }
    
    
}
