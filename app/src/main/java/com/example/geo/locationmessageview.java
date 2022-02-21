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

public class locationmessageview extends AppCompatActivity implements LocationMessageRecyclerView.OnclickListener {
    LocationMessageRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationmessageview);
        RecyclerView recyclerView = findViewById(R.id.recycler_message);
        TextView textView=findViewById(R.id.notfound);
        adapter = new LocationMessageRecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);


        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname = pref.getString("userId", "");
        String dd=pref.getString("userId", "");
        FirebaseDatabase.getInstance().getReference("user").child(uname)
                .child("location_message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<locationmessagestore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    locationmessagestore locationmessagestores = postSnapshot.getValue(locationmessagestore.class);
                    data.add(locationmessagestores);

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
    public void Onclick(locationmessagestore data) {
        Intent i = new Intent(this, locationmessage.class);
        i.putExtra("data", data);
        startActivity(i);

    }

    @Override
    public void OnDelete(locationmessagestore data) {
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname = pref.getString("userId", "");
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("location_message")
                .child(String.valueOf(data.getId())).removeValue();
    }
}