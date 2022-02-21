package com.example.geo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class timereminderview extends AppCompatActivity implements TimeReminderRecyclerView.OnclickListener{
TimeReminderRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timereminderview);
        RecyclerView recyclerView =findViewById(R.id.recycler_reminder);
        TextView textView=findViewById(R.id.notfound);
        adapter=new TimeReminderRecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname=pref.getString("userId","");
        FirebaseDatabase.getInstance().getReference("user").child(uname)
                .child("time_reminder").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<timereminderstore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    timereminderstore timereminderstores = postSnapshot.getValue(timereminderstore.class);
                    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
                    try {
                        if (format.parse(timereminderstores.getDate()).getTime() >= (System.currentTimeMillis() - (
                                1000 * 60 *  60 * 24
                        )) )
                            data.add(timereminderstores);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if(!data.isEmpty()) {
                    textView.setVisibility(View.INVISIBLE);
                    data.sort(new Comparator<timereminderstore>() {
                        @Override
                        public int compare(timereminderstore o1, timereminderstore o2) {
                            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
                            try {
                                return (int) (format.parse(o2.getDate()).getTime() - format.parse(o1.getDate()).getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }return 0;
                        }
                    });
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