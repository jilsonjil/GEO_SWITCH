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


public class connectivityl extends AppCompatActivity {
    TextInputLayout ltittle,dd,loc,add;
    EditText editText;
    int year,month,day;
    Button savebtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectivityl);
        ltittle=findViewById(R.id.tittle);
        dd=findViewById(R.id.date);
        loc=findViewById(R.id.rlocation);
        add=findViewById(R.id.address);
        editText=findViewById(R.id.edate);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_tittle = ltittle.getEditText().getText().toString();
                String l_dd = dd.getEditText().getText().toString();
                String l_loc = loc.getEditText().getText().toString();
                String l_add = add.getEditText().getText().toString();
                if (!l_tittle.isEmpty()) {
                    ltittle.setError(null);
                    ltittle.setEnabled(false);
                    if(!l_dd.isEmpty())
                    {
                    dd.setError(null);
                    dd.setErrorEnabled(false);
                    if(!l_add.isEmpty())
                    {
                        add.setError(null);
                        add.setEnabled(false);
                        if(!l_loc.isEmpty())

                        {
                            loc.setError(null);
                            loc.setEnabled(false);
                            firebaseDatabase=FirebaseDatabase.getInstance();
                            reference=firebaseDatabase.getReference("l_connectivity");
                            String fl_tittle = ltittle.getEditText().getText().toString();
                            String fl_dd = dd.getEditText().getText().toString();
                            String fl_loc = loc.getEditText().getText().toString();
                            String fl_add = add.getEditText().getText().toString();
                            locconnectivitystore locconnectivitystores=new locconnectivitystore(fl_tittle,fl_dd,fl_add,fl_loc);
                            reference.child(fl_tittle).setValue(locconnectivitystores);
                            Toast.makeText(getApplicationContext(),"Save data Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            loc.setError("Select location");
                        }
                     }
                    else{
                        add.setError("Enter address");
                    }
                    }
                    else
                    {
                        dd.setError("Select date");
                    }
                }
                else {
                    ltittle.setError("Enter tittle");

                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(connectivityl.this, new DatePickerDialog.OnDateSetListener() {
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