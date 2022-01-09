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

public class messagel extends AppCompatActivity {
    EditText editText;
    TextInputLayout co_name,phn_no,dd,msg,loc;
    int year,month,day;
    Button savebtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagel);
        co_name=findViewById(R.id.cname);
        dd=findViewById(R.id.date);
        phn_no=findViewById(R.id.pno);
        msg=findViewById(R.id.message);
        loc=findViewById(R.id.rlocation);
        editText=findViewById(R.id.edate);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_cname=co_name.getEditText().getText().toString();
                String l_dd=dd.getEditText().getText().toString();
                String l_pno=phn_no.getEditText().getText().toString();
                String l_msg=msg.getEditText().getText().toString();
                String l_loc=loc.getEditText().getText().toString();
                if(!l_cname.isEmpty()) {
                    co_name.setError(null);
                    co_name.setEnabled(false);
                    if(!l_dd.isEmpty())
                    {
                        dd.setError(null);
                        dd.setEnabled(false);
                        if(!l_pno.isEmpty())
                        {
                            phn_no.setError(null);
                            phn_no.setEnabled(false);
                            if(!l_msg.isEmpty())
                            {
                                msg.setError(null);
                                msg.setEnabled(false);
                                if(!l_loc.isEmpty())
                                {
                                    loc.setError(null);
                                    loc.setEnabled(false);
                                    firebaseDatabase=FirebaseDatabase.getInstance();
                                    reference=firebaseDatabase.getReference("l_message");
                                    String fl_cname=co_name.getEditText().getText().toString();
                                    String fl_dd=dd.getEditText().getText().toString();
                                    String fl_pno=phn_no.getEditText().getText().toString();
                                    String fl_msg=msg.getEditText().getText().toString();
                                    String fl_loc=loc.getEditText().getText().toString();
                                    locmessagestore locmessagestores = new locmessagestore(fl_cname,fl_pno,fl_dd,fl_msg,fl_loc);
                                    reference.child(fl_cname).setValue(locmessagestores);
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
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(messagel.this, new DatePickerDialog.OnDateSetListener()
                    {
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