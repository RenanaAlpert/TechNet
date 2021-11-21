package com.example.tecknet;

public class User {

    private String email;
    private String fName;
    private String lName;
    private String pass;
    private String role;

    public String userId;

    public User(String e, String fn,String ln, String p, String r){
        this.email=e;
        this.fName=fn;
        this.lName=ln;
        this.pass=p;
        this.role=r;
    }

    public String getlName() {
        return lName;
    }
    public String getfName() {
        return fName;
    }

    public String getPass() {
        return pass;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
