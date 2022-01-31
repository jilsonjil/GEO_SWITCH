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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class timemessageview extends AppCompatActivity implements TimeMessageRecyclerView.OnclickListener {
TimeMessageRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timemessageview);
        RecyclerView recyclerView =findViewById(R.id.recycler_message);
        TextView textView=findViewById(R.id.notfound);
        adapter=new TimeMessageRecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname=pref.getString("userId","");
        FirebaseDatabase.getInstance().getReference("user").child(uname)
                .child("time_message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<timemessagestore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    timemessagestore timemessagestores = postSnapshot.getValue(timemessagestore.class);
                    data.add(timemessagestores);

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
    public void Onclick(timemessagestore data) {
        Intent i=new Intent(this,timemessage.class);
        i.putExtra("data",data);
        startActivity(i);

    }
    public void OnDelete(timemessagestore data) {
        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname = pref.getString("userId", "");
        FirebaseDatabase.getInstance().getReference("user").child(uname).child("time_message")
                .child(String.valueOf(data.getId())).removeValue();
    }
}