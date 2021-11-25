package com.example.tecknet.model;

public class Contact implements ContactInt{
    String first_name;
    String last_name;
    String phone_number;
    String email;

    /**
     * copy constructor
     * @param c -contact to copy
     */
    public Contact(Contact c) {
        this.first_name = c.first_name;
        this.last_name = c.last_name;
        this.phone_number = c.phone_number;
        this.email = c.email;
    }

    /**
     * constructor
     * @param first_name
     * @param last_name
     * @param phone_number
     * @param email
     */
    public Contact(String first_name, String last_name, String phone_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
    }

    /**
     * @return the first mane of the contact
     */
    @Override
    public String get_first_name() {
        return null;
    }

    /**
     * @param name - the first name of the contact
     */
    @Override
    public void set_first_name(String name) {

    }

    /**
     * @return the last mane of the contact
     */
    @Override
    public String get_last_name() {
        return null;
    }

    /**
     * @param name - the last name of the contact
     */
    @Override
    public void set_last_name(String name) {

    }

    /**
     * @return the phone number of the contact
     */
    @Override
    public String get_phone_number() {
        return null;
    }

    /**
     * @param phone - the phone number of the contact
     */
    @Override
    public void set_phone_number(String phone) {

    }

    /**
     * @return the email of the contact
     */
    @Override
    public String get_email() {
        return null;
    }

    /**
     * @param mail - the email of the contact
     */
    @Override
    public void set_email(String mail) {

    }
}
