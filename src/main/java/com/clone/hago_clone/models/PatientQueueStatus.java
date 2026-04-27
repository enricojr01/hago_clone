/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

/**
 *
 * @author anonymous
 */
public enum PatientQueueStatus {    

    WAITING ("Waiting"),
    CANCELLED_CLINIC ("Cancelled by Clinic"),
    CANCELLED_USER ("Cancelled by You"),    
    MISSED ("Missed Appointment"),
    DONE ("Finished Appointment");
    
    
    private final String niceString;
    
    PatientQueueStatus(String niceString) {
        this.niceString = niceString;   
    }
    
    public static String getSQLType() {      
        return "ENUM('WAITING','CANCELLED_CLINIC','CANCELLED_USER','MISSED','DONE','CONFIRMED')";
    }
    
    public String getNiceString() { return this.niceString; }
    
    public boolean canCancel() {
        switch(this) {
            case WAITING:                            
                return true;                            
        }            
        return false;
    }            
}
