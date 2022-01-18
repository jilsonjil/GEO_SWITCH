package com.example.geo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class messaget extends AppCompatActivity {
    EditText editText,editText2;
    TextInputLayout co_name,phn_no,dd,msg,tim;
    int year,month,day,hour,min;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaget);
        co_name=findViewById(R.id.cname);
        dd=findViewById(R.id.date);
        phn_no=findViewById(R.id.pno);
        msg=findViewById(R.id.message);
        tim=findViewById(R.id.time);
        editText=findViewById(R.id.edate);
        editText2=findViewById(R.id.ttime);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
      savebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String t_cname=co_name.getEditText().getText().toString();
              String t_dd=dd.getEditText().getText().toString();
              String t_pno=phn_no.getEditText().getText().toString();
              String t_msg=msg.getEditText().getText().toString();
              String t_time=tim.getEditText().getText().toString();
              if(!t_cname.isEmpty()) {
                  co_name.setError(null);
                  co_name.setEnabled(false);
                  if(!t_dd.isEmpty())
                  {
                      dd.setError(null);
                      dd.setEnabled(false);
                      if(!t_pno.isEmpty())
                      {
                          phn_no.setError(null);
                          phn_no.setEnabled(false);
                          if(!t_msg.isEmpty())
                          {
                              msg.setError(null);
                              msg.setEnabled(false);
                              if(!t_time.isEmpty())
                              {
                                  tim.setError(null);
                                  tim.setEnabled(false);
                                  firebaseDatabase=FirebaseDatabase.getInstance();
                                  reference=firebaseDatabase.getReference("t_message");
                                  String ft_cname=co_name.getEditText().getText().toString();
                                  String ft_dd=dd.getEditText().getText().toString();
                                  String ft_pno=phn_no.getEditText().getText().toString();
                                  String ft_msg=msg.getEditText().getText().toString();
                                  String ft_time=tim.getEditText().getText().toString();
                                  timessagestore timessagestores=new timessagestore(ft_cname,ft_pno,ft_dd,ft_msg,ft_time);
                                  reference.child(ft_cname).setValue(timessagestores);
                                  Toast.makeText(getApplicationContext(),"Save data Successfully",Toast.LENGTH_SHORT).show();

                                  Intent intent=new Intent(getApplicationContext(),dashboard.class);
                                  startActivity(intent);
                                  finish();
                              }
                              else
                              {
                                  tim.setError("select Time");
                              }
                          }
                          else
                          {
                              msg.setError("enter message");
                          }
                      }
                      else
                      {
                          phn_no.setError("enter phone number");
                      }
                  }
                  else
                  {
                      dd.setError("select date");
                  }
              }
              else
              {
                  co_name.setError("Enter Contact name");
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
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(messaget.this, new TimePickerDialog.OnTimeSetListener() {
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