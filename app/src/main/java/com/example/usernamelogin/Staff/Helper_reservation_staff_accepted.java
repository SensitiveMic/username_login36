package com.example.usernamelogin.Staff;

public class Helper_reservation_staff_accepted {
    String date, time, user, res_id;

    public Helper_reservation_staff_accepted(String date, String time, String user, String res_id) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.res_id = res_id;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }
}
