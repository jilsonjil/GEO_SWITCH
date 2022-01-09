package com.example.geo;

public class storingdata {
    String name;
    String email;
    String phone;
    String password;
    String cpassword;
    String username;

    public storingdata() {
    }



    public storingdata(String name, String email, String phone, String password, String cpassword, String username) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.cpassword = cpassword;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
       this.username = username;
    }



}
