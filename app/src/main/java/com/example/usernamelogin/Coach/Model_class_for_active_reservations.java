package com.example.usernamelogin.Coach;

public class Model_class_for_active_reservations {

    String Fullname, Date_sent, Meet_time;
    int viewType;
    private String pushId;

    public String getFullname() {
        return Fullname;
    }

    public String getDate_sent() {
        return Date_sent;
    }

    public String getMeet_time() {
        return Meet_time;
    }

    public int getViewType() {
        return viewType;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
