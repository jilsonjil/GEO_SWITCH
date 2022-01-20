package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    TextInputLayout fname,mail,phone,pass,cpass,u_name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        fname=findViewById(R.id.fullname);
        mail=findViewById(R.id.email);
        phone=findViewById(R.id.phonenumber);
        pass=findViewById(R.id.password);
        cpass=findViewById(R.id.confirmpass);
        u_name=findViewById(R.id.uname);


    }

    public void loginbtn(View view) {
        Intent intent=new Intent(getApplicationContext(), login.class);
        startActivity(intent);
        finish();
    }

    public void registerbtn(View view) {
        String Sfname=fname.getEditText().getText().toString();
        String Smail=mail.getEditText().getText().toString();
        Smail.replace('.','_');
        String Sphone=phone.getEditText().getText().toString();
        String Spass=pass.getEditText().getText().toString();
        String Scpass=cpass.getEditText().getText().toString();
        String Su_name=u_name.getEditText().getText().toString();
        if(!Sfname.isEmpty())
        {
            fname.setError(null);

            if(!Smail.isEmpty())
            {
                mail.setError(null);

                if (!Sphone.isEmpty()) {
                    phone.setError(null);
                    if (!Su_name.isEmpty()) {
                        u_name.setError(null);

                        if (!Spass.isEmpty()) {
                            pass.setError(null);

                            if (!Scpass.isEmpty()&&Scpass.equals(Spass)) {
                                cpass.setError(null);

                                if (Smail.matches("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
                                    if (Spass.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=/])(?=\\S+$).{8,}$")) {
                                        if(Su_name.matches("[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$")) {
                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            reference = firebaseDatabase.getReference("user");
                                            String Sfname_s = fname.getEditText().getText().toString();
                                            String Smail_s = mail.getEditText().getText().toString();
                                            String Sphone_s = phone.getEditText().getText().toString();
                                            String Spass_s = pass.getEditText().getText().toString();
                                            String Scpass_s = cpass.getEditText().getText().toString();
                                            String Suname_s=u_name.getEditText().getText().toString();

                                            registerstoringdata storingdatas = new registerstoringdata(Sfname_s, Smail_s, Sphone_s, Spass_s, Scpass_s,Suname_s);
                                            reference.child(Suname_s).child("profile").setValue(storingdatas).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getApplicationContext(), "Registartion successfully", Toast.LENGTH_SHORT).show();
                                                    SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                                    pref.edit().putString("userId",Suname_s).apply();
                                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });

                                        }
                                        else
                                            {
                                                u_name.setError("Only contains alphanumeric characters,underscore and dot,Number of characters must be between 8 to 20");
                                            }

                                    } else {
                                        pass.setError("At least 8 chars, one digit,one lower alpha char and one upper alpha char,one special chars");
                                    }
                                } else {
                                    mail.setError("Invalid email");
                                }
                            } else {
                                cpass.setError("password doe's not match ");
                            }
                        } else {
                            pass.setError("please enter Your password");
                        }
                    } else {
                        u_name.setError("Enter user name");

                    }
                }


                else
                    {
                    phone.setError("Please enter Your phone number");
                    }
            }
            else
            {
                mail.setError("Please enter your Email");
            }
        }
        else
        {
            fname.setError("Please enter your full name");
        }
    }
}