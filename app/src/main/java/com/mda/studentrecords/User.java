package com.mda.studentrecords;

public class User {

    public String idNumber, fname, lname, email;
    public int userType;

    public User(){

    }

    public User(String idNumber, String fname, String lname, String email, int userType){
        this.idNumber=idNumber;
        this.fname=fname;
        this.lname=lname;
        this.email=email;
        this.userType=userType;
    }
}
