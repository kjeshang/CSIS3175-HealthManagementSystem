package com.example.healthmanagementapp.model.cashier;

import com.example.healthmanagementapp.model.User;

public class Cashier extends User {

    public Cashier(String id, String name, String password) {
        super(id, name, password);
    }

    @Override
    public boolean checkUser() {
        return super.checkUser();
    }

    @Override
    public String toString() {
        return "Cashier: " + super.toString();
    }

    @Override
    public String display() {
        return "*** Cashier ***" +
                super.display();
    }
}
