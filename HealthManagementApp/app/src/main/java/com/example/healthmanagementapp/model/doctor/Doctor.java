package com.example.healthmanagementapp.model.doctor;

public class Doctor {

    private String id;
    private String name;
    private String password;
    private String postalCode;
    private String licenseNumber;

    public Doctor(){}

    public Doctor(String id, String name, String password, String postalCode, String licenseNumber) {
        setId(id);
        setName(name);
        setPassword(password);
        setPostalCode(postalCode);
        setLicenseNumber(licenseNumber);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public boolean checkDoctor(){
        if(getId().equals("") || getName().equals("") || getPassword().equals("") || getLicenseNumber().equals("")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "*** Doctor ***\n" +
                "ID: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Password: " + getPassword() + "\n" +
                "Postal Code: " + getPostalCode() + "\n" +
                "License Number: " + getLicenseNumber();
    }
}
