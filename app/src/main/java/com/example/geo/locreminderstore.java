package com.example.geo;

import java.util.List;

public class locreminderstore {
    String Tittle;
    String Date;
    String Reminder;
    String Radius;
    List<Double> Location;
    public locreminderstore() {
    }



    public locreminderstore(String Tittle, String Date, String Reminder, String Radius, List<Double> Location) {
        this.Tittle = Tittle;
        this.Date = Date;
        this.Reminder = Reminder;
        this.Radius = Radius;
        this.Location = Location;
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
