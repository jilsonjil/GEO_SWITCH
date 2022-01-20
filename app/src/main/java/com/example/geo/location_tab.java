package com.example.geo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
                i=new Intent(requireContext(), locationreminder.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
            case R.id.cardmessage:
                i=new Intent(requireContext(), locationmessage.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
            case R.id.cardmprofile:
                i=new Intent(requireContext(), locationmobileprofile.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
            case R.id.cardconnect:
                i=new Intent(requireContext(), locationconnectivity.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
        }

    }
}
