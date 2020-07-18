package com.stimuligaming.gamely.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stimuligaming.gamely.adapters.AppAdapter;
import com.stimuligaming.gamely.helperClasses.Application;
import com.stimuligaming.gamely.MainActivity;
import com.stimuligaming.gamely.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private TextView gameCountView;
    private ProgressDialog progressDialog;
    private ArrayList<Application> installedApps;
    private RecyclerView userInstalledApps;
    private AlertDialog.Builder builder;
    private Context context;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        userInstalledApps = view.findViewById(R.id.application_list);
        gameCountView = view.findViewById(R.id.games_count);
        context = getContext();

        ((MainActivity) getActivity()).setActionBarTitle("Gamely");
        ((MainActivity) getActivity()).setButton(true);

        getInstalledApps();
        initializeList();
        showTotalNumber();

        return view;
    }

    private void showDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting your games..");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void hideDialog(){
        progressDialog.dismiss();
    }

    private void getInstalledApps() {
        ArrayList<Application> apps = new ArrayList<>();
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (!isSystemPackage(p) && isGame(p)) {
                String appName = p.applicationInfo.loadLabel(context.getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(context.getPackageManager());
                String packages = p.applicationInfo.packageName;
                apps.add(new Application(appName, icon, packages));
            }
        }
        installedApps = apps;
    }

    private void initializeList(){
        userInstalledApps.setLayoutManager(new GridLayoutManager(context, 4, GridLayoutManager.HORIZONTAL, false));
        userInstalledApps.setHasFixedSize(true);
        builder = new AlertDialog.Builder(context);
        AppAdapter installedAppAdapter = new AppAdapter(context, installedApps);
        userInstalledApps.setAdapter(installedAppAdapter);
    }


    private void showTotalNumber(){
        //Total Number of Installed-Apps(i.e. List Size)
        String  abc = installedApps.size() + "";
        String temp = (String) gameCountView.getText();
        gameCountView.setText(temp + " (" + abc + ")");
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    private boolean isGame(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_IS_GAME) == ApplicationInfo.FLAG_IS_GAME;
    }

}
