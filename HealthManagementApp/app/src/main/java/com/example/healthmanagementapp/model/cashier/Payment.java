package com.example.healthmanagementapp.model.cashier;

public class Payment {
    private String patientID;
    private int value;

    public Payment(String patientID, int value){
        setPatientID(patientID);
        setValue(value);
    }

    public String getPatientID(){ return patientID; }
    private void setPatientID(String patientID){
        this.patientID = patientID;
    }

    public int getValue(){ return value; }
    private void setValue(int value){
        this.value = value;
    }
}
