package com.example.bsnotes.activities.device;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bsnotes.R;
import com.example.bsnotes.activities.SemesterActivity;

public class DeviceAddedSuccessfully extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_added_successfully);
    }
    public void gotoDevicelist(View view) {
        Intent i = new Intent(getApplicationContext(), SemesterActivity.class);
        startActivity(i);
        finish();
    }
}