package com.example.geo;

public class locconnectivitystore {
    String Tittle;
    String Date;
    String Address;

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    String Location;

    public locconnectivitystore() {
    }

    public locconnectivitystore(String tittle, String date, String address, String location) {
        Tittle = tittle;
        Date = date;
        Address = address;
        Location = location;
    }
}
