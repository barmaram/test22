package com.example.test2;

import java.util.ArrayList;

public class User {
private String email;
private ArrayList<String> BasketArrayList;

public User(){

}

    public User(String email, ArrayList<String> basketArrayList) {
        this.email = email;
        BasketArrayList = basketArrayList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getBasketArrayList() {
        return BasketArrayList;
    }

    public void setBasketArrayList(ArrayList<String> basketArrayList) {
        BasketArrayList = basketArrayList;
    }

}
