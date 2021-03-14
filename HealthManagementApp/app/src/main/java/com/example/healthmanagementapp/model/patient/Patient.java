package com.example.healthmanagementapp.model.patient;

public class Patient {

    private String id;
    private String name;
    private String password;
    private String postalCode;

    public Patient(){}

    public Patient(String id, String name, String password, String postalCode){
        setId(id);
        setName(name);
        setPassword(password);
        setPostalCode(postalCode);
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

    public boolean checkPatient(){
        if(getId().equals("") || getName().equals("") || getPassword().equals("") || getPostalCode().equals("")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "*** Patient ***\n" +
                "ID: " + getId() +"\n" +
                "Name: " + getName() + "\n" +
                "Password: " + getPassword() + "\n" +
                "Postal Code: " + getPostalCode();
    }
}
