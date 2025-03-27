package com.example.usernamelogin.Member.Reservation.Current_Coach_Res;

public class Modelclass_for_current_member_res_accepted {

    String Coach_name, Gym_meet_date,Gym_meet_time;
    Integer viewType;

    public Modelclass_for_current_member_res_accepted(String coach_name, String gym_meet_date, String gym_meet_time,Integer viewType) {
        Coach_name = coach_name;
        Gym_meet_date = gym_meet_date;
        Gym_meet_time = gym_meet_time;
        this.viewType = viewType;
    }

    public Modelclass_for_current_member_res_accepted() {

    }

    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public String getCoach_name() {
        return Coach_name;
    }

    public void setCoach_name(String coach_name) {
        Coach_name = coach_name;
    }

    public String getGym_meet_date() {
        return Gym_meet_date;
    }

    public void setGym_meet_date(String gym_meet_date) {
        Gym_meet_date = gym_meet_date;
    }

    public String getGym_meet_time() {
        return Gym_meet_time;
    }

    public void setGym_meet_time(String gym_meet_time) {
        Gym_meet_time = gym_meet_time;
    }
}
