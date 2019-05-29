package com.example.asus.digi_club.Admin.Sub_Admin;

public class Member_info2{
    public String id;
    public String name;
    public String email;
    public String imagePath;
    public String department;
    public String total_bill;


    public Member_info2(String id, String name, String email, String imagePath, String department, String total_bill) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imagePath = imagePath;
        this.department = department;
        this.total_bill = total_bill;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(String total_bill) {
        this.total_bill = total_bill;
    }
}
