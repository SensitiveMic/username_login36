package com.example.usernamelogin.Gym_Owner.Gym_manage;

public class Modelclass_gym_manage_Adapter {
    String gym_name, gym_descrp,gym_contact_number,gym_opening,gym_closing;
    public Modelclass_gym_manage_Adapter() {
        // Required for Firebase
    }
    public Modelclass_gym_manage_Adapter(String gym_name, String gym_descrp, String gym_contact_number, String gym_opening, String gym_closing) {
        this.gym_name = gym_name;
        this.gym_descrp = gym_descrp;
        this.gym_contact_number = gym_contact_number;
        this.gym_opening = gym_opening;
        this.gym_closing = gym_closing;
    }

    public String getGym_name() {
        return gym_name;
    }

    public String getGym_descrp() {
        return gym_descrp;
    }

    public String getGym_contact_number() {
        return gym_contact_number;
    }

    public String getGym_opening() {
        return gym_opening;
    }

    public String getGym_closing() {
        return gym_closing;
    }

}
