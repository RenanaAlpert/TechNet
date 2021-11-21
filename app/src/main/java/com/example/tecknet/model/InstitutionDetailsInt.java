package com.example.tecknet.model;

import com.example.tecknet.model.region;

/**
 * Interface of institution details
 */
public interface InstitutionDetailsInt {

    /**
     * @return the id of the institution
     */
    public int get_institution_Id();

    /**
     * @return the name of the institution
     */
    public String get_name();

    /**
     * @return the address of the institution
     */
    public String get_address();

    /**
     * @return the region of the institution
     */
    public region get_area();

    /**
     * @return activity time of the institution
     */
    public String get_operation_hours();

    /**
     * @return the phone number of the institution
     */
    public String get_phone_number();
}
