/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

/**
 *
 * @author anonymous
 */
public class PatientQueueBean {
    private long id;
    private PatientBean patient;
    private QueueBean queue;

    public PatientQueueBean(long id, PatientBean patient, QueueBean queue) {
        this.id = id;
        this.patient = patient;
        this.queue = queue;
    }

    public long getId() {
        return id;
    }

    public PatientBean getPatient() {
        return patient;
    }

    public QueueBean getQueue() {
        return queue;
    }
            
}
