package com.example.geo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class mprofilel extends AppCompatActivity {
    EditText editText;
    TextInputLayout ltittle,dd,lloc;
    RadioGroup rg;
    RadioButton rb;
    int year,month,day;
    Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mprofilel);
        ltittle=findViewById(R.id.tittle);
        dd=findViewById(R.id.date);
        lloc=findViewById(R.id.rlocation);
        editText=findViewById(R.id.edate);
        rg=findViewById(R.id.rdg);
        Calendar calendar=Calendar.getInstance();
        savebtn=findViewById(R.id.rlsave);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_tittle=ltittle.getEditText().getText().toString();
                String l_dd=dd.getEditText().getText().toString();
                String l_loc=lloc.getEditText().getText().toString();
                int radio=rg.getCheckedRadioButtonId();
                rb=findViewById(radio);
                String l_rb=rb.getText().toString();
                if (!l_tittle.isEmpty())
                {
                    ltittle.setError(null);
                    ltittle.setEnabled(false);
                    if (!l_dd.isEmpty())
                    {
                        dd.setError(null);
                       dd.setEnabled(false);
                        if (!l_loc.isEmpty())
                        {
                            lloc.setError(null);
                            lloc.setEnabled(false);
                            Intent intent=new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            lloc.setError("Select Location");
                        }
                    }
                    else
                    {
                      dd.setError("select date");
                    }
                }
                else
                {
                    ltittle.setError("Enter Tittle");
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(mprofilel.this, new DatePickerDialog.OnDateSetListener() {
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