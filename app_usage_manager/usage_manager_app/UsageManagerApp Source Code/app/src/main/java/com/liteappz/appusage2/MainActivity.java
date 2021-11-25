package com.liteappz.appusage2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.liteappz.appusage2.Fragments.LaunchedFragment;
import com.liteappz.appusage2.Fragments.SettingsFragment;
import com.liteappz.appusage2.Fragments.TimeFragment;
import com.liteappz.appusage2.Fragments.WifiFragment;
import com.liteappz.appusage2.Utils.TinyDB;
import com.liteappz.appusage2.Utils.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bot.box.appusage.contract.UsageContracts;
import bot.box.appusage.handler.Monitor;
import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.Duration;
import bot.box.appusage.utils.DurationRange;

public class MainActivity extends AppCompatActivity implements UsageContracts.View {
//    private ActionBar toolbar;
    LinearLayout parentLL;
    RelativeLayout mainLayout;
    TinyDB tinyDB;
    int[] backgrounds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            util.startTimeUsageService(this);
        }catch (Exception e){
            Log.d("ServiceError", e.toString());
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new TimeFragment());

        tinyDB = new TinyDB(this);
        tinyDB.putString("modeSelected", "Today");
        backgrounds = new int[]{R.drawable.background_home, R.drawable.background_home2,
                                R.drawable.background_home3};
        mainLayout = findViewById(R.id.mainLayout_MainActivity);
        parentLL = findViewById(R.id.parent_horizontal_linear_MainActivity);
        for (int i=0; i<parentLL.getChildCount(); i++){
            TextView tv = (TextView) parentLL.getChildAt(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView = (TextView) view;
                    for (int i=0; i<parentLL.getChildCount(); i++){
                        TextView tv = (TextView) parentLL.getChildAt(i);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                        tv.setBackgroundResource(0);
                    }
                    textView.setBackgroundResource(R.drawable.rounded_corners_percentoff);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                    textView.setPadding(20,10,20,10);
                    String value = textView.getText().toString();
                    tinyDB.putString("modeSelected", value);

                    if (value.equals("Today"))
                        Monitor.scan().getAppLists(MainActivity.this).fetchFor(DurationRange.TODAY);
                    else if (value.equals("Yesterday"))
                        Monitor.scan().getAppLists(MainActivity.this).fetchFor(DurationRange.YESTERDAY);
                    else if (value.equals("Week"))
                        Monitor.scan().getAppLists(MainActivity.this).fetchFor(Duration.WEEK);
                    else if (value.equals("Month"))
                        Monitor.scan().getAppLists(MainActivity.this).fetchFor(DurationRange.MONTH);

                }
            });

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.time_navigation:
//                    toolbar.setTitle(R.string.title_time);
                    loadFragment(new TimeFragment());
                    parentLL.setVisibility(View.VISIBLE);
                    mainLayout.setBackgroundResource(backgrounds[(int) (Math.random()*3)]);
                    return true;
                case R.id.launched_navigation:
//                    toolbar.setTitle(R.string.title_launched);
                    loadFragment(new LaunchedFragment());
                    parentLL.setVisibility(View.VISIBLE);
                    mainLayout.setBackgroundResource(backgrounds[(int) (Math.random()*3)]);
                    return true;
                case R.id.wifi_navigation:
//                    toolbar.setTitle(R.string.title_wifi);
                    loadFragment(new WifiFragment());
                    parentLL.setVisibility(View.VISIBLE);
                    mainLayout.setBackgroundResource(backgrounds[(int) (Math.random()*3)]);
                    return true;
                case R.id.settings_navigation:
//                    toolbar.setTitle(R.string.title_mobile_data);
                    loadFragment(new SettingsFragment());
                    parentLL.setVisibility(View.GONE);
                    mainLayout.setBackgroundResource(0);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Monitor.hasUsagePermission()) {
            Monitor.scan().getAppLists(this).fetchFor(Duration.TODAY);
            util.isStoragePermissionGranted(this);

        } else {
            Monitor.requestUsagePermission();
        }
    }

    @Override
    public void getUsageData(List<AppData> usageData, long mTotalUsage, int duration) {
        TinyDB tinydb = new TinyDB(this);
        tinydb.putListObject("usageArray", usageData);
        Log.d("UsageData: ", usageData.get(0).mName+" "+usageData.get(0).mWifi+" "+usageData.get(0).mUsageTime);
//        Toast.makeText(this, "In GetUsageData", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("data_loaded");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}