package com.example.proyectonube;

import com.google.firebase.FirebaseApp;

public class MyFirebaseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
