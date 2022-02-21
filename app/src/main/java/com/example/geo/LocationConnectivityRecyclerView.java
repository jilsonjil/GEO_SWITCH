package com.example.geo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LocationConnectivityRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<locationconnectivitystore> locconnectList = new ArrayList<>();
    LocationConnectivityRecyclerView.OnclickListener listener;
    public LocationConnectivityRecyclerView(LocationConnectivityRecyclerView.OnclickListener listener) {
        this.listener = listener;
    }

    public interface OnclickListener {
        public void Onclick(locationconnectivitystore data);

        void OnDelete(locationconnectivitystore data);
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showlist,parent,false);
        return new LocationConnectivityRecyclerView.ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        locationconnectivitystore data = locconnectList.get(position);
        TextView info = holder.itemView.findViewById(R.id.info);
        ImageView delete = holder.itemView.findViewById(R.id.img_delete);
        info.setText(data.getTittle());
        TextView date=holder.itemView.findViewById(R.id.date);
        date.setText(data.getDate());
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
        return locconnectList.size();
    }

    public void add(List< locationconnectivitystore> locconnectList) {
        this.locconnectList.clear();
        this.locconnectList.addAll(locconnectList);
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