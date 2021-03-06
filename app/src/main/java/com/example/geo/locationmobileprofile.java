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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.geo.utils.GeoFenceUtil;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class locationmobileprofile extends AppCompatActivity {
    EditText editText;
    TextInputLayout ltittle,dd,lloc;
    RadioGroup rg;
    RadioButton rb;
    int year,month,day;
    long profileid;
    Button savebtn,view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<Double> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mprofilel);
        ltittle=findViewById(R.id.tittle);
        profileid=System.currentTimeMillis();
       // dd=findViewById(R.id.date);
        lloc=findViewById(R.id.rlocation);
        editText=findViewById(R.id.edate);
        rg=findViewById(R.id.rdg);
        view=findViewById(R.id.showmprofile);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_tittle=ltittle.getEditText().getText().toString();
//                String l_dd=dd.getEditText().getText().toString();
                String l_loc=lloc.getEditText().getText().toString();
                int radio=rg.getCheckedRadioButtonId();
                rb=findViewById(radio);
                String l_rb=rb.getText().toString();
                if (!l_tittle.isEmpty())
                {
                    ltittle.setError(null);

                  //  if (!l_dd.isEmpty())
                   // {
                      //  dd.setError(null);

                        if (!l_loc.isEmpty())
                        {
                            lloc.setError(null);

                            SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                            String uname=pref.getString("userId","");
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            reference = firebaseDatabase.getReference("user").child(uname).child("location_mobileprofile");
                            String fl_tittle = ltittle.getEditText().getText().toString();
//                            String fl_dd = dd.getEditText().getText().toString();
                            String fl_rb=rb.getText().toString();
                            String date = new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime());

                            locationmobileprofilestore locmprofiles = new locationmobileprofilestore(fl_tittle,date,fl_rb,location);
                            locmprofiles.setId((int)profileid);
                            reference.child(String.valueOf(((int) profileid))).setValue(locmprofiles);
                            new GeoFenceUtil(locationmobileprofile.this).addToGeoFence(
                                    (int) profileid, location.get(1), location.get(0),1000
                            );
                            Toast.makeText(getApplicationContext(), "Save data Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Snackbar.make(lloc,"Select Location",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                   // else
                  //  {
                  //      Snackbar.make(dd,"Select Date",Snackbar.LENGTH_SHORT).show();
                 //   }
               // }
                else
                {
                    ltittle.setError("Enter Tittle");
                }
            }
        });
        /*dd.setEndIconOnClickListener(v -> {
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

        });*/

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),locationmobileprofileview.class);
                startActivity(intent);

            }
        });
        lloc.setEndIconOnClickListener(v -> {
            Intent intent = new Intent(locationmobileprofile.this, MapsActivity.class);
            startActivityForResult(intent, 102);
        });
        locationmobileprofilestore data=(locationmobileprofilestore) getIntent().getSerializableExtra("data");
        if(data !=null)
        {
            ltittle.getEditText().setText(data.getTittle());
//            dd.getEditText().setText(data.getDate());
            profileid=data.getId();
            location=data.getLocation();
            if (data.getProfile().equals("Silent Mode")) {
                rg.check(R.id.r2);
            }
            if (location != null && !location.isEmpty()) {
                setLocationTitle(location.get(1),location.get(0));
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
            setLocationTitle(lat, lon);
        }
    }
    private void setLocationTitle(Double lat, Double lon) {
        String title=String.valueOf(lon) + "," + String.valueOf(lat);
            if (Geocoder.isPresent()) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat,lon,1);
                    if (!addresses.isEmpty()) {
                        if (addresses.get(0).getMaxAddressLineIndex() > 0) {
                            lloc.getEditText().setText(
                                    addresses.get(0).getAddressLine(0)
                            );
                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            lloc.getEditText().setText(title);
        }
    }

