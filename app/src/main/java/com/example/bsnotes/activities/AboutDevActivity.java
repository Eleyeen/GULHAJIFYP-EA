package com.example.bsnotes.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.bsnotes.R;
import com.example.bsnotes.helper.ApppreferenceManager;

import butterknife.ButterKnife;

public class AboutDevActivity extends AppCompatActivity {
    ApppreferenceManager apppreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        darkMode();
        initVar();
    }

    private void initVar() {
        setContentView(R.layout.activity_about_dev);
        ButterKnife.bind(this);

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