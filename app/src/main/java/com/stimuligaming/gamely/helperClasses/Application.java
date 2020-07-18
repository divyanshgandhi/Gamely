package com.stimuligaming.gamely.helperClasses;

import android.graphics.drawable.Drawable;

public class Application {
    private String name;
    private Drawable icon;
    private String packageName;

    public Application(String name, Drawable icon, String packageName){
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
    }

    public String getName(){
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getPackageName() {
        return packageName;
    }
}
