package com.example.bsnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.bsnotes.R;
import com.example.bsnotes.helper.ApppreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView toolbarTitle;
    @BindView(R.id.cvChangeTheme)
    CardView cvChangeTheme;
    private Toolbar mToolbar;
    ApppreferenceManager apppreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onClickInit();
    }

    private void onClickInit(){
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        cvChangeTheme.setOnClickListener(this);
    }
    private void initView() {

        apppreferenceManager = new ApppreferenceManager(this);
        if(apppreferenceManager.getDarkModeState()){
            setTheme(R.style.AppTheme);


            Toast.makeText(SettingsActivity.this, "light mode", Toast.LENGTH_SHORT).show();

        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppThemeDark);
            Toast.makeText(SettingsActivity.this, "dark mode", Toast.LENGTH_SHORT).show();
        }


        initToolbar();
    }

    public void enableUpButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        setSupportActionBar(mToolbar);
//        mToolbar.setTitle("Setting");

    }

    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void darkMode(boolean b) {
        apppreferenceManager.setDarkModeState(b);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(),SemesterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        },1000);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvChangeTheme:
                if (apppreferenceManager.getDarkModeState()) {

                    darkMode(false);
                } else {
                    darkMode(true);
                }
                break;
        }
    }
}