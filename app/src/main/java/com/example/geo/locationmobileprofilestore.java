package com.example.geo;

import java.util.List;

public class locationmobileprofilestore {
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
