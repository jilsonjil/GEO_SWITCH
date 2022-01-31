package com.example.geo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geo.R;
import com.example.geo.locationmobileprofilestore;
import java.util.ArrayList;

import java.util.List;

public class LocationMobileRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<locationmobileprofilestore> locmprofileList = new ArrayList<>();
        OnclickListener listener;
public LocationMobileRecyclerView(OnclickListener listener) {
        this.listener = listener;
        }

public interface OnclickListener {
    public void Onclick(locationmobileprofilestore data);
}




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationconnectivityviewlist,parent,false);
        return new LocationMobileRecyclerView.ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        locationmobileprofilestore data = locmprofileList.get(position);
        TextView info = holder.itemView.findViewById(R.id.info);
        info.setText(data.getTittle());
        holder.itemView.setOnClickListener(v -> {
            listener.Onclick(data);
        });
    }

    @Override
    public int getItemCount() {
        return locmprofileList.size();
    }

    public void add(List<locationmobileprofilestore> locmprofileList) {
        this.locmprofileList.clear();
        this.locmprofileList.addAll(locmprofileList);
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
