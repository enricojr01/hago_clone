/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

/**
 *
 * @author anonymous
 */
public class AppointmentStatusBean {
    private int id;
    private AppointmentBean appointment;
    private StatusBean status;

    public AppointmentStatusBean() {
    }

    public AppointmentStatusBean(int id, AppointmentBean appointment, StatusBean status) {
        this.id = id;
        this.appointment = appointment;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppointmentBean getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentBean appointment) {
        this.appointment = appointment;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }
    
    
    
}
