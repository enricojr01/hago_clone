/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.models;

/**
 *
 * @author anonymous
 */


public class PatientBean {
    private long id; //READ
    private String name; //READ WRITE
    private String email; //READ WRITE
    private String pword; //READ WRITE
    
    
    public PatientBean(long id, String name, String email, String pword) {
        this.id = id;
        this.name = name;
        this.pword = pword;
        this.email = email;
    }

    
    public long getId() {
        return id;
    }
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    
}
