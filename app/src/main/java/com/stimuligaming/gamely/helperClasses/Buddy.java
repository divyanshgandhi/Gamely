package com.stimuligaming.gamely.helperClasses;

import android.net.Uri;

import java.net.URI;
import java.net.URL;

public class Buddy {
    private String name, username, uid, imageURL;

    public Buddy(String name, String username, String uid, String imageURL) {
        this.name = name;
        this.username = username;
        this.uid = uid;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getUid() {
        return uid;
    }

    public String getImageURL(){
        return imageURL;
    }

}
