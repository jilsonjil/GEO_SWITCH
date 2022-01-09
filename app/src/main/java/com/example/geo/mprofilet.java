package com.example.geo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class mprofilet extends AppCompatActivity {
    EditText editText,editText2;
    TextInputLayout ttittle,dd,ttime;
    RadioGroup rg;
    RadioButton rb;
    int year,month,day,hour,min;
    Button savebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mprofilet);
        ttittle=findViewById(R.id.tittle);
        dd=findViewById(R.id.date);
        rg=findViewById(R.id.rdg);
        ttime=findViewById(R.id.t_time);
        editText=findViewById(R.id.rtime);
        Calendar calendar=Calendar.getInstance();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_tittle=ttittle.getEditText().getText().toString();
                String l_dd=dd.getEditText().getText().toString();
                String l_loc=ttime.getEditText().getText().toString();
                int radio=rg.getCheckedRadioButtonId();
                rb=findViewById(radio);
                if (!l_tittle.isEmpty())
                {
                    ttittle.setError(null);
                    ttittle.setEnabled(false);
                    if (!l_dd.isEmpty())
                    {
                        dd.setError(null);
                        dd.setEnabled(false);
                        if (!l_loc.isEmpty())
                        {
                            ttime.setError(null);
                            ttime.setEnabled(false);
                            Intent intent=new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            ttime.setError("Select Location");
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
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(mprofilet.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(mprofilet.this, new TimePickerDialog.OnTimeSetListener() {
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