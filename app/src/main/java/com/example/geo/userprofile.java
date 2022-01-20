package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userprofile extends AppCompatActivity {
    TextView f_name,u_name;
    EditText tname,tuname,tmail,tphone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        initUi();
        getdataFromDb();


    }
    private void initUi()
    {  f_name=findViewById(R.id.full_name);
        u_name=findViewById(R.id.user_name);
        tname=findViewById(R.id.f_name);
        tuname=findViewById(R.id.u_name);
        tmail=findViewById(R.id.u_mail);
        tphone=findViewById(R.id.u_phone);

    }
    private void getdataFromDb()
    {
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String userId = pref.getString("userId","");
        final  FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference("user").child(userId).child("profile");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                registerstoringdata registerstoringdata =datasnapshot.getValue(registerstoringdata.class);
               f_name.setText(registerstoringdata.name);
                u_name.setText(registerstoringdata.username);
                tname.setText(registerstoringdata.name);
                tuname.setText(registerstoringdata.username);
                tmail.setText(registerstoringdata.email);
                tphone.setText(registerstoringdata.phone);



                // System.out.println(storingdata);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("failed");

            }
        });

    }

}