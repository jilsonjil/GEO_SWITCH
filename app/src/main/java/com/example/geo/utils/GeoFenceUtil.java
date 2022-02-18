package com.example.geo.utils;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class GeoFenceUtil {

    Activity context;
    private GeofencingClient geofencingClient;

    public GeoFenceUtil(Activity context) {
        this.context = context;
        geofencingClient = LocationServices.getGeofencingClient(context);
    }

    public void addToGeoFence(int id, double latitude, double longitude, int radius) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("GEOFENCE", "Permisson false");
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},112);
            return;
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("GEOFENCE", "Permission background false");
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},112);
            return;
        }
        Log.d("GEOFENCE", "Permisson true");
        geofencingClient.addGeofences(getGeofencingRequest(new Geofence.Builder()
                        .setRequestId(String.valueOf(id))

                        .setCircularRegion(
                                latitude,
                                longitude,
                                radius
                        )
                        .setExpirationDuration(1000 * 60 * 60 * 24)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build()), getGeofencePendingIntent(id)
        ).addOnCompleteListener(task -> {
                Log.d("GEOFENCE", String.valueOf(task.isSuccessful()));
            if (!task.isSuccessful())
                Log.e("GEOFENCE","",task.getException());
        });
    }

    private GeofencingRequest getGeofencingRequest(Geofence data) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        ArrayList<Geofence> list = new ArrayList<>();
        list.add(data);
        builder.addGeofences(list);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent(int code) {
        Intent intent = new Intent(context, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getBroadcast(context, code, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }
}
