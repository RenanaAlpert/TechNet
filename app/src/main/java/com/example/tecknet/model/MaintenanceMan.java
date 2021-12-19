package com.example.tecknet.model;

import com.example.tecknet.model.MaintenanceManInt;

public class MaintenanceMan implements MaintenanceManInt {

    private String phone_number, institution;
//    List<String> malfunctions_list;
    UserInt user;

    public MaintenanceMan() {
//        malfunctions_list = new ArrayList<>();
    }

    public MaintenanceMan(UserInt user) {
        this();
        this.user = user;
    }

    public MaintenanceMan(String phone_number, String institution) {
        this();
        this.phone_number = phone_number;
        this.institution = institution;
    }

    public UserInt getUser() {
        return user;
    }

    public void setUser(UserInt user) {
        this.user = user;
    }

//    public List<String> getMalfunctions() {
//        return malfunctions_list;
//    }

//    //todo not in interface
//    public void addMalfunctions(String malfunction) {
//        this.malfunctions_list.add(malfunction);
//    }
//
//    public void removeMalfunction(String malfunction) {
//        this.malfunctions_list.remove(malfunction);
//    }
//
//
//    public void setMalfunctions(ArrayList<String> malfunctions) {
//        this.malfunctions_list.clear();
//        this.malfunctions_list.addAll(malfunctions);
//    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getInstitution(){
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
