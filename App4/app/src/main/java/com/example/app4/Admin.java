package com.example.app4;

public class Admin {
    private String username, email;
    private int id;
    public Admin(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}