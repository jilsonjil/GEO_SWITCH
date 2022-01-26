package com.example.geo;

import java.io.Serializable;
import java.util.List;

public class locationmobileprofilestore implements Serializable {
    long id;
    String Tittle;
    List<Double> Location;
    String Date;
    String Profile;

    public locationmobileprofilestore() {
    }


    public locationmobileprofilestore(String Tittle, String Date, String Profile, List<Double> Location) {
        this.Tittle = Tittle;
        this.Date = Date;
        this.Profile = Profile;
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

    public List<Double> getLocation() {
        return Location;
    }

    public void setLocation(List<Double> location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }



}
