package com.example.healthmanagementapp.model.patient;

import com.example.healthmanagementapp.model.User;

import java.io.Serializable;

public class Patient extends User implements Serializable {

    private String postalCode;
    private String allergies;
    private String diseases;

    public Patient(String id, String name, String password, String postalCode, String allergies, String diseases) {
        super(id, name, password);
        setPostalCode(postalCode);
        setAllergies(allergies);
        setDiseases(diseases);
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

    public String getAllergies() {
        return allergies;
    }
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getDiseases() {
        return diseases;
    }
    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    @Override
    public boolean checkUser() {
        if(getId().equals("") || getName().equals("") || getPassword().equals("") || getPostalCode().equals("") || getAllergies().equals("") || getDiseases().equals("")){
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
                "Postal Code: " + getPostalCode() + "\n" +
                "Allergies: " + getAllergies() + "\n" +
                "Diseases: " + getDiseases();
    }
}
