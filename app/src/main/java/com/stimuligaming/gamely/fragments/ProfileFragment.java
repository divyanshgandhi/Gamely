package com.stimuligaming.gamely.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stimuligaming.gamely.LoginActivity;
import com.stimuligaming.gamely.MainActivity;
import com.stimuligaming.gamely.R;
import com.stimuligaming.gamely.RegisterActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference ref;
    ImageView profileImg;
    Button regButton, loginButton;
    TextView name, username;
    String fName, lName;
    String nameStr, usernameStr, uid;
    Context context;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Profile");
        ((MainActivity) getActivity()).setButton(false);
        context = getContext();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.username);
        profileImg = view.findViewById(R.id.profile_picture);
        regButton = view.findViewById(R.id.register_button);
        loginButton = view.findViewById(R.id.login_button);

        regButton.setOnClickListener(click -> {
            Intent intent = new Intent(context, RegisterActivity.class);
            startActivity(intent);
        });

        regButton.setOnClickListener(click -> {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        });

        Intent intent = getActivity().getIntent();
        if(!(intent.getStringExtra("name")==null)){
            name.setText(intent.getStringExtra("name"));
            username.setText(intent.getStringExtra("username"));
            Glide.with(context).load(intent.getStringExtra("image")).into(profileImg);
        }else{
            user = mAuth.getCurrentUser();
            if(user==null){
                Intent loginIntent = new Intent(context, LoginActivity.class);
                startActivityForResult(loginIntent, 1);
            }else{
                loadUser();
            }
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if(requestCode == 1) {
            loadUser();
        }
    }

    private void loadUser(){
        user = mAuth.getCurrentUser();
        uid = user.getUid();
        ref = database.getReference().child("users").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fName = (String) snapshot.child("fName").getValue();
                lName = (String) snapshot.child("lName").getValue();
                nameStr = fName + " " + lName;
                usernameStr = (String) "@"+snapshot.child("username").getValue();
                if(nameStr!=null){
                    name.setText(nameStr);
                    username.setText(usernameStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
