package com.example.geo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TimeMessageRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<timemessagestore> timemessageList=new ArrayList<>();
    TimeMessageRecyclerView.OnclickListener listener;
    public TimeMessageRecyclerView(TimeMessageRecyclerView.OnclickListener listener) {
        this.listener = listener;
    }

    public interface OnclickListener {
        public void Onclick(timemessagestore data);
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationconnectivityviewlist,parent,false);
        return new TimeMessageRecyclerView.ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        timemessagestore data = timemessageList.get(position);
        TextView info = holder.itemView.findViewById(R.id.info);
        info.setText(data.getContact_name());
        holder.itemView.setOnClickListener(v -> {
            listener.Onclick(data);
        });
    }

    @Override
    public int getItemCount() {
        return timemessageList.size();
    }

    public void add(List<timemessagestore> timemessageList) {
        this.timemessageList.clear();
        this.timemessageList.addAll(timemessageList);
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
