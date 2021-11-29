package com.example.tecknet.model;

public class InstitutionDetails implements InstitutionDetailsInt{

    private String institution_id;
    private String name;
    private String address;
    private String city;
    private String area;
    private String operation_hours;
    private String phone_number;
    private String contact;

    public InstitutionDetails(String id, String name, String addr, String city, String area, String operation_hours, String phone, String phone_maintenance){
        this.institution_id = id;
        this.name = name;
        this.address = addr;
        this.city = city;
        this.area = area;
        this.operation_hours = operation_hours;
        this.phone_number = phone;
        this.contact = phone_maintenance;
    }

    public String getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(String institution_id) {
        this.institution_id = institution_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOperation_hours() {
        return operation_hours;
    }

    public void setOperation_hours(String operation_hours) {
        this.operation_hours = operation_hours;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String phone_maintenance) {
        this.contact = phone_maintenance;
    }
}