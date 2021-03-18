package com.example.healthmanagementapp.model;

public class User {

    private String id;
    private String name;
    private String password;

    public User(String id, String name, String password) {
        setId(id);
        setName(name);
        setPassword(password);
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

    public boolean checkUser(){
        if(getId().equals("") || getName().equals("") || getPassword().equals("")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString() {
        return getId() + ", " + getName();
    }

    public String display(){
        return "ID: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Password: " + getPassword();
    }
}
