package com.example.tecknet.model;

import java.util.Map;

/**
 * Interface of technician
 */
public interface TechnicianInt extends UserInt {

    /**
     * @return the technician's work area
     */
    public region get_work_area();

    /**
     * @param region - the work area to the technician
     */
    public void set_work_area(region region);

    /**
     * @return list of all open malfunction
     */
    public Map<Integer,MalfunctionDetailsInt> get_open_malfunction();

    /**
     * @param malfunction_id - the malfunction that the technician pick
     */
    public void register_to_fix_malfunction(int malfunction_id);

    /**
     * @param malfunction_id - the id of the malfunction that the technician dealing now
     * @return
     */
    public MalfunctionDetailsInt get_my_malfunction(int malfunction_id);
}
