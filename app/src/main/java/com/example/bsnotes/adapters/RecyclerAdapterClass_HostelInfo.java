package com.example.bsnotes.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bsnotes.R;
import com.example.bsnotes.activities.models.HolderClass_Shop_Info;
import com.example.bsnotes.activities.shops.DetailedShop;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerAdapterClass_HostelInfo extends FirebaseRecyclerAdapter<HolderClass_Shop_Info, RecyclerAdapterClass_HostelInfo.ViewHolder_HostelInfo> {
    Context context;
    String Latitude;
    String Longitude;
    String addressss;

    public double distanceCalcc;
    ImageView HostelImagePrevieww;
    /*String WardenUid;*/

    private LocationManager locationManager;


    public RecyclerAdapterClass_HostelInfo(@NonNull FirebaseRecyclerOptions<HolderClass_Shop_Info> options, Context context) {
        super(options);
        this.context = context;


    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder_HostelInfo holder, int position, @NonNull HolderClass_Shop_Info model) {
        holder.HostelName.setText(model.getHostelname());
        holder.HostelCity.setText(model.getHostelcity().toLowerCase());
        holder.HostelDescription.setText(model.getHosteldescription());
        Glide.with(holder.HostelImagePreview.getContext()).load(model.getHostelimage()).into(holder.HostelImagePreview);

       /* Latitude = String.valueOf(model.getLatitude());
        Longitude = String.valueOf(model.getLongitude());*/
        String imageurl = model.getHostelimage();
        String wardenname = model.getWardenName();
        String wardenphone = model.getWardenphonenumber();
        String cityname = model.getHostelcity().toUpperCase();
        String Hostelnamee = model.getHostelname();
        String description = model.getHosteldescription();
        String ontime= model.getOnTimingAdForm();
        String offtime= model.getOffTimingAdForm();

        String HostelWuid = model.getWardenUid();



        Glide.with(holder.HostelImagePreview.getContext()).load(model.getHostelimage()).into(holder.HostelImagePreview);

        holder.wholeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), DetailedShop.class);

                intent.putExtra("hostelimage", imageurl);
                intent.putExtra("wardenname", wardenname);
                intent.putExtra("wardenphone", wardenphone);
                intent.putExtra("description", description);
                intent.putExtra("cityname", cityname);
                intent.putExtra("hosstelname", Hostelnamee);
                intent.putExtra("wardenUid", HostelWuid);
                intent.putExtra("onTiming",ontime);
                intent.putExtra("offTiming",offtime);
                intent.putExtra("completeLocationAdd", addressss);



                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);

                context.getApplicationContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder_HostelInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hostelview_in_recyclerview, parent, false);
        return new ViewHolder_HostelInfo(view);
    }

    class ViewHolder_HostelInfo extends RecyclerView.ViewHolder {
        ImageView HostelImagePreview;
        TextView HostelName, HostelCity, HostelDescription, distanceCalculated, latitudee, longitudee, Hostelwardenuid;
        RatingBar ratingBar;
        RelativeLayout wholeCardView;


        public ViewHolder_HostelInfo(@NonNull View itemView) {
            super(itemView);
            HostelImagePreview = (ImageView) itemView.findViewById(R.id.hostelImage);
            HostelName = (TextView) itemView.findViewById(R.id.Hostelname);
            HostelCity = (TextView) itemView.findViewById(R.id.HostelcityName);
            HostelDescription = (TextView) itemView.findViewById(R.id.hostelDesc);
            distanceCalculated = (TextView) itemView.findViewById(R.id.distance);
            latitudee = (TextView) itemView.findViewById(R.id.latitudeeee);
            longitudee = (TextView) itemView.findViewById(R.id.longitudeeee);
            wholeCardView = (RelativeLayout) itemView.findViewById(R.id.wholeCard);
            Hostelwardenuid = itemView.findViewById(R.id.hostelwardenuid);
        }
    }
}


