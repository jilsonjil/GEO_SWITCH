package com.example.geo.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.geo.R;

public class NotificationUtil {
    Context context;

    public NotificationUtil(Context context) {
        this.context = context;
    }

    public void showNotification(String msgTitle, String msgTtext) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context, "alarmNot")
                .setContentText(msgTtext)
                .setContentTitle(msgTitle)
                .setSmallIcon(R.drawable.menu)
                .build();
        NotificationManagerCompat.from(context).notify(
                1, notification
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel() {
        NotificationManagerCompat.from(context)
                .createNotificationChannel(new NotificationChannel("alarmNot","Alarm",NotificationManager.IMPORTANCE_HIGH));
    }
}
