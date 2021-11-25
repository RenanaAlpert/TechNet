package com.example.tecknet.model;

import com.example.tecknet.model.region;

/**
 * Interface of institution details
 */
public interface InstitutionDetailsInt {

    /**
     * @return the id of the institution
     */
    public String getInstitution_id();

    /**
     * @return the name of the institution
     */
    public String getName();

    /**
     * @return the sity of the institution
     */
    public String get_sity();

    /**
     * @return the address of the institution
     */
    public String get_address();

    /**
     * @return the region of the institution
     */
    public String get_area();

    /**
     * @return activity time of the institution
     */
    public String get_operation_hours();

    /**
     * @return the phone number of the institution
     */
    public String get_phone_number();
}
