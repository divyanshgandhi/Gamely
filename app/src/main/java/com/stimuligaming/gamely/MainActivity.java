package com.stimuligaming.gamely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navbar;
    NavController navControl;
    MaterialToolbar customBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbar = findViewById(R.id.bottom_navbar);
        navControl = Navigation.findNavController(this, R.id.main_fragment);
        NavigationUI.setupWithNavController(navbar, navControl);
        customBar = findViewById(R.id.app_bar);
    }

    public void setActionBarTitle(String title){
        TextView titleInBar = customBar.findViewById(R.id.title);
        titleInBar.setText(title);
    }

    public void setActionBarTitle(String title, float size){
        TextView titleInBar = customBar.findViewById(R.id.title);
        titleInBar.setText(title);
        titleInBar.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setButton(boolean isSearch){
        ImageView icon = customBar.findViewById(R.id.button);

        if(isSearch){
            icon.setImageResource(R.drawable.ic_search);
            icon.setOnClickListener(click ->{
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            });
        }else{
            icon.setImageResource(R.drawable.ic_settings);
            icon.setOnClickListener(click ->{
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            });
        }
    }

}
