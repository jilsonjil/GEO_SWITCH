package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
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
        final  FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                storingdata storingdata=datasnapshot.getValue(com.example.geo.storingdata.class);
                f_name.setText(storingdata.name);
                u_name.setText(storingdata.username);
                tname.setText(storingdata.name);
                tuname.setText(storingdata.username);
                tmail.setText(storingdata.email);
                tphone.setText(storingdata.phone);



                // System.out.println(storingdata);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("failed");

            }
        });

    }

}