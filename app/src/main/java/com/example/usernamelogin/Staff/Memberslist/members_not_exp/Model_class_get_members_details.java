package com.example.usernamelogin.Staff.Memberslist.members_not_exp;

public class Model_class_get_members_details {
    String GymName,expiration_date,start_date;
    String username;

    public Model_class_get_members_details() {
    }

    public Model_class_get_members_details(String username, String expiration_date, String start_date) {
        this.username = username;
        this.expiration_date = expiration_date;
        this.start_date = start_date;
    }

    public String getUsername() {
        return username;
    }

    public String getGymName() {
        return GymName;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public String getStart_date() {
        return start_date;
    }
}
