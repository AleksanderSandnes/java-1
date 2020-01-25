package com.example.app4;

public class Lecturer {
    private String name, password, email, bildeURL;
    private int id;
    public Lecturer(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Lecturer(String name, String password, String email, String bildeURL, int id) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.bildeURL = bildeURL;
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
