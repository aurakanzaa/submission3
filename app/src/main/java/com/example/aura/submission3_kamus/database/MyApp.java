package com.example.aura.submission3_kamus.database;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class MyApp extends Application{
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
