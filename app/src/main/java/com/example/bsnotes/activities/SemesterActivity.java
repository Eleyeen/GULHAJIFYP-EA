package com.example.bsnotes.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsnotes.R;
import com.example.bsnotes.activities.device.AddDeviceForm;
import com.example.bsnotes.activities.device.Device_List_activity;
import com.example.bsnotes.activities.shops.AddShopForm;
import com.example.bsnotes.activities.helperClasses.ActivityUtilities;
import com.example.bsnotes.activities.helperClasses.AppConstant;
import com.example.bsnotes.activities.helperClasses.AppUtilities;
import com.example.bsnotes.activities.helperClasses.DialogUtilities;
import com.example.bsnotes.activities.shops.Nearby_Shop_List;
import com.example.bsnotes.helper.ApppreferenceManager;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SemesterActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    @BindView(R.id.rvSemester)
    RecyclerView rvSemester;
    @BindView(R.id.btnTesting)
    Button btnTesting;
    @BindView(R.id.btnTesting1)
    Button btnTesting1;

    @BindView(R.id.showshop)
    Button show;
    @BindView(R.id.addshop)
    Button addshop;
//    SemesterAdapter semesterAdapter;
////    @BindView(R.id.tvNoSemester)
//    TextView tvNoSemester;

    private Activity mActivity;
    private Context mContext;
    private Toolbar mToolbar;

    private ImageButton mImgBtnSearch;
    LinearLayoutManager linearLayoutManager;
    ApppreferenceManager apppreferenceManager;
//    private List<SemesterData> itemLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        darkMode();
        initVar();
        initUI();
    }

    private void initVar() {
        setContentView(R.layout.activity_semester);
        ButterKnife.bind(this);
        mActivity = SemesterActivity.this;
        btnTesting.setOnClickListener(this);
        addshop.setOnClickListener(this);
        show.setOnClickListener(this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Device_List_activity.class);
                startActivity(intent);
            }
        });
        addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddDeviceForm.class);
                startActivity(intent);
            }
        });

        btnTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddShopForm.class);
                startActivity(intent);
            }
        });

        btnTesting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Nearby_Shop_List.class);
                startActivity(intent);
            }
        });


        mContext = getApplicationContext();
//        recylcerView();

    }

    private void darkMode() {
        apppreferenceManager = new ApppreferenceManager(this);
        if (apppreferenceManager.getDarkModeState()) {
            setTheme(R.style.AppTheme);
            Toast.makeText(SemesterActivity.this, "light mode", Toast.LENGTH_SHORT).show();


        } else {
            setTheme(R.style.AppThemeDark);
            Toast.makeText(SemesterActivity.this, "dark mode", Toast.LENGTH_SHORT).show();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {


//        mNotificationView = (RelativeLayout) findViewById(R.id.notificationView);
//        mImgBtnSearch = (ImageButton) findViewById(R.id.imgBtnSearch);


        initToolbar(false);
        initDrawer();
    }

    public void initToolbar(boolean isTitleEnabled) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(isTitleEnabled);

    }


    public void initDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };


        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setItemIconTintList(null);
        getNavigationView().setNavigationItemSelectedListener(this);
    }

    public NavigationView getNavigationView() {
        return mNavigationView;
    }


    @Override
    public void onBackPressed() {
        AppUtilities.tapPromptToExit(mActivity);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        // main items
        if (id == R.id.action_settings) {
            ActivityUtilities.getInstance().invokeNewActivity(mActivity, SettingsActivity.class, false);
        } else if (id == R.id.action_about_dev) {
            ActivityUtilities.getInstance().invokeNewActivity(mActivity, AboutDevActivity.class, false);
        }


        // social
        if (id == R.id.action_youtube) {
            AppUtilities.youtubeLink(mActivity);
        } else if (id == R.id.action_facebook) {
            AppUtilities.faceBookLink(mActivity);
        } else if (id == R.id.action_twitter) {
            AppUtilities.twitterLink(mActivity);
        } else if (id == R.id.action_google_plus) {
            AppUtilities.googlePlusLink(mActivity);
        }

        // others
        else if (id == R.id.action_share) {
            AppUtilities.shareApp(mActivity);
        } else if (id == R.id.action_rate_app) {
            AppUtilities.rateThisApp(mActivity); // this feature will only work after publish the app
        } else if (id == R.id.action_more_app) {
            AppUtilities.moreApps(mActivity);
        } else if (id == R.id.action_exit) {
            FragmentManager manager = getSupportFragmentManager();
            DialogUtilities dialog = DialogUtilities.newInstance(getString(R.string.exit), getString(R.string.close_prompt), getString(R.string.yes), getString(R.string.no), AppConstant.BUNDLE_KEY_EXIT_OPTION);
            dialog.show(manager, AppConstant.BUNDLE_KEY_DIALOG_FRAGMENT);
        }

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;

    }


//    private void recylcerView() {
//        rvSemester.setHasFixedSize(true);
//        rvSemester.setLayoutManager(new GridLayoutManager(SemesterActivity.this, 2));
//        rvSemester.setHasFixedSize(true);
//        itemLists = new ArrayList<>();
//        semesterAdapter = new SemesterAdapter(itemLists, SemesterActivity.this);
//        rvSemester.setAdapter(semesterAdapter);
//    }


}