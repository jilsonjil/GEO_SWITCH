package com.example.geo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geo.R;
import com.example.geo.locationreminderstore;

import java.util.ArrayList;
import java.util.List;

public class LocationReminderRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<locationreminderstore> locreminderList = new ArrayList<>();
    OnclickListener listener;
    public LocationReminderRecyclerView(OnclickListener listener) {
        this.listener = listener;
    }

    public interface OnclickListener {
        public void Onclick(locationreminderstore data);
        void OnDelete(locationreminderstore data);
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationconnectivityviewlist,parent,false);
        return new ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        locationreminderstore data = locreminderList.get(position);
       TextView info = holder.itemView.findViewById(R.id.info);
        ImageView delete = holder.itemView.findViewById(R.id.img_delete);
       info.setText(data.getTittle());
        ImageView img=holder.itemView.findViewById(R.id.img_edit);

       img.setOnClickListener(v -> {
           listener.Onclick(data);
       });
        delete.setOnClickListener(v-> {
            listener.OnDelete(data);
        });
    }

    @Override
    public int getItemCount() {
        return locreminderList.size();
    }

    public void add(List<locationreminderstore> locreminderList) {
        this.locreminderList.clear();
        this.locreminderList.addAll(locreminderList);
        notifyDataSetChanged();
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {
        //TextView info;
        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            //info = itemView.findViewById(R.id.info);
        }
    }
}
