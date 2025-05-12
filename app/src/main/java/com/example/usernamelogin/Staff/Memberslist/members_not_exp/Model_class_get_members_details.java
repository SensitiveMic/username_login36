package com.example.usernamelogin.Staff.Memberslist.members_not_exp;

public class Model_class_get_members_details {
    String GymName,expiration_date,start_date;
    String username;
    String remainind_days;
    String package_name;

    public Model_class_get_members_details(String username, String expiration_date, String start_date, String package_name) {
        this.username = username;
        this.expiration_date = expiration_date;
        this.start_date = start_date;
        this.package_name = package_name;
    }

    public String getPackage_name() {
        return package_name;
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

    public String getRemainind_days() {
        return remainind_days;
    }

    public void setRemainind_days(String remainind_days) {
        this.remainind_days = remainind_days;
    }
}
