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

import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class mprofilel extends AppCompatActivity {
    EditText editText;
    TextInputLayout ltittle,dd,lloc;
    RadioGroup rg;
    RadioButton rb;
    int year,month,day;
    Button savebtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<Double> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mprofilel);
        ltittle=findViewById(R.id.tittle);
        dd=findViewById(R.id.date);
        lloc=findViewById(R.id.rlocation);
        editText=findViewById(R.id.edate);
        rg=findViewById(R.id.rdg);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_tittle=ltittle.getEditText().getText().toString();
                String l_dd=dd.getEditText().getText().toString();
                String l_loc=lloc.getEditText().getText().toString();
                int radio=rg.getCheckedRadioButtonId();
                rb=findViewById(radio);
                String l_rb=rb.getText().toString();
                if (!l_tittle.isEmpty())
                {
                    ltittle.setError(null);
                    ltittle.setEnabled(false);
                    if (!l_dd.isEmpty())
                    {
                        dd.setError(null);
                       dd.setEnabled(false);
                        if (!l_loc.isEmpty())
                        {
                            lloc.setError(null);
                            lloc.setEnabled(false);
                            SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                            String uname=pref.getString("userId","");
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            reference = firebaseDatabase.getReference("user").child(uname).child("location_reminder");
                            String fl_tittle = ltittle.getEditText().getText().toString();
                            String fl_dd = dd.getEditText().getText().toString();
                            String fl_rb=rb.getText().toString();

                            locmprofilestore locmprofiles = new locmprofilestore(fl_tittle,fl_rb,fl_dd,location);
                            reference.child(fl_tittle).setValue(locmprofiles);
                            Toast.makeText(getApplicationContext(), "Save data Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            lloc.setError("Select Location");
                        }
                    }
                    else
                    {
                      dd.setError("select date");
                    }
                }
                else
                {
                    ltittle.setError("Enter Tittle");
                }
            }
        });
        editText.setOnClickListener(v -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            @SuppressLint("RestrictedApi") MaterialStyledDatePickerDialog datePickerDialog = new MaterialStyledDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                }
            },year,month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();

        });
        lloc.setEndIconOnClickListener(v -> {
            Intent intent = new Intent(mprofilel.this, MapsActivity.class);
            startActivityForResult(intent, 102);
        });
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
            lloc.getEditText().setText(String.valueOf(lon)+","+String.valueOf(lat));
        }
    }

}