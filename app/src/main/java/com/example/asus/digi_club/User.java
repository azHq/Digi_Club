package com.example.asus.digi_club;

public class User {

    private String id;
    private String type;
    private String username, email, gender;


    public User(String id, String type, String username, String email, String gender) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}