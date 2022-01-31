package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.geo.ui.LocationMobileRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class timemobileprofileview extends AppCompatActivity implements TimeMobileRecyclerView.OnclickListener {
    TimeMobileRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationmobileprofileview);
        RecyclerView recyclerView =findViewById(R.id.recycler_profile);
        adapter=new TimeMobileRecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname=pref.getString("userId","");
        FirebaseDatabase.getInstance().getReference("user").child(uname)
                .child("time_mobileprofile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<timemobileprofilestore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    timemobileprofilestore timemobileprofilestores = postSnapshot.getValue(timemobileprofilestore.class);
                    data.add(timemobileprofilestores);

                }

                    adapter.add(data);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void Onclick(timemobileprofilestore data) {
        Intent i=new Intent(this,timemobileprofile.class);
        i.putExtra("data",data);
        startActivity(i);

    }
    public void OnDelete(timemobileprofilestore data) {
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname = pref.getString("userId", "");
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("time_mobileprofile")
                .child(String.valueOf(data.getId())).removeValue();
    }
}