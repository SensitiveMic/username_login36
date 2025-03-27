package com.example.usernamelogin;

public class Users_Update1 {
    String username;
    String password;
    String email;
    Integer membership_status;
    String Mobile;

    public Users_Update1(String username, String password, String email, Integer membership_status, String mobile) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.membership_status = membership_status;
        Mobile = mobile;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
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

    public Integer getMembership_status() {
        return membership_status;
    }

    public void setMembership_status(Integer membership_statsu) {
        this.membership_status = membership_statsu;
    }
}
