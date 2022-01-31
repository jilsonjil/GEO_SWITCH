package com.example.geo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geo.R;
import com.example.geo.timemobileprofilestore;

import java.util.ArrayList;
import java.util.List;

public class TimeMobileRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
List<timemobileprofilestore> timemprofileList=new ArrayList<>();
TimeMobileRecyclerView.OnclickListener listener;
    public TimeMobileRecyclerView(TimeMobileRecyclerView.OnclickListener listener) {
        this.listener = listener;
    }

    public interface OnclickListener {
        public void Onclick(timemobileprofilestore data);

        void OnDelete(timemobileprofilestore data);
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationconnectivityviewlist,parent,false);
        return new TimeMobileRecyclerView.ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        timemobileprofilestore data = timemprofileList.get(position);
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
        return timemprofileList.size();
    }

    public void add(List<timemobileprofilestore> timemprofileList) {
        this.timemprofileList.clear();
        this.timemprofileList.addAll(timemprofileList);
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
