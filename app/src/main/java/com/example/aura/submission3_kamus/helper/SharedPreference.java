package com.example.aura.submission3_kamus.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.aura.submission3_kamus.R;

public class SharedPreference {
    Context context;
    SharedPreferences prefs;

    public SharedPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.apps_first_running);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getFirstRun() {
        String key = context.getResources().getString(R.string.apps_first_running);
        return prefs.getBoolean(key, true);
    }
}
