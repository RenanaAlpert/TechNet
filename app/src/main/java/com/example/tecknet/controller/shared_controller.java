package com.example.tecknet.controller;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tecknet.R;
import com.example.tecknet.model.User;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.HomeMaintenanceMan;
import com.example.tecknet.view.HomeTechnician;
import com.example.tecknet.view.MaintenanceManDetailsActivity;
import com.example.tecknet.view.TechMenDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public abstract class shared_controller {



    protected static DatabaseReference connect_db(String db) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        return rootNode.getReference(db);
    }

    /**
     * This function is for login activity
     * and call FirebaseAuth try to sign in with email and password.
     * if success -> save in userId the user id that the FirebaseAuth gives,
     * and go to login_get_user how extract the User obj and then move the correct screen.
     * @param root
     * @param email
     * @param password
     */
    public static void login(View root , String email , String password){
        FirebaseAuth fAuth =FirebaseAuth.getInstance();

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = fAuth.getUid();
                    login_get_user(root,userId);
                    Toast.makeText(root.getContext(), "הכניסה בוצעה בהצלחה", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(root.getContext(), "שם משתמש או סיסמה לא נכונים או שמשתמש לא קיים.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //////////////************ START LOGIN FUNCTION ************//////////////

    /**
     * This function find the user phone and take the user obj according his phone.
     * @param root
     * @param userId
     */
    public static void login_get_user(View root , String userId){
        DatabaseReference r = connect_db("AuthToReal");
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone = (String) dataSnapshot.child(userId).getValue();
                assert phone != null;
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/" + phone);
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        assert user != null;
                        move_to_home_pages(user , root);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }});
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    /**
     * change status of malfunction in data base
     * @param mal
     * @param s
     */
    public static void set_status_malfunction(String mal, String s){
        DatabaseReference r = shared_controller.connect_db("mals/" + mal);
        r.child("status").setValue(s);
    }

    /**
     * This private function according the user obj go to the fix home page screen.
     * @param user
     * @param root
     */
    private static void move_to_home_pages(UserInt user, View root){
        if(user.getRole().equals("טכנאי")){
            Intent intent = new Intent();
            intent.setClass(root.getContext(), HomeTechnician.class);
            intent.putExtra("User" , user);
            root.getContext().startActivity(intent);

        }
        else{
            Intent intent = new Intent();
            intent.setClass(root.getContext(), HomeMaintenanceMan.class);
            intent.putExtra("User" , user);
            root.getContext().startActivity(intent);
        }

    }

    //////////////************ END LOGIN FUNCTION ************//////////////


    /////////////************ STAER SIGN UP USER ************//////////////

    public static void new_user_auth_real_db(UserInt user ,View root ){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPass()).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            add_new_user_to_Auth(fAuth.getUid() ,user.getPhone());
                            new_user(user);
                            Toast.makeText(root.getContext() ,"רישום הצליח", Toast.LENGTH_LONG).show();

                            move_to_register_continue(user, root);
                        }
                        else {
                            Toast.makeText(root.getContext() ,"בעיה ברישום!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    /**
     * this function move to the next screen
     */
    private static void move_to_register_continue(UserInt user, View root){
        if(user.getRole().equals("טכנאי")){
            Intent intent = new Intent();
            intent.setClass(root.getContext(), TechMenDetailsActivity.class);
            intent.putExtra("User" , user);
            root.getContext().startActivity(intent);
        }
        else{
            Intent intent = new Intent();
            intent.setClass(root.getContext(), MaintenanceManDetailsActivity.class);
            intent.putExtra("User" , user);
            root.getContext().startActivity(intent);
        }

    }

    /**
     * Add new User.
     *
     */
    public static void new_user(UserInt user) {
        DatabaseReference r = connect_db("users");
        r.child(user.getPhone()).setValue(user);
    }

    /**
     * This controller function is enter to AuthToReal table the new user id - phone
     * we want the access to user db be more essy
     * @param userId
     * @param phoneNum
     */
    public static void add_new_user_to_Auth(String userId , String phoneNum){
        DatabaseReference r = connect_db("AuthToReal");
        r.child(userId).setValue(phoneNum);

    }
    /////////////************ END SIGN UP USER ************//////////////

    /////////////************ START UPDATE USER SHARED DETAILS ************//////////////

    /**
     * yuval for update details
     */
    public static void update_user_details(String phone , String first, String last){
        DatabaseReference r = connect_db("users/"+phone);
        if(!first.equals("")&&!first.equals(" ") && !first.equals("\n")){
            r.child("firstName").setValue(first);
        }
        if(!last.equals("")&&!last.equals(" ") && !last.equals("\n")){
            r.child("lastName").setValue(last);
        }
    }
    /**
     * yuval for update details
     */
    public static void update_user_pass(String phone , EditText new1 ){
        DatabaseReference r = connect_db("users/"+phone);
        String new1str  = new1.getText().toString();
        r.child("pass").setValue(new1str);

    }
    public static void change_user_pass(String email , String pass){
        FirebaseAuth mAuth;

        FirebaseUser thisUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(email, pass);

        thisUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            thisUser.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Password updated");
                                    } else {
                                        Log.d(TAG, "Error password not updated");
                                    }
                                }
                            });
                        } else {
                            Log.d(TAG, "Error auth failed");
                        }
                    }
                });
    }
    /////////////************ END UPDATE USER SHARED DETAILS ************//////////////

    public static void loadMalImage(String malPicId, Context context, ImageView imageView)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("mals_images/" + malPicId);
        System.out.println("mals_images/" + malPicId);
        System.out.println(storageReference);

        Glide.with(context)
                .load(storageReference)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.homepage_back)
                        .fitCenter())
                .into(imageView);
    }
}
