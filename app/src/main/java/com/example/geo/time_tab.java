package com.example.geo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class time_tab extends Fragment implements View.OnClickListener {

    CardView treminder,tmessage,tmprofile;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.time_tab, container, false);
        treminder=(CardView) root.findViewById(R.id.cardtreminder);
        treminder.setOnClickListener(this);
        tmessage=(CardView) root.findViewById(R.id.cardtmessage);
        tmessage.setOnClickListener(this);
        tmprofile=(CardView) root.findViewById(R.id.cardtmprofile);
        tmprofile.setOnClickListener(this);
        return root;
    }
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardtreminder:
                i = new Intent(requireContext(),reminderti.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
            case R.id.cardtmessage:
                i = new Intent(requireContext(), messaget.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;
            case R.id.cardtmprofile:
                i = new Intent(requireContext(), mprofilet.class);
                requireContext().startActivity(i);
                requireActivity().finish();
                break;


        }
    }


}
