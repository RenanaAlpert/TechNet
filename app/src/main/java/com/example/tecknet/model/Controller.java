package com.example.tecknet.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Controller implements ControllerInt{

    FirebaseDatabase rootNode;
    DatabaseReference reference;
//    /**
//     * Get user from data base by email and password
//     *
//     * @param Email
//     * @param password
//     * @return
//     */
//    @Override
//    public User get_user(String Email, String password) {
//        return null;
//    }
//
//    /**
//     * Keep new user in data base
//     *
//     * @param user
//     * @param password
//     */
//    @Override
//    public void set_user(User user, String password) {
//
//    }
//
//    /**
//     * Get malfunctions assigned to tech from database
//     *
//     * @param tech
//     * @param amount - mount of MalfunctionDetails to get from data base
//     * @return malfunctions tech opened is assigned to
//     */
//    @Override
//    public ArrayList<MalfunctionDetails> get_tech_malfunction(TechnicianInt tech, int amount) {
//        return null;
//    }
//
//    /**
//     * Get malfunctions mm opened from database
//     *
//     * @param mm     - maintenance man
//     * @param amount - amount of MalfunctionDetails to get from data base
//     * @param all    - if true bring every MalfunctionDetails mm opened, else bring only the one that wasn't closed
//     * @return malfunctions mm opened
//     */
//    @Override
//    public ArrayList<MalfunctionDetails> get_mm_malfunction(MaintenanceManInt mm, int amount, boolean all) {
//        return null;
//    }
//
//    /**
//     * Get all un-assigned  malfunction from database
//     *
//     * @param tech
//     * @param amount - amount of MalfunctionDetails to get from data base
//     * @return
//     */
//    @Override
//    public ArrayList<MalfunctionDetails> get_open_malfunction(TechnicianInt tech, int amount) {
//        return null;
//    }

    private void conect_db(String db){
        rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        reference = rootNode.getReference(db);
    }

    /**
     * Add  MalfuntionDetails to data base
     *
     * @param mal
     */
    @Override
    public void set_malfunction(MalfunctionDetails mal) {
        conect_db("mals");

    }
}
