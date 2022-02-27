package com.example.geo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.geo.locationconnectivitystore;
import com.example.geo.locationmessagestore;
import com.example.geo.locationmobileprofilestore;
import com.example.geo.locationreminderstore;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private String TAG = "GeoFenceReeiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes
                    .getStatusCodeString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            String reqId = geofencingEvent.getTriggeringGeofences().get(0).getRequestId();
            Log.i(TAG, "Inside geofence::" + reqId);
            SharedPreferences pref = context.getSharedPreferences("mypref", Context.MODE_PRIVATE);
            String uname = pref.getString("userId", "");
            FirebaseDatabase.getInstance().getReference("user").child(uname).child("location_reminder")
                    .child(reqId).get().addOnCompleteListener(dataSnapshot -> {
                if (dataSnapshot.isSuccessful()) {
                    locationreminderstore data = dataSnapshot
                            .getResult()
                            .getValue(locationreminderstore.class);
                    if (data != null) {
                        new NotificationUtil(context)
                                .showNotification("GeoLocation", data.getReminder());
                        removeFence(context, reqId);

                    } else {
                        checkMobileProfile(uname, reqId, context);
                    }
                } else {
                    checkMobileProfile(uname, reqId, context);
                }


            });


        } else {
            // Log the error.
            Log.e(TAG, "Error");
        }
    }


    void checkMobileProfile(String uname, String reqId, Context context) {
        Log.i(TAG, "ReqId::" + reqId);
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("location_mobileprofile")
                .child(reqId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                locationmobileprofilestore data = task.getResult().getValue(locationmobileprofilestore.class);
                if (data != null) {
                    Log.i(TAG, "data notnull in checkmobileprpofile");
                    String profile = data.getProfile();
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    if (profile.equals("Silent Mode"))
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    else if (profile.equals("Vibration Mode"))
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                } else {
                    checkMessage(uname, reqId, context);
                    Log.e(TAG, "data null in checkmobileprpofile");
                }
            } else {
                checkMessage(uname, reqId, context);
                Log.e(TAG, "task failed in checkmobileprpofile");
            }
        });

    }

    void checkMessage(String uname, String reqId, Context context) {
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("location_message")
                .child(reqId)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                locationmessagestore data = task.getResult().getValue(locationmessagestore.class);
                if (data != null) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(data.getPhone_number(), null, data.getMessage(), null, null);
                    removeFence(context,reqId);
                } else {
                    Log.e(TAG, "data null in checkMessage");
                    checkConnectivity(uname, reqId, context);
                }
            } else {
                Log.e(TAG, "task failed in checkMessage");
                checkConnectivity(uname, reqId, context);
            }
        });
    }

    void checkConnectivity(String uname, String reqId, Context context) {
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("location_connectivity")
                .child(reqId)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                locationconnectivitystore data = task.getResult().getValue(locationconnectivitystore.class);
                if (data != null) {
                    WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                    removeFence(context, reqId);
                    } else {
                    Log.e(TAG, "data null in checkConnectivity");
                }
            } else {
                Log.e(TAG, "task failed in checkConnectivity");
            }
        });
    }

    void removeFence(Context context, String reqId) {
        GeoFenceUtil geoFenceUtil = new GeoFenceUtil(context);
        geoFenceUtil.removeGeoFence(reqId);
    }
}
