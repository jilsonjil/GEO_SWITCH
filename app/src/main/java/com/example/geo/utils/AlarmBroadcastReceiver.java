package com.example.geo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.SmsManager;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String reminder = intent.getStringExtra("reminder");
        String profile = intent.getStringExtra("profile");
        boolean isSms = intent.getBooleanExtra("isSms", false);
        if (reminder != null) {
            new NotificationUtil(context).showNotification(
                    "GeoSwitch", reminder
            );
        }
        if (profile != null) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (profile.equals("Silent Mode"))
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            else if (profile.equals("Vibration Mode"))
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

        }

        if (isSms)
        {
            String number = intent.getStringExtra("contact");
            String content = intent.getStringExtra("content");
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, content, null, null);
        }
    }
}
