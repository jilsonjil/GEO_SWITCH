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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class locationconnectivityview extends AppCompatActivity implements LocationConnectivityRecyclerView.OnclickListener{


    LocationConnectivityRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationconnectivityview);
        RecyclerView recyclerView = findViewById(R.id.recycler_connectivity);
        TextView textView=findViewById(R.id.notfound);
        adapter = new LocationConnectivityRecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);



        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String uname=pref.getString("userId","");
        FirebaseDatabase.getInstance().getReference("user").child(uname)
                .child("location_connectivity").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<locationconnectivitystore> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    locationconnectivitystore locationconnectivitystores = postSnapshot.getValue(locationconnectivitystore.class);
                    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
                    try {
                        if (format.parse(locationconnectivitystores.getDate()).getTime() >= (System.currentTimeMillis() - (
                                1000 * 60 *  60 * 24
                        )) )
                            data.add(locationconnectivitystores);
                    } catch (ParseException e) {
                        e.printStackTrace();


                }

                }
                if(!data.isEmpty()) {
                    textView.setVisibility(View.INVISIBLE);
                    data.sort(new Comparator<locationconnectivitystore>() {
                        @Override
                        public int compare(locationconnectivitystore o1, locationconnectivitystore o2) {
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

    public String getData(String lat, String lon) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?lat="+lat
                        +"&lon="+lon+"&appid=89c5f723d5950889ae093e798e4b1f37")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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