package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userprofile extends AppCompatActivity {
    TextView f_name,u_name;
    TextInputEditText tname,tuname,tmail,tphone;

    Button btn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        initUi();
        getdataFromDb();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });


    }
    private void initUi()
    {  f_name=findViewById(R.id.full_name);
        u_name=findViewById(R.id.user_name);
        tname=findViewById(R.id.f_name);
        tuname=findViewById(R.id.u_name);
        tmail=findViewById(R.id.u_mail);
        tphone=findViewById(R.id.u_phone);
        btn=findViewById(R.id.update);

    }
    private void update()
    {
      //  reference=FirebaseDatabase.getInstance().getReference("user");
       //reference.child("profile/name").setValue(tname.getText().toString());
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String userId = pref.getString("userId","");
        final  FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference("user").child(userId).child("profile");
        String name = tname.getText().toString();
        if(!name.isEmpty()) {


            reference.child("name").setValue(name);

            Intent intent = new Intent(getApplicationContext(), dashboard.class);
            startActivity(intent);
        }
        else
        {
            tname.setError("name field is empty");
        }

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