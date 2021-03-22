package com.example.healthmanagementapp.model.patient;

public class Disease {

    private int id;
    private String patientId;
    private String allergy;
    private String diseaseInfo;

    public Disease(){}

    public Disease(int id, String patientId, String allergy, String diseaseInfo) {
        setId(id);
        setPatientId(patientId);
        setAllergy(allergy);
        setDiseaseInfo(diseaseInfo);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAllergy() {
        return allergy;
    }
    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getDiseaseInfo() {
        return diseaseInfo;
    }
    public void setDiseaseInfo(String diseaseInfo) {
        this.diseaseInfo = diseaseInfo;
    }

    @Override
    public String toString() {
        return "*** Patient Health History ***\n" +
                "ID: " + getId() + "\n" +
                "Allergies: " + getAllergy() + "\n" +
                "Diseases: " + getDiseaseInfo();
    }
}
