package com.example.geo;

import java.io.Serializable;

public class timereminderstore implements Serializable {
    long id;
    String Tittle;
    String Date;
    String Reminder;
    String Time;
    public timereminderstore() {
    }



    public timereminderstore(String Tittle, String Date, String Reminder, String Time) {
        this.Tittle = Tittle;
        this.Date = Date;
        this.Reminder = Reminder;
        this.Time = Time;
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



    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
}
