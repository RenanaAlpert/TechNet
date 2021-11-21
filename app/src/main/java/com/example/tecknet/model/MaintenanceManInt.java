package com.example.tecknet.model;

import java.util.Map;

/**
 * Interface of maintenanceMAn
 */
public interface MaintenanceManInt {

    /**
     * @return 1 if it's sent, 0 if not
     */
    public int report_malfunction();

    /**
     * @return list of all the malfunction in treatment that the maintenanceMan report about them
     */
    public Map<Integer, MalfunctionDetailsInt> malfunction_in_treatment();

    /**
     * @return list of all the malfunction that not in treatment that the maintenanceMan report about them
     */
    public Map<Integer, MalfunctionDetailsInt> malfunction_not_in_treatment();
}
