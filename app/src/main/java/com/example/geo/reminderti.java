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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.geo.R;
import com.example.geo.reminderl;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class reminderti extends AppCompatActivity {
    TextInputLayout ltitle,dd,task,ti;
    EditText editText1,editText2;
    Button savebtn;
    int year,month,day,hour,min;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderti);
        ltitle=findViewById(R.id.ti_tittle);
        dd=findViewById(R.id.ti_date);
        task=findViewById(R.id.ti_reminder);
        ti=findViewById(R.id.ti_time);
        editText1=findViewById(R.id.edate);
        editText2=findViewById(R.id.rtime);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_tittle=ltitle.getEditText().getText().toString();
                String t_dd=dd.getEditText().getText().toString();
                String t_task=task.getEditText().getText().toString();
                String t_ti=ti.getEditText().getText().toString();
                if(!t_tittle.isEmpty()) {
                    ltitle.setError(null);
                    ltitle.setEnabled(false);
                    if(!t_dd.isEmpty())
                    {
                        dd.setError(null);
                        dd.setEnabled(false);
                        if(!t_task.isEmpty())
                        {
                            task.setError(null);
                            task.setEnabled(false);

                            if(!t_ti.isEmpty())
                            {
                                ti.setError(null);
                                ti.setEnabled(false);

                                SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                String uname=pref.getString("userId","");
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                reference = firebaseDatabase.getReference("user").child(uname).child("time_reminder");
                                String ft_tittle=ltitle.getEditText().getText().toString();
                                String ft_dd=dd.getEditText().getText().toString();
                                String ft_task=task.getEditText().getText().toString();
                                String ft_ti=ti.getEditText().getText().toString();
                                timereminderstore timereminderstores=new timereminderstore(ft_tittle,ft_dd,ft_task,ft_ti);
                                reference.child(ft_tittle).setValue(timereminderstores);
                                Toast.makeText(getApplicationContext(),"Save data Successfully",Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(getApplicationContext(),dashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                ti.setError("select location");
                            }
                        }


                        else
                        {
                            task.setError("enter reminder ");
                        }
                    }
                    else
                    {
                        dd.setError("select date");
                    }
                }
                else
                {
                    ltitle.setError("Enter Tittle");
                }
            }
        });
        editText1.setOnClickListener(v -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            @SuppressLint("RestrictedApi") MaterialStyledDatePickerDialog datePickerDialog = new MaterialStyledDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editText1.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                }
            },year,month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();

        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(reminderti.this, new TimePickerDialog.OnTimeSetListener() {
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