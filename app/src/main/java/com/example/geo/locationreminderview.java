package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.geo.ui.LocationReminderRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class locationreminderview extends AppCompatActivity implements LocationReminderRecyclerView.OnclickListener {

    LocationReminderRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locreminderview);
        RecyclerView recyclerView = findViewById(R.id.recycler_reminder);
        TextView textView=findViewById(R.id.notfound);
        adapter = new LocationReminderRecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);



        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname=pref.getString("userId","");
        FirebaseDatabase.getInstance().getReference("user").child(uname)
                .child("location_reminder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<locationreminderstore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    locationreminderstore locreminderstores = postSnapshot.getValue(locationreminderstore.class);
                    data.add(locreminderstores);

                }
                if(!data.isEmpty()) {
                    textView.setVisibility(View.INVISIBLE);
                    adapter.add(data);

                }
                else
                {
                    recyclerView.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void Onclick(locationreminderstore data) {
        Intent i = new Intent(this,locationreminder.class);
        i.putExtra("data",data);
        startActivity(i);
    }
    public void OnDelete(locationreminderstore data) {
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname = pref.getString("userId", "");
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("location_reminder")
                .child(String.valueOf(data.getId())).removeValue();
    }
}