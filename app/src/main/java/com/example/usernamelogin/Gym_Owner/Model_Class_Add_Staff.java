package com.example.usernamelogin.Gym_Owner;

public class Model_Class_Add_Staff {
    String username;
    String password;
    String email;
    String mobile_number;

    public Model_Class_Add_Staff(String username, String password, String email, String mobile_number) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile_number = mobile_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
}
