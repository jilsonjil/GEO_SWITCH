package com.example.geo;

import java.io.Serializable;
import java.util.List;

public class locationreminderstore implements Serializable {
    long id;
    String Tittle;
    String Date;
    String Reminder;
    String Radius;
    List<Double> Location;
    public locationreminderstore() {
    }



    public locationreminderstore(String Tittle, String Date, String Reminder, String Radius, List<Double> Location) {
        this.Tittle = Tittle;
        this.Date = Date;
        this.Reminder = Reminder;
        this.Radius = Radius;
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

    public void setTittle(String Tittle) {
        this.Tittle = Tittle;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getReminder() {
        return Reminder;
    }

    public void setReminder(String Reminder ) {
        this.Reminder = Reminder;
    }

    public String getRadius() {
        return Radius;
    }

    public void setRadius(String Radius) {
        this.Radius = Radius;
    }

    public List<Double> getLocation() {
        return Location;
    }

    public void setLocation(List<Double> Location) {
        this.Location = Location;
    }
}
