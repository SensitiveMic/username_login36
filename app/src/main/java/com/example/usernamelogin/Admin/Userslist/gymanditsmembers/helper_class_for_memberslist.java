package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

public class helper_class_for_memberslist {
    String username,fullname;
    public helper_class_for_memberslist() {
    }

    public helper_class_for_memberslist(String username) {
        this.username = username;
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
}
