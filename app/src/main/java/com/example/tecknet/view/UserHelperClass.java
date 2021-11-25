package com.example.tecknet.view;

public class UserHelperClass {
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
}
