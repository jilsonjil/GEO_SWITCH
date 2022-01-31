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

public class TimeReminderRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<timereminderstore>timereminderlist=new ArrayList<>();
    TimeReminderRecyclerView.OnclickListener listener;

  public TimeReminderRecyclerView(TimeReminderRecyclerView.OnclickListener listener) {
      this.listener = listener;
  }

    public interface OnclickListener {
        public void Onclick(timereminderstore data);

        void OnDelete(timereminderstore data);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationconnectivityviewlist,parent,false);
        return new TimeReminderRecyclerView.ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       timereminderstore data =timereminderlist.get(position);
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
        return timereminderlist.size();
    }

    public void add(List<timereminderstore> timereminderlist) {
        this.timereminderlist.clear();
        this.timereminderlist.addAll(timereminderlist);
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
