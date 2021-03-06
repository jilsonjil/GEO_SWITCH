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

import com.example.geo.utils.AlarmUtil;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timemessage extends AppCompatActivity {
    EditText editText,editText2;
    TextInputLayout co_name,phn_no,dd,msg,tim;
    long messageId;
    int year,month,day,hour,min;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Button savebtn,view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaget);
        co_name=findViewById(R.id.cname);
        dd=findViewById(R.id.date);
        messageId=System.currentTimeMillis();
        phn_no=findViewById(R.id.pno);
        msg=findViewById(R.id.message);
        tim=findViewById(R.id.time);
        editText=findViewById(R.id.edate);
        editText2=findViewById(R.id.ttime);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
        view=findViewById(R.id.showmsg);
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

                  if(!t_dd.isEmpty())
                  {
                      dd.setError(null);

                      if(!t_pno.isEmpty()&&t_pno.matches("^(\\+\\d{2}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"))
                      {
                          phn_no.setError(null);

                          if(!t_msg.isEmpty())
                          {
                              msg.setError(null);

                              if(!t_time.isEmpty())
                              {
                                  tim.setError(null);

                                  SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                  String uname=pref.getString("userId","");
                                  firebaseDatabase = FirebaseDatabase.getInstance();
                                  reference = firebaseDatabase.getReference("user").child(uname).child("time_message");
                                  String ft_cname=co_name.getEditText().getText().toString();
                                  String ft_dd=dd.getEditText().getText().toString();
                                  String ft_pno=phn_no.getEditText().getText().toString();
                                  String ft_msg=msg.getEditText().getText().toString();
                                  String ft_time=tim.getEditText().getText().toString();
                                  timemessagestore timessagestores=new timemessagestore(ft_cname,ft_pno,ft_dd,ft_msg,ft_time);
                                  timessagestores.setId(messageId);
                                  reference.child(String.valueOf(messageId)).setValue(timessagestores);

                                  try {
                                      Date date = new SimpleDateFormat("MMM d, yyyy hh:mm:a")
                                              .parse(timessagestores.getDate() + " " + timessagestores.getTime());
                                      new AlarmUtil(getApplicationContext()).scheduleSms(
                                              timessagestores.getPhone_number(),ft_msg, date.getTime(), (int) timessagestores.getId()
                                      );
                                  } catch (ParseException e) {
                                      Toast.makeText(getApplicationContext(), "Date parse error", Toast.LENGTH_SHORT).show();
                                      e.printStackTrace();
                                  }

                                  Toast.makeText(getApplicationContext(),"Save data Successfully",Toast.LENGTH_SHORT).show();

                                  Intent intent=new Intent(getApplicationContext(),dashboard.class);
                                  startActivity(intent);

                              }
                              else
                              {
                                  Snackbar.make(tim,"Select Time",Snackbar.LENGTH_SHORT).show();
                              }
                          }
                          else
                          {
                              msg.setError("Enter message");
                          }
                      }
                      else
                      {
                          phn_no.setError("Enter valid phone number");
                      }
                  }
                  else
                  {
                      Snackbar.make(dd,"Select Date",Snackbar.LENGTH_SHORT).show();
                  }
              }
              else
              {
                  co_name.setError("Enter Contact name");
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
            },year,month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();

        });
        timemessagestore data=(timemessagestore) getIntent().getSerializableExtra("data");
        if(data !=null)
        {
            messageId=data.getId();
           tim.getEditText().setText(data.getTime());
            dd.getEditText().setText(data.getDate());
            msg.getEditText().setText(data.getMessage());
            phn_no.getEditText().setText(data.getPhone_number());
            co_name.getEditText().setText(data.getContact_name());

            }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),timemessageview.class);
                startActivity(intent);

            }
        });
        tim.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(timemessage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //calendar.set(0,0,0,hour,min);
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                            //editText2.setError("Select Future Time");
                            Snackbar.make(editText2,"Select upcoming time",Snackbar.LENGTH_SHORT).show();
                        } else {
                            //editText2.setError("");
                            hour=hourOfDay;
                            min=minute;
                            editText2.setText(DateFormat.format("hh:mm:aa",calendar));
                        }


                    }
                },12,0,false);
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();

            }
        });
    }
}