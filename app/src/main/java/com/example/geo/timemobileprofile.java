package com.example.geo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class timemobileprofile extends AppCompatActivity {
    EditText editText,editText2;
    TextInputLayout ttittle,dd,ttime;
    RadioGroup rg;
    RadioButton rb;
    int year,month,day,hour,min;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Button savebtn,view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mprofilet);
        ttittle=findViewById(R.id.tittle);
        dd=findViewById(R.id.date);
        rg=findViewById(R.id.rdg);
        ttime=findViewById(R.id.t_time);
        editText=findViewById(R.id.edate);
        editText2=findViewById(R.id.rtime);
        view=findViewById(R.id.showmprofile);
        savebtn=findViewById(R.id.rlsave);
        Calendar calendar=Calendar.getInstance();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_tittle=ttittle.getEditText().getText().toString();
                String t_dd=dd.getEditText().getText().toString();
                String t_time=ttime.getEditText().getText().toString();
                int radio=rg.getCheckedRadioButtonId();
                rb=findViewById(radio);
                if (!t_tittle.isEmpty())
                {
                    ttittle.setError(null);

                    if (!t_dd.isEmpty())
                    {
                        dd.setError(null);

                        if (!t_time.isEmpty())
                        {
                            ttime.setError(null);
                            SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                            String uname=pref.getString("userId","");
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            reference = firebaseDatabase.getReference("user").child(uname).child("time_mobileprofile");
                            String fl_tittle = ttittle.getEditText().getText().toString();
                            String fl_dd = dd.getEditText().getText().toString();
                            String fl_rb=rb.getText().toString();
                            String ft_time=ttime.getEditText().getText().toString();
                            timemobileprofilestore timemobileprofilestores=new timemobileprofilestore(fl_tittle,fl_dd,fl_rb,ft_time);
                            reference.child(fl_tittle).setValue(timemobileprofilestores);
                            Toast.makeText(getApplicationContext(), "Save data Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);

                        }
                        else
                        {
                            ttime.setError("Select Time");
                        }
                    }
                    else
                    {
                        dd.setError("select date");
                    }
                }
                else
                {
                    ttittle.setError("Enter Tittle");
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
                    editText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                }
            },year,month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();

        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),timemobileprofileview.class);
                startActivity(intent);


            }
        });
        ttime.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(timemobileprofile.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour=hourOfDay;
                        min=minute;
                        calendar.set(0,0,0,hour,min);
                        editText2.setText(DateFormat.format("hh:mm:aa",calendar));

                    }
                },12,0,false);
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();

            }
        });
    }
}