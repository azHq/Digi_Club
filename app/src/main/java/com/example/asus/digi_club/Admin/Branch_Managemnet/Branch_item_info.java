package com.example.asus.digi_club.Admin.Branch_Managemnet;

public class Branch_item_info {
    public int id;
    public String name;
    public String manager_name;
    public int manager_id;
    public String status;
    public String location;
    public String contact;
    public String image_path;

    public Branch_item_info(int id, String name, String manager_name, int manager_id, String status, String location, String contact, String image_path) {
        this.id = id;
        this.name = name;
        this.manager_name = manager_name;
        this.manager_id = manager_id;
        this.status = status;
        this.location = location;
        this.contact = contact;
        this.image_path = image_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
