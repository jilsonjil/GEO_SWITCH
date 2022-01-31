package com.example.geo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class locationconnectivity extends AppCompatActivity {
    TextInputLayout ltittle,dd,loc,add;
    EditText editText;
    int year,month,day;
    long coId;
    Button savebtn,view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<Double> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectivityl);
        ltittle = findViewById(R.id.tittle);
        dd = findViewById(R.id.date);
        loc = findViewById(R.id.location);
        coId = System.currentTimeMillis();
        add = findViewById(R.id.address);
        editText = findViewById(R.id.edate);
        Calendar calendar = Calendar.getInstance();
        view = findViewById(R.id.showconnect);
        savebtn = findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_tittle = ltittle.getEditText().getText().toString();
                String l_dd = dd.getEditText().getText().toString();
                String l_loc = loc.getEditText().getText().toString();
                String l_add = add.getEditText().getText().toString();
                if (!l_tittle.isEmpty()) {
                    ltittle.setError(null);

                    if (!l_dd.isEmpty()) {
                        dd.setError(null);

                        if (!l_add.isEmpty()) {
                            add.setError(null);

                            if (!l_loc.isEmpty()) {
                                loc.setError(null);

                                SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                String uname = pref.getString("userId", "");
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                reference = firebaseDatabase.getReference("user").child(uname).child("location_connectivity");
                                String fl_tittle = ltittle.getEditText().getText().toString();
                                String fl_dd = dd.getEditText().getText().toString();
                                String fl_loc = loc.getEditText().getText().toString();
                                String fl_add = add.getEditText().getText().toString();
                                locationconnectivitystore locconnectivitystores = new locationconnectivitystore(fl_tittle, fl_dd, fl_add, location);
                                locconnectivitystores.setId(coId);
                                reference.child(String.valueOf(coId)).setValue(locconnectivitystores);
                                Toast.makeText(getApplicationContext(), "Save data Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                startActivity(intent);


                            } else {
                                loc.setError("Select location");
                            }
                        } else {
                            add.setError("Enter address");
                        }
                    } else {
                        dd.setError("Select date");
                    }
                } else {
                    ltittle.setError("Enter tittle");

                }
            }
        });

        dd.setEndIconOnClickListener(v -> {
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
                Intent intent = new Intent(getApplicationContext(), locationconnectivityview.class);
                startActivity(intent);


            }
        });
        loc.setEndIconOnClickListener(v -> {
            Intent intent = new Intent(locationconnectivity.this, MapsActivity.class);
            startActivityForResult(intent, 102);
        });
        locationconnectivitystore data = (locationconnectivitystore) getIntent().getSerializableExtra("data");
        if (data != null) {
            location = data.getLocation();
            dd.getEditText().setText(data.getDate());
            add.getEditText().setText(data.getAddress());

            ltittle.getEditText().setText(data.getTittle());
            if (location != null && !location.isEmpty()) {
                setLocationTitle(location.get(1), location.get(0));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 102 && resultCode == Activity.RESULT_OK && data != null) {

                double lon = data.getDoubleExtra("longitude", 0);
                double lat = data.getDoubleExtra("latitude", 0);

                ArrayList<Double> listLoc = new ArrayList<>();
                listLoc.add(lon);
                listLoc.add(lat);
                location = listLoc;
                setLocationTitle(lat, lon);
            }
        }
        private void setLocationTitle(Double lat, Double lon) {
            String title = String.valueOf(lon) + "," + String.valueOf(lat);
            if (Geocoder.isPresent()) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat,lon,1);
                    if (!addresses.isEmpty()) {
                        if (addresses.get(0).getMaxAddressLineIndex() > 0) {
                            loc.getEditText().setText(
                                    addresses.get(0).getAddressLine(0)
                            );
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


