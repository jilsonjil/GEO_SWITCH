package com.example.geo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geo.R;
import com.example.geo.locreminderstore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ReminderRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<locreminderstore> locreminderList = new ArrayList<>();
    OnclickListener listener;
    public ReminderRecyclerView(OnclickListener listener) {
        this.listener = listener;
    }

    public interface OnclickListener {
        public void Onclick(locreminderstore data);
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locreminderviewlist,parent,false);
        return new ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        locreminderstore data = locreminderList.get(position);
       TextView info = holder.itemView.findViewById(R.id.info);
       info.setText(data.getTittle());
       holder.itemView.setOnClickListener(v -> {
           listener.Onclick(data);
       });
    }

    @Override
    public int getItemCount() {
        return locreminderList.size();
    }

    public void add(List<locreminderstore> locreminderList) {
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
