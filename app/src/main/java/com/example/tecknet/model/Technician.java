package com.example.tecknet.model;

public class Technician implements TechnicianInt{

    private String phone_number;
    private String area;

    public Technician(){}

    public Technician(String phone_number, String area){
        this.phone_number = phone_number;
        this.area = area;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
