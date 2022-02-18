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

public class timereminder extends AppCompatActivity {
    TextInputLayout ltitle, dd, task, ti;
    EditText editText1, editText2;
    Button savebtn, view;
    long reminderId;
    int year, month, day, hour, min;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderti);
        ltitle = findViewById(R.id.ti_tittle);
        dd = findViewById(R.id.ti_date);
        reminderId = System.currentTimeMillis();
        task = findViewById(R.id.ti_reminder);
        ti = findViewById(R.id.ti_time);
        editText1 = findViewById(R.id.edate);
        editText2 = findViewById(R.id.rtime);
        view = findViewById(R.id.showreminder);
        Calendar calendar = Calendar.getInstance();
        savebtn = findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_tittle = ltitle.getEditText().getText().toString();
                String t_dd = dd.getEditText().getText().toString();
                String t_task = task.getEditText().getText().toString();
                String t_ti = ti.getEditText().getText().toString();
                if (!t_tittle.isEmpty()) {
                    ltitle.setError(null);

                    if (!t_dd.isEmpty()) {
                        dd.setError(null);

                        if (!t_task.isEmpty()) {
                            task.setError(null);


                            if (!t_ti.isEmpty()) {
                                ti.setError(null);


                                SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                String uname = pref.getString("userId", "");
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                reference = firebaseDatabase.getReference("user").child(uname).child("time_reminder");
                                String ft_tittle = ltitle.getEditText().getText().toString();
                                String ft_dd = dd.getEditText().getText().toString();
                                String ft_task = task.getEditText().getText().toString();
                                String ft_ti = ti.getEditText().getText().toString();
                                timereminderstore timereminderstores = new timereminderstore(ft_tittle, ft_dd, ft_task, ft_ti);
                                timereminderstores.setId(reminderId);
                                reference.child(String.valueOf(reminderId)).setValue(timereminderstores);

                                try {
                                    Date date = new SimpleDateFormat("MMM d, yyyy hh:mm:a")
                                            .parse(timereminderstores.getDate() + " " + timereminderstores.getTime());
                                    new AlarmUtil(getApplicationContext()).scheduleReminder(
                                            timereminderstores.getReminder(), date.getTime(), (int) timereminderstores.getId()
                                    );
                                } catch (ParseException e) {
                                    Toast.makeText(getApplicationContext(), "Date parse error", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                                Toast.makeText(getApplicationContext(), "Save data Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                startActivity(intent);

                            } else {
                                Snackbar.make(ti, "Select Time", Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            task.setError("Enter reminder ");
                        }
                    } else {
                        Snackbar.make(dd, "Select Date", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    ltitle.setError("Enter Tittle");
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
                    editText1.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();

        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), timereminderview.class);
                startActivity(intent);

            }
        });
        timereminderstore data = (timereminderstore) getIntent().getSerializableExtra("data");
        if (data != null) {
            ltitle.getEditText().setText(data.getTittle());
            task.getEditText().setText(data.getReminder());
            ti.getEditText().setText(data.getTime());
            dd.getEditText().setText(data.getDate());
            reminderId = data.getId();

        }
        ti.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(timereminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //calendar.set(0,0,0,hour,min);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                            //editText2.setError("Select Future Time");
                            Snackbar.make(editText2, "Select upcoming time", Snackbar.LENGTH_SHORT).show();
                        } else {
                            //editText2.setError("");
                            hour = hourOfDay;
                            min = minute;
                            editText2.setText(DateFormat.format("hh:mm:aa", calendar));
                        }


                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(hour, min);
                timePickerDialog.show();

            }
        });
    }

}