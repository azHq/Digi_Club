package com.example.asus.digi_club;

public class User {

    private String id;
    private String type;
    private String username, email, gender;
    private int branch_id;


    public User(String id, String type, String username, String email, String gender) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }

    public User(String id, String type, String username, String email, String gender, int branch_id) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.branch_id = branch_id;
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


    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }
}