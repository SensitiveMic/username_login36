package com.example.usernamelogin.Staff.Membership_req_management;

public class Model_class_exiprationsys {
    private String start_date;
    private String expiration_date;
    public Model_class_exiprationsys() {
    }

    public Model_class_exiprationsys(String start_date, String expiration_date) {
        this.start_date = start_date;
        this.expiration_date = expiration_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }
}
