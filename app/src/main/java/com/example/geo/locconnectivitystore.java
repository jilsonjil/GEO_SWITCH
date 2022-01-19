package com.example.geo;

import java.util.List;

public class locconnectivitystore {
    String Tittle;
    String Date;



    String Address;
    List<Double> Location;

    public locconnectivitystore() {
    }

    public locconnectivitystore(String Tittle, String Date, String Address, List<Double> Location) {
        this.Tittle = Tittle;
        this.Date = Date;
        this.Address = Address;
        this.Location = Location;
    }
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

    public List<Double> getLocation() {
        return Location;
    }

    public void setLocation(List<Double> location) {
        Location = location;
    }
}
