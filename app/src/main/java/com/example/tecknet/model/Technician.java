package com.example.tecknet.model;

public class Technician implements TechnicianInt{

    private String phone_number;
    private region area;

    public Technician(){}

    public Technician(String phone_number, region area){
        this.phone_number = phone_number;
        this.area = area;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public region getArea() {
        return area;
    }

    public void setArea(region area) {
        this.area = area;
    }
}
