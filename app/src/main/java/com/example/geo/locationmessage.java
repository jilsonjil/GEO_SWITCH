package com.example.geo;

import androidx.activity.result.contract.ActivityResultContracts;
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
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class locationmessage extends AppCompatActivity {
    private static final int RESULT_PICK_CONTACT=1;
    EditText editText;
    TextInputLayout co_name, phn_no, dd, msg, loc;
    int year, month, day;
    long messageId;
    Button savebtn, view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<Double> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagel);
        co_name = findViewById(R.id.cname);
        dd = findViewById(R.id.date);
        phn_no = findViewById(R.id.pno);
        messageId = System.currentTimeMillis();
        msg = findViewById(R.id.message);
        loc = findViewById(R.id.rlocation);
        editText = findViewById(R.id.edate);
        Calendar calendar = Calendar.getInstance();
        view = findViewById(R.id.showmsg);
        savebtn = findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_cname = co_name.getEditText().getText().toString();
                //String l_dd=dd.getEditText().getText().toString();
                String l_pno = phn_no.getEditText().getText().toString();
                String l_msg = msg.getEditText().getText().toString();
                String l_loc = loc.getEditText().getText().toString();
                if (!l_cname.isEmpty()) {
                    co_name.setError(null);

                    //if(!l_dd.isEmpty())
                    // {
                    //   dd.setError(null);

                    if (!l_pno.isEmpty() && l_pno.matches("^(\\+\\d{2}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")) {
                        phn_no.setError(null);

                        if (!l_msg.isEmpty()) {
                            msg.setError(null);

                            if (!l_loc.isEmpty()) {
                                loc.setError(null);
                                loc.setEnabled(false);
                                SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                String uname = pref.getString("userId", "");
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                reference = firebaseDatabase.getReference("user").child(uname).child("location_message");
                                String fl_cname = co_name.getEditText().getText().toString();
                                //String fl_dd=dd.getEditText().getText().toString();
                                String fl_pno = phn_no.getEditText().getText().toString();
                                String fl_msg = msg.getEditText().getText().toString();
                                String fl_loc = loc.getEditText().getText().toString();
                                String date = new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime());
                                locationmessagestore locmessagestores = new locationmessagestore(fl_cname, fl_pno, date, fl_msg, location);
                                locmessagestores.setId((int) messageId);
                                reference.child(String.valueOf((int) messageId)).setValue(locmessagestores);

                                new GeoFenceUtil(locationmessage.this).addToGeoFence(
                                        (int) messageId, location.get(1), location.get(0),1000
                                );

                                Toast.makeText(getApplicationContext(), "Save data Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                startActivity(intent);

                            } else {
                                Snackbar.make(loc, "Select Location", Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            msg.setError("Enter message");
                        }
                    } else {
                        phn_no.setError("Enter valid phone number");
                    }
                }
                //  else
                // {
                //     Snackbar.make(dd,"Select Date",Snackbar.LENGTH_SHORT).show();
                // }
                // }
                else {
                    co_name.setError("Enter Contact name");
                }

            }
        });
/*        dd.setEndIconOnClickListener(v -> {
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
                Intent intent = new Intent(getApplicationContext(), locationmessageview.class);
                startActivity(intent);

            }
        });
        loc.setEndIconOnClickListener(v -> {
            Intent intent = new Intent(locationmessage.this, MapsActivity.class);
            startActivityForResult(intent, 102);
        });
       phn_no.setEndIconOnClickListener(v -> {
            Intent intent =new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent,RESULT_PICK_CONTACT);
        });


        locationmessagestore data = (locationmessagestore) getIntent().getSerializableExtra("data");
        if (data != null) {
            location = data.getLocation();
            messageId = data.getId();
            //   dd.getEditText().setText(data.getDate());
            msg.getEditText().setText(data.getMessage());
            phn_no.getEditText().setText(data.getPhone_number());
            co_name.getEditText().setText(data.getContact_name());
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
            setLocationTitle(lat, lon);
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