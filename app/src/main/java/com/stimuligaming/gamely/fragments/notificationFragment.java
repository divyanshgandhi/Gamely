package com.stimuligaming.gamely.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stimuligaming.gamely.MainActivity;
import com.stimuligaming.gamely.R;


public class notificationFragment extends Fragment {


    public notificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Notifications", 28);
        ((MainActivity) getActivity()).setButton(true);
        return view;
    }
}
