package com.example.tecknet;

import com.example.tecknet.model.MaintenanceManInt;

public class MaintenanceMan implements MaintenanceManInt {

    private String phone_number, institution;

    public MaintenanceMan(){}

    public MaintenanceMan(String phone_number, String institution){
        this.phone_number = phone_number;
        this.institution = institution;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
