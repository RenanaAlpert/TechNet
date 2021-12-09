package com.example.tecknet.model;

import android.os.Parcelable;

/**
 * Interface of User - maintenance & technician
 */
public interface UserInt extends Parcelable {

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String getPass();

    public void setPass(String pass);

    public String getEmail();

    public void setEmail(String email);

    public String getPhone();

    public void setPhone(String phone) ;

    public String getRole();

    public void setRole(String role);
}
