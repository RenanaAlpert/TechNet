package com.example.tecknet.model;

import android.os.Parcel;
import android.os.Parcelable;

public class user implements UserInt , Parcelable {
    private String firstName , lastName , pass, email ,phone ,role;

    public user(){

    }
    public user(String firstName, String lastName, String pass, String email , String role,
                String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    protected user(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        pass = in.readString();
        email = in.readString();
        phone = in.readString();
        role = in.readString();
    }

    public static final Creator<user> CREATOR = new Creator<user>() {
        @Override
        public user createFromParcel(Parcel in) {
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
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