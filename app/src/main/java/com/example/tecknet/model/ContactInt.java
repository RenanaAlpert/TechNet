package com.example.tecknet.model;

/**
 * Interface of contact to the institution
 */
public interface ContactInt {

    /**
     * @return the first mane of the contact
     */
    public String get_first_name();

    /**
     * @param name - the first name of the contact
     */
    public void set_first_name(String name);

    /**
     * @return the last mane of the contact
     */
    public String get_last_name();

    /**
     * @param name - the last name of the contact
     */
    public void set_last_name(String name);

    /**
     * @return the phone number of the contact
     */
    public String get_phone_number();

    /**
     * @param phone - the phone number of the contact
     */
    public void set_phone_number(String phone);

    /**
     * @return the email of the contact
     */
    public String get_email();

    /**
     * @param mail - the email of the contact
     */
    public void set_email(String mail);
}
