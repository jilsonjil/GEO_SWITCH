package com.example.geo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class reminderl extends AppCompatActivity {

    TextInputLayout ltitle,dd,task,rad,loc;
    EditText editText;
    Button savebtn;
    int year,month,day;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderl);
        ltitle=findViewById(R.id.tittle);
        dd=findViewById(R.id.date);
        task=findViewById(R.id.reminder);
        rad=findViewById(R.id.radius);
        loc=findViewById(R.id.location);
        editText=findViewById(R.id.edate);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_tittle=ltitle.getEditText().getText().toString();
                String l_dd=dd.getEditText().getText().toString();
                String l_task=task.getEditText().getText().toString();
                String l_rad=rad.getEditText().getText().toString();
                String l_loc=loc.getEditText().getText().toString();

                if(!l_tittle.isEmpty()) {
                    ltitle.setError(null);
                    ltitle.setEnabled(false);
                    if(!l_dd.isEmpty())
                    {
                        dd.setError(null);
                        dd.setEnabled(false);
                        if(!l_task.isEmpty())
                        {
                            task.setError(null);
                            task.setEnabled(false);
                            if(!l_rad.isEmpty())
                            {
                                rad.setError(null);
                                rad.setEnabled(false);
                                if(!l_loc.isEmpty())
                                {
                                    loc.setError(null);
                                    loc.setEnabled(false);
                                    firebaseDatabase=FirebaseDatabase.getInstance();
                                    reference=firebaseDatabase.getReference("l_reminder");
                                    String fl_tittle=ltitle.getEditText().getText().toString();
                                    String fl_dd=dd.getEditText().getText().toString();
                                    String fl_task=task.getEditText().getText().toString();
                                    String fl_rad=rad.getEditText().getText().toString();
                                    String fl_loc=loc.getEditText().getText().toString();

                                    locreminderstore locreminderstores= new locreminderstore(fl_tittle,fl_dd,fl_task,fl_rad,fl_loc);
                                    reference.child(fl_tittle).setValue(locreminderstores);
                                    Toast.makeText(getApplicationContext(),"Save data Successfully",Toast.LENGTH_SHORT).show();




                                    Intent intent=new Intent(getApplicationContext(),dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    loc.setError("select location");
                                }
                            }
                            else
                            {
                                rad.setError("enter radius");
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
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(reminderl.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
    }
}