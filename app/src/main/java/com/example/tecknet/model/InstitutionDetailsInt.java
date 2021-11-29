package com.example.tecknet.model;

import com.example.tecknet.model.region;

/**
 * Interface of institution details
 */
public interface InstitutionDetailsInt {

    public String getInstitution_id();

    public void setInstitution_id(String institution_id);

    public String getName();

    public void setName(String name);

    public String getAddress();

    public void setAddress(String address);

    public String getCity();

    public void setCity(String city);

    public region getArea();

    public void setArea(region area);

    public String getOperation_hours();

    public void setOperation_hours(String operation_hours);

    public String getPhone_number();

    public void setPhone_number(String phone_number);

    public String getContact();

    public void setContact(String phone_maintenance);

    }
