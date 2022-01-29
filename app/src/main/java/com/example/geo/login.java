package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    Button signupbtn,loginbtn;
    TextView forgot;
    TextInputLayout username,upassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        signupbtn=findViewById(R.id.signup);
        loginbtn=findViewById(R.id.login);
        username=findViewById(R.id.usname);
        upassword=findViewById(R.id.pass);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us_name=username.getEditText().getText().toString();
                String pass_word=upassword.getEditText().getText().toString();
                if(!us_name.isEmpty())
                {
                    username.setError(null);
                    if(!pass_word.isEmpty())
                    {
                        upassword.setError(null);

                        final String user_name=username.getEditText().getText().toString();

                        final String passw=upassword.getEditText().getText().toString();
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference=firebaseDatabase.getReference("user");
                        Query checkmail=databaseReference.orderByChild("profile/username").equalTo(user_name);
                        checkmail.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    username.setError(null);

                                    String passwordcheck=dataSnapshot.child(user_name).child("profile").child("password").getValue(String.class);
                                    if(passwordcheck.equals(passw))
                                    {
                                        upassword.setError(null);
                                        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                        pref.edit().putString("userId",user_name).apply();
                                        Toast.makeText(getApplicationContext(),"Login Succesfully",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    } else
                                    { upassword.setError("Wrong Password");

                                    }

                                }
                                else{
                                    username.setError("User doesnot exists");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                    else
                    {
                        upassword.setError("Please enter your password");
                    }
                }
                else
                {
                    username.setError("Please enter user name");
                }


            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}