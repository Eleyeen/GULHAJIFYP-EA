package com.example.bsnotes.activities.shops;

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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DetailedShop extends AppCompatActivity {
    ImageView HostelImage;
    TextView HostelName,
            Hostel_Description,
            CityName,
            WardenName,
            WardenPhone,
            ONTiming,
            OffTiming
    ;

    ImageButton chat, call, dislike;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String WardenUniqueIDinDataBase;
    String image;
    String Wardenph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_hostel);
        call = (ImageButton) findViewById(R.id.call);
        WardenName = (TextView) findViewById(R.id.wardennametext);
        WardenPhone = (TextView) findViewById(R.id.wardennumber);
        HostelImage = (ImageView) findViewById(R.id.hostelimagesShop);
        Hostel_Description = (TextView) findViewById(R.id.hostelDescD);
        OffTiming = (TextView) findViewById(R.id.offTimimgAddForm);
        ONTiming = (TextView) findViewById(R.id.onTimimgAddForm);

        CityName = (TextView) findViewById(R.id.cityname);
        HostelName = (TextView) findViewById(R.id.hosstelname);
        Hostel_Description.setMovementMethod(new ScrollingMovementMethod());

        image = getIntent().getStringExtra("hostelimage");
        WardenUniqueIDinDataBase = getIntent().getStringExtra("wardenUid");



        WardenPhone.setText(getIntent().getStringExtra("wardenphone"));


        Hostel_Description.setText(getIntent().getStringExtra("description"));
        Wardenph = getIntent().getStringExtra("wardenphone");

        ONTiming.setText(getIntent().getStringExtra("onTiming"));
        OffTiming.setText(getIntent().getStringExtra("offTiming"));
        CityName.setText(getIntent().getStringExtra("cityname"));
        WardenName.setText(getIntent().getStringExtra("wardenname"));
        HostelName.setText(getIntent().getStringExtra("hosstelname").toLowerCase());
        Glide.with(this).load(image).into(HostelImage);


    }



    public void Callwarden(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailedShop.this);
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