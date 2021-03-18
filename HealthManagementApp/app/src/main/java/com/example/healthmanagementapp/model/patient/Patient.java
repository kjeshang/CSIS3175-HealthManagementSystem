package com.example.healthmanagementapp.model.patient;

import com.example.healthmanagementapp.model.User;

public class Patient extends User {

    private String postalCode;

    public Patient(String id, String name, String password, String postalCode) {
        super(id, name, password);
        setPostalCode(postalCode);
    }

    public Patient(String id, String name, String password) {
        super(id, name, password);
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean checkUser() {
        if(getId().equals("") || getName().equals("") || getPassword().equals("") || getPostalCode().equals("")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Patient: " + super.toString();
    }

    @Override
    public String display() {
        return "*** Patient ***\n" +
                super.display() + "\n" +
                "Postal Code: " + getPostalCode();
    }
}
