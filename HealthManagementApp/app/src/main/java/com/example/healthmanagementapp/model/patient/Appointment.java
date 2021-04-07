package com.example.healthmanagementapp.model.patient;

public class Appointment {

    String doctorId;
    String dateTime;
    String patientId;

    public Appointment(String doctorId, String dateTime, String patientId) {
        setDoctorId(doctorId);
        setDateTime(dateTime);
        setPatientId(patientId);
    }

    public String getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "Appointment: " + getDateTime() + " (" + getDoctorId() + ")";
    }

    public String display(){
        return "*** Appointment ***" +
                "DoctorID: " + getDoctorId() + "\n" +
                "Date & Time: " + getDateTime() + "\n" +
                "PatientID: " + getPatientId();
    }
}
