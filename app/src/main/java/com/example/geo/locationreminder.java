package com.example.geo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geo.utils.GeoFenceUtil;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class locationreminder extends AppCompatActivity {

    TextInputLayout ltitle, dd, task, rad, loc;
    EditText editText;
    Button savebtn, view;
    int year, month, day;
    long reminderId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<Double> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locreminder);
        reminderId = System.currentTimeMillis();
        ltitle = findViewById(R.id.tittle);
        dd = findViewById(R.id.date);
        task = findViewById(R.id.reminder);
        rad = findViewById(R.id.radius);
        loc = findViewById(R.id.location);
        editText = findViewById(R.id.edate);
        view = findViewById(R.id.showreminder);

        savebtn = findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_tittle = ltitle.getEditText().getText().toString();
                String l_dd = dd.getEditText().getText().toString();
                String l_task = task.getEditText().getText().toString();
                String l_rad = rad.getEditText().getText().toString();
                String l_loc = loc.getEditText().getText().toString();

                if (!l_tittle.isEmpty()) {
                    ltitle.setError(null);
                    if (!l_dd.isEmpty()) {
                        dd.setError(null);

                        if (!l_task.isEmpty()) {
                            task.setError(null);

                            if (!l_rad.isEmpty()) {
                                rad.setError(null);

                                if (!l_loc.isEmpty()) {
                                    loc.setError(null);
                                    SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                    String uname = pref.getString("userId", "");
                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    reference = firebaseDatabase.getReference("user").child(uname).child("location_reminder");
                                    String fl_tittle = ltitle.getEditText().getText().toString();
                                    String fl_dd = dd.getEditText().getText().toString();
                                    String fl_task = task.getEditText().getText().toString();
                                    String fl_rad = rad.getEditText().getText().toString();
                                    String fl_loc = loc.getEditText().getText().toString();

                                    locationreminderstore locreminderstores = new locationreminderstore(fl_tittle, fl_dd, fl_task, fl_rad, location);
                                    locreminderstores.setId((int)reminderId );

                                    new GeoFenceUtil(locationreminder.this).addToGeoFence(
                                            (int) reminderId, location.get(1), location.get(0),1000
                                    );


                                    reference.child(String.valueOf((int)reminderId)).setValue(locreminderstores);
                                    Toast.makeText(getApplicationContext(), "Save data Successfully ", Toast.LENGTH_SHORT).show();


                                    Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                    startActivity(intent);

                                } else {
                                    Snackbar.make(loc,"Select Location",Snackbar.LENGTH_SHORT).show();
                                }
                            } else {
                                rad.setError("Enter radius");
                            }
                        } else {
                            task.setError("Enter reminder ");
                        }
                    } else {
                        Snackbar.make(dd,"Select Date",Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    ltitle.setError("Enter Tittle");
                }
            }
        });
        dd.setEndIconOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            if (!dd.getEditText().getText().toString().isEmpty()) {
                try {
                    Date d = SimpleDateFormat.getDateInstance().parse(dd.getEditText().getText().toString());
                    if (d != null)
                        calendar.setTime(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("RestrictedApi") MaterialStyledDatePickerDialog datePickerDialog = new MaterialStyledDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    editText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                }
            }, year, month, day);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();

        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), locationreminderview.class);
                startActivity(intent);


            }
        });

        loc.setEndIconOnClickListener(v -> {
            Intent intent = new Intent(locationreminder.this, MapsActivity.class);
            if (location != null && !location.isEmpty()) {
                intent.putExtra("lon",location.get(0));
                intent.putExtra("lat", location.get(1));
            }
            startActivityForResult(intent, 102);
        });

        locationreminderstore data = (locationreminderstore) getIntent().getSerializableExtra("data");
        if (data != null) {
            ltitle.getEditText().setText(data.getTittle());
            task.getEditText().setText(data.getReminder());
            rad.getEditText().setText(data.getRadius());
            location = data.getLocation();
            dd.getEditText().setText(data.getDate());
            reminderId = data.getId();
            if (location != null && !location.isEmpty()) {
                setLocationTitle(location.get(1), location.get(0));
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == Activity.RESULT_OK && data != null) {

            double lon = data.getDoubleExtra("longitude", 0);
            double lat = data.getDoubleExtra("latitude", 0);

            ArrayList<Double> listLoc = new ArrayList<>();
            listLoc.add(lon);
            listLoc.add(lat);
            location = listLoc;
            setLocationTitle(lat,lon);
        }
    }

    private void setLocationTitle(Double lat, Double lon) {
        String title = String.valueOf(lon) + "," + String.valueOf(lat);
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                if (!addresses.isEmpty()) {
                    if (addresses.get(0).getMaxAddressLineIndex() > 0) {

                        title = addresses.get(0).getAddressLine(0);

                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loc.getEditText().setText(title);
    }
}