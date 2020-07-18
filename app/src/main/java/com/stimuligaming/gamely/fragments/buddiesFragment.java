package com.stimuligaming.gamely.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stimuligaming.gamely.MainActivity;
import com.stimuligaming.gamely.R;
import com.stimuligaming.gamely.adapters.AppAdapter;
import com.stimuligaming.gamely.adapters.BuddyAdapter;
import com.stimuligaming.gamely.helperClasses.Buddy;
import com.stimuligaming.gamely.helperClasses.User;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class buddiesFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView buddiesList;
    ArrayList<Buddy> buddyList;
    BuddyAdapter buddyAdapter;
    Context context;

    public buddiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buddies, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Buddies");
        ((MainActivity) getActivity()).setButton(true);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("users");
        buddiesList = view.findViewById(R.id.buddies_list);
        buddyList = new ArrayList<>();
        context = getContext();

        buddiesList.setLayoutManager(new LinearLayoutManager(context));
        buddiesList.setHasFixedSize(true);
        loadBuddies();

        return view;
    }

    public void loadBuddies() {
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                buddyList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    assert currUser != null;
                    if (!currUser.getUid().equals(snapshot.child("uid").getValue())) {
                        String tempName = snapshot.child("fName").getValue() + " " + snapshot.child("lName").getValue();
                        String tempUsername = (String) snapshot.child("username").getValue();
                        String tempUid = (String) snapshot.child("uid").getValue();
                        String tempImageURL = "default"; //TODO: (String) snapshot.child("imageURL").getValue()
                        Buddy newBuddy = new Buddy(tempName, tempUsername, tempUid, tempImageURL);
                        buddyList.add(newBuddy);
                    }
                }
                buddyAdapter = new BuddyAdapter(context, buddyList);
                buddiesList.setAdapter(buddyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
