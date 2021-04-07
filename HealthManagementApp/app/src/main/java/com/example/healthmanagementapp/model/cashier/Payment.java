package com.example.healthmanagementapp.model.cashier;

public class Payment {
    private String patientID, cashierID;
    private int value;

//    public Payment(String patientID, String cashierID, int value){
//        setPatientID(patientID);
//        setCashierID(cashierID);
//        setValue(this.value);
//    }
    public Payment(String patientID, int value){
        setPatientID(patientID);
        setValue(value);
    }

    public String getPatientID(){ return patientID; }
    private void setPatientID(String patientID){
        this.patientID = patientID;
    }

//    public String getCashierID(){ return cashierID; }
//    private void setCashierID(String cashierID){
//        this.cashierID = cashierID;
//    }

    public int getValue(){ return value; }
    private void setValue(int value){
        this.value = value;
    }
    public boolean checkPayment(){
        if(getPatientID().equals("") || getValue() == 0)
            return false;
        else
            return true;
    }
//    public boolean checkPayment(){
//        if(getPatientID().equals("") || getCashierID().equals("") || getPayment() == 0)
//            return false;
//        else
//            return true;
//    }
}
