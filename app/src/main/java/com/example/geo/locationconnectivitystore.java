package com.example.geo;

import java.io.Serializable;
import java.util.List;

public class locationconnectivitystore implements Serializable {
    long id;
    String Tittle;
    String Date;



    List<Double> Location;

    public locationconnectivitystore() {
    }

    public locationconnectivitystore(String Tittle, String Date, List<Double> Location) {
        this.Tittle = Tittle;
        this.Date = Date;

        this.Location = Location;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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



    public List<Double> getLocation() {
        return Location;
    }

    public void setLocation(List<Double> location) {
        Location = location;
    }
}
