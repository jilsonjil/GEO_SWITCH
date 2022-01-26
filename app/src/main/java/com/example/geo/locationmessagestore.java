package com.example.geo;
import java.io.Serializable;
import java.util.List;

public class locationmessagestore implements Serializable {
    long id;
    String Contact_name;
    String Phone_number;
    String Date;
    String Message;
    List<Double> Location;

    public locationmessagestore() {
    }




    public locationmessagestore(String Contact_name, String Phone_number, String Date, String Message, List<Double> Location) {
        this.Contact_name = Contact_name;
        this.Phone_number = Phone_number;
        this.Date = Date;
        this.Message = Message;
        this.Location = Location;
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

    public List<Double> getLocation() {
        return Location;
    }

    public void setLocation(List<Double> location) {
        Location = location;
    }


}

