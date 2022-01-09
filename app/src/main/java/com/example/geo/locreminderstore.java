package com.example.geo;

public class locreminderstore {
    String Tittle;
    String Date;
    String Reminder;
    String Radius;
    String Location;
    public locreminderstore() {
    }



    public locreminderstore(String Tittle, String Date, String Reminder, String Radius, String Location) {
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }
}
