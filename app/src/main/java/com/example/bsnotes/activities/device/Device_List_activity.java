package com.example.bsnotes.activities.device;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.bsnotes.R;
import com.example.bsnotes.activities.SemesterActivity;
import com.example.bsnotes.activities.models.HolderClass_Device_Info;
import com.example.bsnotes.activities.models.HolderClass_Shop_Info;
import com.example.bsnotes.adapters.RecyclerAdapterClass_Device_Info;
import com.example.bsnotes.adapters.RecyclerAdapterClass_HostelInfo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Device_List_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    /* Adapter_Nearby_Hostels adapter_nearby_hostels;*/
    RecyclerAdapterClass_Device_Info recyclerAdapterClass_hostelInfo;
    ProgressBar progressBar;
    SearchView searchView;
    private LocationManager locationManager;
    List<HolderClass_Device_Info> list;
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device__list_activity);
        recyclerView = (RecyclerView) findViewById(R.id.devicelistrecylcerview);
        progressBar = findViewById(R.id.progressbar);
        searchView = findViewById(R.id.list_searchbar);
        backArrow=findViewById(R.id.backbuttonD);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SemesterActivity.class);
                startActivity(intent);
            }
        });


        String city = getIntent().getStringExtra("Cityselected");
//        searchView.setQuery(city.toLowerCase(), true);
        /*searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                FirebaseRecyclerOptions<HolderClass_Device_Info> options =
                        new FirebaseRecyclerOptions.Builder<HolderClass_Device_Info>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("device").orderByChild("shopcity").startAt(newText).endAt(newText + "\uf8ff"), HolderClass_Device_Info.class)
                                .build();

                recyclerAdapterClass_hostelInfo = new RecyclerAdapterClass_Device_Info(options, Device_List_activity.this);
                recyclerAdapterClass_hostelInfo.startListening();
                recyclerView.setAdapter(recyclerAdapterClass_hostelInfo);
                return false;
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<HolderClass_Device_Info> options =
                new FirebaseRecyclerOptions.Builder<HolderClass_Device_Info>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("device"), HolderClass_Device_Info.class)
                        .build();

        recyclerAdapterClass_hostelInfo = new RecyclerAdapterClass_Device_Info(options, this);
        recyclerView.setAdapter(recyclerAdapterClass_hostelInfo);


    }

    private void searchCity(String query) {

        FirebaseRecyclerOptions<HolderClass_Device_Info> options =
                new FirebaseRecyclerOptions.Builder<HolderClass_Device_Info>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("device").orderByChild("shopcity").startAt(query).endAt(query + "\uf8ff"), HolderClass_Device_Info.class)
                        .build();
        recyclerAdapterClass_hostelInfo = new RecyclerAdapterClass_Device_Info(options, this);
        recyclerAdapterClass_hostelInfo.startListening();
        recyclerView.setAdapter(recyclerAdapterClass_hostelInfo);


    }


    private void showAlert() {
        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setCancelable(false)
                .setMessage("GPS is off. \nPlease Enable Location to " + "use this app")
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), SemesterActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        dialog.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapterClass_hostelInfo.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapterClass_hostelInfo.stopListening();
    }



}