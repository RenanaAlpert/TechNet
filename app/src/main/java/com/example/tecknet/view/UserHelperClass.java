package com.example.tecknet.view;

import android.os.Parcel;
import android.os.Parcelable;

public class UserHelperClass implements Parcelable {
    private String firstName , lastName , pass, email ,phone ,role ;

    public UserHelperClass(){

    }
    public UserHelperClass(String firstName, String lastName, String pass, String email , String role ,
                           String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pass = pass;
        this.email = email;
        this.role = role;
        this.phone = phone;
    }


    protected UserHelperClass(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        pass = in.readString();
        email = in.readString();
        phone = in.readString();
        role = in.readString();
    }

    public static final Creator<UserHelperClass> CREATOR = new Creator<UserHelperClass>() {
        @Override
        public UserHelperClass createFromParcel(Parcel in) {
            return new UserHelperClass(in);
        }

        @Override
        public UserHelperClass[] newArray(int size) {
            return new UserHelperClass[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(pass);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(role);
    }
}
