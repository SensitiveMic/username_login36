package com.example.usernamelogin.Gym_Owner.employeelist;

public class Model_class_staffandcoachlist {
    String username;
    String role;
    String fullname;
    String gym_name;
    public Model_class_staffandcoachlist(){

    }

    public Model_class_staffandcoachlist(String role, String username,String fullname) {
        this.role = role;
        this.username = username;
        this.fullname = fullname;
    }

    public String getGym_name() {
        return gym_name;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
