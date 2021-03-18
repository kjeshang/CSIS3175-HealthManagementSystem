package com.example.healthmanagementapp.model.admin;

import com.example.healthmanagementapp.model.User;

public class Admin {

    private String id;
    private String name;
    private String password;

    public Admin(){}

    public Admin(String id, String name, String password){
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

    @Override
    public String toString() {
        return "*** Admin ***\n" +
                "ID: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Password: " + getPassword();
    }
}
