package com.example.tecknet.model;

public class MalfunctionDetails {
    static long malfunction_counter=0;
    long malfunction_id;
    /**
     * TODO: add product_id and connect between invantory to malfunctions
     * text_mal_details is only temporary
     */
    String text_mal_details;
    boolean is_taken_by_technician;
    long technician_id;

    public MalfunctionDetails(String text_mal_details) {
        this.malfunction_id=malfunction_counter;
        malfunction_counter++;
        this.text_mal_details = text_mal_details;
        this.is_taken_by_technician=false;
        this.technician_id=-1;
    }

    public long getMalfunction_id() {
        return malfunction_id;
    }

    public void setMalfunction_id(long malfunction_id) {
        this.malfunction_id = malfunction_id;
    }

    public String getText_mal_details() {
        return text_mal_details;
    }

    public void setText_mal_details(String text_mal_details) {
        this.text_mal_details = text_mal_details;
    }

    public boolean isIs_taken_by_technician() {
        return is_taken_by_technician;
    }

    public void setIs_taken_by_technician(boolean is_taken_by_technician) {
        this.is_taken_by_technician = is_taken_by_technician;
    }

    public long getTechnician_id() {
        return technician_id;
    }

    public void setTechnician_id(long technician_id) {
        this.technician_id = technician_id;
    }
}
