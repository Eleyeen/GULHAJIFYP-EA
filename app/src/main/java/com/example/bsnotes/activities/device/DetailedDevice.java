package com.example.bsnotes.activities.device;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsnotes.R;
import com.example.bsnotes.activities.shops.DetailedShop;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DetailedDevice extends AppCompatActivity {
    ImageView HostelImage;
    TextView HostelName, Hostel_Description, DistanceCalculated, CityName, WardenName, WardenPhone, completeLocation,ONTiming,
            OffTiming;
    double HostelLatitude;
    double HostelLongitude;

    private LocationManager locationManager;
    /* double currentlat;
     double currentlong;*/
    String hostelname;
    ImageButton chat, call, dislike;
    String HostelWardenName;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String WardenUniqueIDinDataBase;
    String image, Hostelnameee;

    String Wardenph;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_device);

        call = (ImageButton) findViewById(R.id.call);
        WardenName = (TextView) findViewById(R.id.shopnametext);
        WardenPhone = (TextView) findViewById(R.id.shopnumber);
        HostelImage = (ImageView) findViewById(R.id.hostelimages);
        Hostel_Description = (TextView) findViewById(R.id.deviceDesc);
        DistanceCalculated = (TextView) findViewById(R.id.shopNumF);
        CityName = (TextView) findViewById(R.id.floorname);
        HostelName = (TextView) findViewById(R.id.devicenamedetail);
        completeLocation = findViewById(R.id.devicedesctext);
        Hostel_Description.setMovementMethod(new ScrollingMovementMethod());
        OffTiming = (TextView) findViewById(R.id.offTimimg);
        ONTiming = (TextView) findViewById(R.id.onTimimg);

        HostelWardenName = getIntent().getStringExtra("wardenname");
        Hostelnameee = getIntent().getStringExtra("hosstelname").toLowerCase();
        image = getIntent().getStringExtra("hostelimage");
        completeLocation.setText(getIntent().getStringExtra("completeLocationAdd"));

        ONTiming.setText(getIntent().getStringExtra("onTiming"));
        OffTiming.setText(getIntent().getStringExtra("offTiming"));

        hostelname = getIntent().getStringExtra("hosstelname");
        WardenUniqueIDinDataBase = getIntent().getStringExtra("wardenUid");

        WardenPhone.setText(getIntent().getStringExtra("wardenphone"));
        Wardenph = getIntent().getStringExtra("wardenphone");

        Hostel_Description.setText(getIntent().getStringExtra("description"));
        DistanceCalculated.setText(getIntent().getStringExtra("shopNum"));
        CityName.setText(getIntent().getStringExtra("cityname"));
        WardenName.setText(getIntent().getStringExtra("wardenname"));
        HostelName.setText(getIntent().getStringExtra("hosstelname").toLowerCase());
        Glide.with(this).load(image).into(HostelImage);


    }



    public void Callwarden(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailedDevice.this);
        builder.setTitle("Call?")
                .setCancelable(false)
                .setMessage("Do you want to call the Warden of this Hostel?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + Wardenph));
                        startActivity(call);
                    }
                }).show();

    }




    private void showAlert() {
        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("GPS is OFF. \nPlease Enable Location to " + "use this app")
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

                    }
                });
        dialog.show();
    }


}