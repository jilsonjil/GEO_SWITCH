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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<timemessagestore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    timemessagestore timemessagestores = postSnapshot.getValue(timemessagestore.class);
                    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
                    try {
                        if (format.parse(timemessagestores.getDate()).getTime() >= (System.currentTimeMillis() - (
                                1000 * 60 *  60 * 24
                        )) )
                            data.add(timemessagestores);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                

                }
                if(!data.isEmpty()) {
                    textView.setVisibility(View.INVISIBLE);
                    data.sort(new Comparator<timemessagestore>() {
                        @Override
                        public int compare(timemessagestore o1, timemessagestore o2) {
                            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
                            try {
                                return (int) (format.parse(o2.getDate()).getTime() - format.parse(o1.getDate()).getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
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