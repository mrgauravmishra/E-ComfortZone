package com.example.gaurav.comfortzone.Model;

/**
 * Created by Gaurav on 2/23/2018.
 */

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String secureCode;
    private String IsStaff;

    public User() {
    }

    public User(String name, String password, String phone, String secureCode) {
        Name = name;
        Password = password;
        Phone = phone;
        this.secureCode = secureCode;
        IsStaff = "false";
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
