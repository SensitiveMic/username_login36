package com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing;

public class Modelclass {
    String gym_name,gym_descrp;
    String gym_contact_number;
    String gymkey;
    String ownerkey;

    public Modelclass(String gym_name, String gym_descrp,String gym_contact_number) {
        this.gym_name = gym_name;
        this.gym_descrp = gym_descrp;
        this.gym_contact_number = gym_contact_number;
    }

    public Modelclass() {

    }

    public String getOwnerkey() {
        return ownerkey;
    }

    public void setOwnerkey(String ownerkey) {
        this.ownerkey = ownerkey;
    }

    public String getGymkey() {
        return gymkey;
    }

    public void setGymkey(String gymkey) {
        this.gymkey = gymkey;
    }

    public String getGym_name() {
        return gym_name;
    }

    public String getGym_descrp() {
        return gym_descrp;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
    }

    public void setGym_descrp(String gym_descrp) {
        this.gym_descrp = gym_descrp;
    }

    public String getGym_contact_number() {
        return gym_contact_number;
    }

    public void setGym_contact_number(String gym_contact_number) {
        this.gym_contact_number = gym_contact_number;
    }
}
