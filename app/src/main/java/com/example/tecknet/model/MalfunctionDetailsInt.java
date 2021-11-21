package com.example.tecknet.model;

/**
 * Interface of the malfunction details
 */
public interface MalfunctionDetailsInt {

    /**
     * @return the id of the malfunction
     */
    public long get_Malfunction_id();

    //public void set_Malfunction_id(long malfunction_id);

    /**
     * @return the details about the malfunction
     */
    public String get_Text_mal_details();

    /**
     * @param text_mal_details - the details about the malfunction
     */
    public void set_Text_mal_details(String text_mal_details);

    /**
     * @return if there is technician that dealing with the malfunction or not
     */
    public boolean is_taken_by_technician();

    /**
     * @param taken_by_technician - if there is technician that dealing with the malfunction or not
     */
    public void set_Is_taken_by_technician(boolean taken_by_technician);

    /**
     * @return the technician id of the technician that dealing with this malfunction
     */
    public long get_Technician_id();

    //public void set_Technician_id(long technician_id);
}
