package com.example.tecknet.model;

public class MalfunctionDetails {
    static long malfunction_counter=0;
    long malfunction_id;
    /**
     * TODO: add product_id and connect between inventory to malfunctions
     * text_mal_details is only temporary
     */
    String text_mal_details;
    boolean taken_by_technician;
    long technician_id;
    Contact contact;

    public MalfunctionDetails(String text_mal_details,Contact contact) {
        this.malfunction_id=malfunction_counter;
        this.contact=new Contact(contact);
        malfunction_counter++;
        this.text_mal_details = text_mal_details;
        this.taken_by_technician=false;
        this.technician_id=-1;
    }

    public long get_Malfunction_id() {
        return malfunction_id;
    }

    /*public void set_Malfunction_id(long malfunction_id) {
        this.malfunction_id = malfunction_id;
    }*/

    public String get_Text_mal_details() {
        return text_mal_details;
    }

    public void set_Text_mal_details(String text_mal_details) {
        this.text_mal_details = text_mal_details;
    }

    public boolean is_taken_by_technician() {
        return taken_by_technician;
    }

    public void set_Is_taken_by_technician(boolean is_taken_by_technician) {
        this.taken_by_technician = is_taken_by_technician;
    }

    public long get_Technician_id() {
        return technician_id;
    }

    /*public void set_Technician_id(long technician_id) {
        this.technician_id = technician_id;
    }*/
}
