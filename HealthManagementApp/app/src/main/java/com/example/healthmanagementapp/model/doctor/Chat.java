package com.example.healthmanagementapp.model.doctor;

public class Chat {

    private String patientID, doctorID, chat;

    public Chat (String patientID, String doctorID, String chat){
        setPatientID(patientID);
        setDoctorID(doctorID);
        setChat(chat);
    }

    public String getPatientID(){ return patientID; }
    private void setPatientID(String patientID){
        this.patientID = patientID;
    }

    public String getDoctorID(){ return doctorID; }
    private void setDoctorID(String doctorID){
        this.doctorID = doctorID;
    }

    public String getChat(){ return chat; }
    private void setChat(String chat){
        this.chat = chat;
    }

    public boolean checkChat(){
        if(getPatientID().equals("") || getDoctorID().equals("") || getChat().equals(""))
            return false;
        else
            return true;
    }
}
