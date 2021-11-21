package com.example.tecknet.model;

import com.example.tecknet.model.*;

import java.util.ArrayList;

/**
 * interface of controller- that connect between data base and gui
 */
public interface ControllerInt {
    /**
     *  Get user from data base by email and password
     * @param Email
     * @param password
     * @return
     */
    User get_user(String Email, String password);

    /**
     * Keep new user in data base
     * @param user
     * @param password
     */
    void set_user(User user,String password);

    /**
     * Get malfunctions assigned to tech from database
     * @param tech
     * @param amount   - mount of MalfunctionDetails to get from data base
     * @return   malfunctions tech opened is assigned to
     */
    ArrayList<MalfunctionDetails> get_tech_malfunction(TechnicianInt tech, int amount);

    /**
     *  Get malfunctions mm opened from database
     * @param mm      - maintenance man
     * @param amount   - amount of MalfunctionDetails to get from data base
     * @param all   - if true bring every MalfunctionDetails mm opened, else bring only the one that wasn't closed
     * @return    malfunctions mm opened
     */
    ArrayList<MalfunctionDetails> get_mm_malfunction(MaintenanceManInt mm, int amount,boolean all);

    /**
     * Get all un-assigned  malfunction from database
     * @param tech
     * @param amount  - amount of MalfunctionDetails to get from data base
     * @return
     */

    ArrayList<MalfunctionDetails> get_open_malfunction(TechnicianInt tech, int amount);

    /**
     *  Add  MalfuntionDetails to data base
     * @param user
     * @param mal
     */
    void set_malfunction(User user, MalfunctionDetails mal);





}
