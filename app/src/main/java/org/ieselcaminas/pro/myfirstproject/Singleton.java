package org.ieselcaminas.pro.myfirstproject;

import com.google.firebase.auth.FirebaseAuth;

public class Singleton {

    private static Singleton sharedInstance;
    private boolean authenticated;
    private boolean mainMenuDown;
    private boolean profileMenuDown;
    private FirebaseAuth mAuth;


    private Singleton() {
        mAuth = FirebaseAuth.getInstance();
        authenticated = false;

        mainMenuDown = false;
        profileMenuDown = false;

    }

    public static Singleton sharedInstance() {
        if (sharedInstance == null)
            sharedInstance = new Singleton();
        return sharedInstance;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isMainMenuDown() {
        return mainMenuDown;
    }

    public void setMainMenuDown(boolean mainMenuDown) {
        this.mainMenuDown = mainMenuDown;
    }

    public boolean isProfileMenuDown() {
        return profileMenuDown;
    }

    public void setProfileMenuDown(boolean profileMenuDown) {
        this.profileMenuDown = profileMenuDown;
    }


    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }


}
