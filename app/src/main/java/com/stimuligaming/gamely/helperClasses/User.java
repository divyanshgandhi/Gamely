package com.stimuligaming.gamely.helperClasses;

public class User {
    private String fName, lName, email, username, uid;

    public User(){
    }

    public User(String fName, String lName, String email, String username, String uid) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.username = username;
        this.uid = uid;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getUid(){
        return uid;
    }

}
