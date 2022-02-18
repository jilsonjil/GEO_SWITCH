package com.example.geo.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.AlarmManagerCompat;

public class AlarmUtil {
    Context context;

    public AlarmUtil(Context context) {
        this.context = context;
    }

    public void scheduleReminder(String reminder, long time, int reminderId) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("reminder",reminder);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminderId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager, AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void scheduleProfileChange(String profile, long time, int reminderId) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("profile",profile);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminderId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager, AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void scheduleSms(String contact, String content, long time, int id) {

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("isSms",true);
        intent.putExtra("content",content);
        intent.putExtra("contact",contact);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager, AlarmManager.RTC_WAKEUP, time, pendingIntent);

    }
}
