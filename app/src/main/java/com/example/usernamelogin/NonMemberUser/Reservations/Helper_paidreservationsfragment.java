package com.example.usernamelogin.NonMemberUser.Reservations;

public class Helper_paidreservationsfragment {

    String date, time, gym_name;

    public Helper_paidreservationsfragment() {

    }

    public Helper_paidreservationsfragment(String date, String time, String gym_name) {
        this.date = date;
        this.time = time;
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

    public String getGym_name() {
        return gym_name;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
    }
}
