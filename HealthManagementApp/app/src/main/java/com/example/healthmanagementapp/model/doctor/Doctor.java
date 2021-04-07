package com.example.healthmanagementapp.model.doctor;

import com.example.healthmanagementapp.model.User;

public class Doctor extends User {

    private String postalCode;
    private String licenseNumber;

    public Doctor(String id, String name, String password, String postalCode, String licenseNumber) {
        super(id, name, password);
        setPostalCode(postalCode);
        setLicenseNumber(licenseNumber);
    }

    public Doctor(String id, String name, String password) {
        super(id, name, password);
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public boolean checkUser() {
        if(getId().equals("") || getName().equals("") || getPassword().equals("") || getLicenseNumber().equals("")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Doctor: " + super.toString();
    }

    @Override
    public String display() {
        return "*** Doctor ***\n" +
                super.display() + "\n" +
                "Postal Code: " + getPostalCode() + "\n" +
                "License Number: " + getLicenseNumber();
    }
}
