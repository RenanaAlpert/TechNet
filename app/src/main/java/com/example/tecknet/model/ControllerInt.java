package com.example.tecknet.model;

import com.example.tecknet.model.*;

import java.util.ArrayList;

/**
 * interface of controller- that connect between data base and gui
 */
public interface ControllerInt {

    public void new_user(String first_name, String last_name, String phone, String mail, String password, String role);

    public void set_institution(String symbol, String name, String address, String city, String area, String phone_number, String phone_maintenance);

    public void new_malfunction(String symbol, String device, String company, String type, String date_of_responsibility, String year_of_production, String explain);


}
