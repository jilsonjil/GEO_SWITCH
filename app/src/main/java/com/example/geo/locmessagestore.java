package com.example.geo;

public class locmessagestore {
    String Contact_name;
    String Phone_number;
    String Date;
    String Message;
    String Location;

    public locmessagestore() {
    }




    public locmessagestore(String contact_name, String phone_number, String date, String message, String location) {
        Contact_name = contact_name;
        Phone_number = phone_number;
        Date = date;
        Message = message;
        Location = location;
    }

    public String getContact_name() {
        return Contact_name;
    }

    public void setContact_name(String contact_name) {
        Contact_name = contact_name;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}

