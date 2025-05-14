package com.example.usernamelogin.Gym_Owner.Gym_manage;

public class OpHoursModel_class {
    Boolean Day_active;
    String Opening_time,Closing_time;

    public OpHoursModel_class() {
    }

    public OpHoursModel_class(Boolean day_active, String opening_time, String closing_time) {
        Day_active = day_active;
        Opening_time = opening_time;
        Closing_time = closing_time;
    }

    public Boolean getDay_active() {
        return Day_active;
    }

    public void setDay_active(Boolean day_active) {
        Day_active = day_active;
    }

    public String getOpening_time() {
        return Opening_time;
    }

    public void setOpening_time(String opening_time) {
        Opening_time = opening_time;
    }

    public String getClosing_time() {
        return Closing_time;
    }

    public void setClosing_time(String closing_time) {
        Closing_time = closing_time;
    }
}
