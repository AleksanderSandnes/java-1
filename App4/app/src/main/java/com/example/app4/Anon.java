package com.example.app4;

public class Anon {
    private String username, password;
    int id;
    public Anon(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }
}
