package com.example.tecknet.model;

import android.os.Parcelable;

import com.example.tecknet.model.region;

import java.util.Map;

/**
 * Interface of institution details
 */
public interface InstitutionDetailsInt extends Parcelable {

    public String getInstitution_id();

    public void setInstitution_id(String institution_id);

    public String getName();

    public void setName(String name);

    public String getAddress();

    public void setAddress(String address);

    public String getCity();

    public void setCity(String city);

    public String getArea();

    public void setArea(String area);

    public String getOperation_hours();

    public void setOperation_hours(String operation_hours);

    public String getPhone_number();

    public void setPhone_number(String phone_number);

    public String getContact();

    public void setContact(String phone_maintenance);

    public Map<Long, Long> getInventory();

    public void setInventory(Map<Long, Long> inventory);
    }
