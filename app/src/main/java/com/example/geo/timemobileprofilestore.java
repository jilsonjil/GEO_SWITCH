package com.example.geo;

import java.io.Serializable;

public class timemobileprofilestore implements Serializable {
    long id;
    String Tittle;
    String Date;
    String Profile;
    String Time;

    public timemobileprofilestore() {
    }




    public timemobileprofilestore(String tittle, String date, String profile, String time) {
        Tittle = tittle;
        Date = date;
        Profile = profile;
        Time = time;
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

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

}

