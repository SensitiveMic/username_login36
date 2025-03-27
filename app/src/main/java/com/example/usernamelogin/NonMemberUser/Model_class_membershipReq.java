package com.example.usernamelogin.NonMemberUser;

public class Model_class_membershipReq {
    String username, package_name, package_price, timeandDate;

    public Model_class_membershipReq(String username, String package_name, String package_price, String timeandDate) {
        this.username = username;
        this.package_name = package_name;
        this.package_price = package_price;
        this.timeandDate = timeandDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getTimeandDate() {
        return timeandDate;
    }

    public void setTimeandDate(String timeandDate) {
        this.timeandDate = timeandDate;
    }
}
