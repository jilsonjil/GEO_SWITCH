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
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class locationconnectivityview extends AppCompatActivity implements LocationConnectivityRecyclerView.OnclickListener{


    LocationConnectivityRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationconnectivityview);
        RecyclerView recyclerView = findViewById(R.id.recycler_connectivity);
        adapter = new LocationConnectivityRecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);



        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname=pref.getString("userId","");
        FirebaseDatabase.getInstance().getReference("user").child(uname)
                .child("location_connectivity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<locationconnectivitystore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    locationconnectivitystore locationconnectivitystores = postSnapshot.getValue(locationconnectivitystore.class);
                    data.add(locationconnectivitystores);

                }
                adapter.add(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       
    }

    @Override
    public void Onclick(locationconnectivitystore data) {
        Intent i=new Intent(this,locationconnectivity.class);
        i.putExtra("data",data);
        startActivity(i);

    }
    public void OnDelete(locationconnectivitystore data) {
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname = pref.getString("userId", "");
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("location_connectivity")
                .child(String.valueOf(data.getId())).removeValue();
    }
}