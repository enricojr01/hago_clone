/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;
import java.sql.Time;

/**
 *
 * @author anonymous
 */
public class TimeslotBean {
    private int id; //READ
    private int capacity; //READ WRITE
    private Time start; //READ WRITE
    private Time end; //READ WRITE

    public TimeslotBean(int id, int capacity, Time start, Time end) {
        this.id = id;
        this.capacity = capacity;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }
    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }
    
    
    
}
