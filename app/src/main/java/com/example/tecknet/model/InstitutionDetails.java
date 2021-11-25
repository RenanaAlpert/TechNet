package com.example.tecknet.model;

public class InstitutionDetails implements InstitutionDetailsInt{

    private final String institution_id;
    private final String name;
    private final String sity;

    private final String address;
    private final String area;
    private final String operation_hours;
    private final String phone_number;

    public InstitutionDetails(String id, String name,String sity, String addr, String area, String operation_hours,
                              String phone){
        this.institution_id = id;
        this.name = name;
        this.sity = sity;
        this.address = addr;
        this.area = area;
        this.operation_hours = operation_hours;
        this.phone_number = phone;
    }

    @Override
    public String getInstitution_id() {
        return this.institution_id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String get_sity() { return  this.sity;}

    @Override
    public String get_address() {
        return this.address;
    }

    @Override
    public String get_area() {
        return this.area;
    }

    @Override
    public String get_operation_hours() {
        return this.operation_hours;
    }

    @Override
    public String get_phone_number() {
        return this.phone_number;
    }
}
/*
package com.example.tecknet.model;

public class InstitutionDetails {

    private String institution_id;
    private String name;
    private String sity;

    private String address;
    private String area;
    private String operation_hours;
    private String phone_number;

    public InstitutionDetails() {
    }


    public InstitutionDetails(String id, String name, String sity, String addr, String area, String operation_hours,
                              String phone) {
        this.institution_id = id;
        this.name = name;
        this.sity = sity;
        this.address = addr;
        this.area = area;
        this.operation_hours = operation_hours;
        this.phone_number = phone;
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

    public String getSity() {
        return sity;
    }

    public void setSity(String sity) {
        this.sity = sity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}*/
