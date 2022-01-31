package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class timereminderview extends AppCompatActivity implements TimeReminderRecyclerView.OnclickListener{
TimeReminderRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timereminderview);
        RecyclerView recyclerView =findViewById(R.id.recycler_reminder);
        adapter=new TimeReminderRecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname=pref.getString("userId","");
        FirebaseDatabase.getInstance().getReference("user").child(uname)
                .child("time_reminder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<timereminderstore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    timereminderstore timereminderstores = postSnapshot.getValue(timereminderstore.class);
                    data.add(timereminderstores);

                }
                adapter.add(data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void Onclick(timereminderstore data) {
        Intent i=new Intent(this,timereminder.class);
        i.putExtra("data",data);
        startActivity(i);

    }
    public void OnDelete(timereminderstore data) {
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname = pref.getString("userId", "");
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("time_reminder")
                .child(String.valueOf(data.getId())).removeValue();
    }
}