package com.example.usernamelogin.Gym_Owner.employeelist;

public class Model_class_staffandcoachlist {
    String username;
    String role;
    public Model_class_staffandcoachlist(){

    }

    public Model_class_staffandcoachlist(String role, String username) {
        this.role = role;
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
