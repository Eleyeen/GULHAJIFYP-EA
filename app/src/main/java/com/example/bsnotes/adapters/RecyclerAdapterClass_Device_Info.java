package com.example.bsnotes.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bsnotes.R;
import com.example.bsnotes.activities.device.DetailedDevice;
import com.example.bsnotes.activities.models.HolderClass_Device_Info;
import com.example.bsnotes.activities.models.HolderClass_Shop_Info;
import com.example.bsnotes.activities.shops.DetailedShop;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerAdapterClass_Device_Info extends FirebaseRecyclerAdapter<HolderClass_Device_Info, RecyclerAdapterClass_Device_Info.ViewHolder_DeviceInfo> {
    Context context;
    String Latitude;
    String Longitude;
    String addressss;

    public double distanceCalcc;
    ImageView HostelImagePrevieww;
    /*String WardenUid;*/

    private LocationManager locationManager;


    public RecyclerAdapterClass_Device_Info(@NonNull FirebaseRecyclerOptions<HolderClass_Device_Info> options, Context context) {
        super(options);
        this.context = context;


    }


    @Override
    protected void onBindViewHolder(@NonNull RecyclerAdapterClass_Device_Info.ViewHolder_DeviceInfo holder, int position, @NonNull HolderClass_Device_Info model) {
        holder.HostelName.setText(model.getDevicename());
        holder.HostelCity.setText(model.getShopcity().toLowerCase());
        holder.HostelDescription.setText(model.getDevicedescription());
        Glide.with(holder.HostelImagePreview.getContext()).load(model.getDeviceimage()).into(holder.HostelImagePreview);

       /* Latitude = String.valueOf(model.getLatitude());
        Longitude = String.valueOf(model.getLongitude());*/
        String imageurl = model.getDeviceimage();
        String wardenname = model.getWardenName();
        String wardenphone = model.getWardenphonenumber();
        String description = model.getDevicedescription();
        String cityname = model.getShopcity().toUpperCase();
        String Hostelnamee = model.getDevicename();
        String HostelWuid = model.getWardenUid();
        String ontime = model.getOnTimingDevice();
        String offtime = model.getOffTimingDevice();


        Glide.with(holder.HostelImagePreview.getContext()).load(model.getDeviceimage()).into(holder.HostelImagePreview);

        holder.wholeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), DetailedDevice.class);

                intent.putExtra("hostelimage", imageurl);
                intent.putExtra("wardenname", wardenname);
                intent.putExtra("wardenphone", wardenphone);
                intent.putExtra("description", description);
                intent.putExtra("cityname", cityname);
                intent.putExtra("hosstelname", Hostelnamee);
                intent.putExtra("wardenUid", HostelWuid);
                intent.putExtra("completeLocationAdd", addressss);
                intent.putExtra("onTiming",ontime);
                intent.putExtra("offTiming",offtime);


                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);

                context.getApplicationContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder_DeviceInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hostelview_in_recyclerview, parent, false);
        return new RecyclerAdapterClass_Device_Info.ViewHolder_DeviceInfo(view);
    }
    class ViewHolder_DeviceInfo extends RecyclerView.ViewHolder {
        ImageView HostelImagePreview;
        TextView HostelName, HostelCity, HostelDescription, distanceCalculated, latitudee, longitudee, Hostelwardenuid;
        RatingBar ratingBar;
        RelativeLayout wholeCardView;


        public ViewHolder_DeviceInfo(@NonNull View itemView) {
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
