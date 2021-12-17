package com.example.tecknet.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface of maintenanceMAn
 */
public interface MaintenanceManInt {

    public String getPhone_number();

    //
    public void setPhone_number(String phone_number);

    public String getInstitution();

    public void setInstitution(String institution);

//    public List<String> getMalfunctions();

//    public void addMalfunctions(String malfunction);

//    public void removeMalfunction(String malfunction);

    public UserInt getUser();

    public void setUser(UserInt user);

//    public void setMalfunctions(ArrayList<String> malfunctions);
}