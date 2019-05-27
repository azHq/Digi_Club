package com.example.asus.digi_club.Admin.Sub_Admin;

public class Sub_Admin_Info {
    public String id;
    public String email;
    public String type;
    public String name;
    public String image_path;

    public Sub_Admin_Info(String id, String email,String type, String name, String image_path) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.name = name;
        this.image_path = image_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
