package com.example.usernamelogin.Staff.Gym_Management;

import android.widget.EditText;

public class Helper_Gym_adding_staff {
    String package_name, package_descrp, package_price;

    public Helper_Gym_adding_staff(String package_name, String package_descrp, String package_price) {
        this.package_name = package_name;
        this.package_descrp = package_descrp;
        this.package_price = package_price;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_descrp() {
        return package_descrp;
    }

    public void setPackage_descrp(String package_descrp) {
        this.package_descrp = package_descrp;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }
}
