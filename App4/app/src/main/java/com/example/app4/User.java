package com.example.app4;

public class User {
    private String name, password, email, studieretning, kull;
    private int id;
    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public User(String name, String password, String email, String studieretning, String kull, int id) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.studieretning = studieretning;
        this.kull = kull;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
