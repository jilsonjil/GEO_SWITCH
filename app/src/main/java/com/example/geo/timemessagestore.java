package com.example.geo;

import java.io.Serializable;

public class timemessagestore implements Serializable {
    long id;
    String Contact_name;
    String Phone_number;
    String Date;
    String Message;
    String Time;

    public timemessagestore() {
    }

    public timemessagestore(String contact_name, String phone_number, String date, String message, String time) {
        Contact_name = contact_name;
        Phone_number = phone_number;
        Date = date;
        Message = message;
        Time = time;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
