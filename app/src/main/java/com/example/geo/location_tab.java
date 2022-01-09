package com.example.geo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class location_tab extends Fragment implements View.OnClickListener {
    CardView loreminder,lomessage,lomprofile,loconnect;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.location_tab,container,false);
        loreminder=(CardView) root.findViewById(R.id.cardreminder);
        loreminder.setOnClickListener(this);
        lomessage=(CardView) root.findViewById(R.id.cardmessage);
        lomessage.setOnClickListener(this);
        lomprofile=(CardView) root.findViewById(R.id.cardmprofile);
        lomprofile.setOnClickListener(this);
        loconnect=(CardView) root.findViewById(R.id.cardconnect);
        loconnect.setOnClickListener(this);
        return root;


    }


    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId())
        {
            case R.id.cardreminder:
                i=new Intent(requireContext(),reminderl.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
            case R.id.cardmessage:
                i=new Intent(requireContext(),messagel.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
            case R.id.cardmprofile:
                i=new Intent(requireContext(),mprofilel.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
            case R.id.cardconnect:
                i=new Intent(requireContext(),connectivityl.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
        }

    }
}
