package com.example.bsnotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.bsnotes.R;
import com.example.bsnotes.activities.authentication.Login;
import com.example.bsnotes.activities.authentication.User_Startup_Screen;
import com.example.bsnotes.helper.ApppreferenceManager;
import com.example.bsnotes.utaills.GeneralUtills;

public class MainActivity extends AppCompatActivity {
    ApppreferenceManager apppreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        darkMode();

        setContentView(R.layout.activity_main);

        splash();
    }

    private void splash() {
//        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(MainActivity.this, User_Startup_Screen.class));
                finish();
            }
        }, 1000);

    }

    private void darkMode() {
        apppreferenceManager = new ApppreferenceManager(this);
        if (apppreferenceManager.getDarkModeState()) {
            setTheme(R.style.AppTheme);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppThemeDark);
        }
    }

}
