package com.example.geo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geo.R;
import com.example.geo.locationmessagestore;


import java.util.ArrayList;
import java.util.List;

public class LocationMessageRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<locationmessagestore> locmessageList = new ArrayList<>();
   OnclickListener listener;
    public LocationMessageRecyclerView(OnclickListener listener) {
        this.listener = listener;
    }

    public interface OnclickListener {
        public void Onclick(locationmessagestore data);
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationmessageviewlist,parent,false);
        return new LocationMessageRecyclerView.ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        locationmessagestore data = locmessageList.get(position);
        TextView info = holder.itemView.findViewById(R.id.info);
        info.setText(data.getContact_name());
        holder.itemView.setOnClickListener(v -> {
            listener.Onclick(data);
        });
    }

    @Override
    public int getItemCount() {
        return locmessageList.size();
    }

    public void add(List<locationmessagestore> locmessageList) {
        this.locmessageList.clear();
        this.locmessageList.addAll(locmessageList);
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

