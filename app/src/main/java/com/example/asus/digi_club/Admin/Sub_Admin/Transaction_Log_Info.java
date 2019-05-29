package com.example.asus.digi_club.Admin.Sub_Admin;

public class Transaction_Log_Info {

    public String id;
    public String user_id;
    public String admin_id;
    public String date;
    public String total_bill;
    public String des;

    public Transaction_Log_Info(String id, String user_id, String admin_id, String date, String total_bill, String des) {
        this.id = id;
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.date = date;
        this.total_bill = total_bill;
        this.des = des;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(String total_bill) {
        this.total_bill = total_bill;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
