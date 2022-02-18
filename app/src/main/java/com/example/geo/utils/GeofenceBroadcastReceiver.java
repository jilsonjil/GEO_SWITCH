package com.example.geo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.geo.locationmessagestore;
import com.example.geo.locationreminderstore;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.tasks.OnSuccessListener;
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
            Log.i(TAG, "Inside geofence::"+ reqId);
            SharedPreferences pref = context.getSharedPreferences("mypref", Context.MODE_PRIVATE);
            String uname=pref.getString("userId","");
            FirebaseDatabase.getInstance().getReference("user").child(uname).child("location_reminder")
                    .child(reqId).get().addOnSuccessListener(dataSnapshot -> {
                locationreminderstore data = dataSnapshot.getValue(locationreminderstore.class);
                        if (data != null) {
                            new NotificationUtil(context)
                                    .showNotification("GeoLocation",data.getReminder());
                        }

                    });
        } else {
            // Log the error.
            Log.e(TAG, "Error");
        }
    }
}
