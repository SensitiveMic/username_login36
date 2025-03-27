package com.example.usernamelogin.Admin;

public class Admin_helper2 {
    String Gym_name, Gym_descrp, Userid;
    String Gym_contact_number;

    public Admin_helper2(String gym_name, String gym_descrp, String userid,String gym_contact_number) {
        Gym_name = gym_name;
        Gym_descrp = gym_descrp;
        Userid = userid;
        Gym_contact_number = gym_contact_number;
    }

    public String getGym_name() {
        return Gym_name;
    }

    public void setGym_name(String gym_name) {
        Gym_name = gym_name;
    }

    public String getGym_descrp() {
        return Gym_descrp;
    }

    public void setGym_descrp(String gym_descrp) {
        Gym_descrp = gym_descrp;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getGym_contact_number() {
        return Gym_contact_number;
    }

    public void setGym_contact_number(String gym_contact_number) {
        Gym_contact_number = gym_contact_number;
    }
}
