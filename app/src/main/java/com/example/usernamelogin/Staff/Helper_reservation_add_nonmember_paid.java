package com.example.usernamelogin.Staff;

public class Helper_reservation_add_nonmember_paid {

    String date, time, user, res_id,gym_name;

    public Helper_reservation_add_nonmember_paid(String date, String time, String user, String res_id, String gym_name) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.res_id = res_id;
        this.gym_name = gym_name;
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

    public String getGym_name() {
        return gym_name;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
    }
}
