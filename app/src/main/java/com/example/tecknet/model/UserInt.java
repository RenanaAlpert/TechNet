package com.example.tecknet.model;

/**
 * Interface of user - maintenance & technician
 */
public interface UserInt {

    /**
     * @return id of the user
     */
    public int get_user_id();

    /**
     * @return the first name of the user
     */
    public String get_first_name();

    /**
     * @return the last name of the user
     */
    public String get_last_name();

    /**
     * @return the phone number of the user
     */
    public String get_phone_number();
}
