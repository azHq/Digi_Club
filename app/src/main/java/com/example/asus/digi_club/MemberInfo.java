package com.example.asus.digi_club;

public class MemberInfo{
    public String id;
    public String name;
    public String email;
    public String imagePath;
    public String department;

    public MemberInfo(String id, String name, String email, String department,String imagePath) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.imagePath=imagePath;
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
}
