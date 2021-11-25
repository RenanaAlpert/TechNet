package com.example.tecknet.model;

public class InstitutionDetails implements InstitutionDetailsInt{

    private final int institution_id;
    private final String name;
    private final String address;
    private final region area;
    private final String operation_hours;
    private final String phone_number;

    public InstitutionDetails(int id, String name, String addr, region area, String operation_hours, String phone){
        this.institution_id = id;
        this.name = name;
        this.address = addr;
        this.area = area;
        this.operation_hours = operation_hours;
        this.phone_number = phone;
    }

    @Override
    public int get_institution_Id() {
        return this.institution_id;
    }

    @Override
    public String get_name() {
        return this.name;
    }

    @Override
    public String get_address() {
        return this.address;
    }

    @Override
    public region get_area() {
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
