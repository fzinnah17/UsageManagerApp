package com.liteappz.appusage2.Fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liteappz.appusage2.Adapters.LaunchedAdapter;
import com.liteappz.appusage2.Adapters.TimeAdapter;
import com.liteappz.appusage2.ArcProgress;
import com.liteappz.appusage2.R;
import com.liteappz.appusage2.Utils.TinyDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.UsageUtils;

public class LaunchedFragment extends Fragment {
    public LaunchedAdapter mAdapter;
    ArcProgress totalLaunchTimesArcProgress, highestNumberOfLanchesArcProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_launched, container, false);

        init(v);

//        totalLaunchTimesArcProgress.setCentralText("11h 53m 5s");

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                TinyDB tinyDB = new TinyDB(getActivity());
                ArrayList usageList = tinyDB.getListObject("usageArray", AppData.class);

                updateData(usageList);

                mAdapter.updateData(usageList);
            }
        };
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
        lbm.registerReceiver(receiver, new IntentFilter("data_loaded"));
        return v;
    }

    public void init(View v){
        totalLaunchTimesArcProgress = v.findViewById(R.id.launchTimesArcProgress_LaunchedFrag);
        highestNumberOfLanchesArcProgress = v.findViewById(R.id.highestNumberOfLanuchesArcProgress_LaunchedFrag);

        RecyclerView mRecycler = v.findViewById(R.id.recyclerViewLaunchedTimes_LaunchedFrag);
        mAdapter = new LaunchedAdapter(getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

    }

    public void updateData(ArrayList usageList){
        int totalLaunches = 0;
        for (Object usageObject: usageList) {
            AppData usage = (AppData) usageObject;
            totalLaunches+=usage.mCount;
        }
        totalLaunchTimesArcProgress.setCentralText(totalLaunches+" Times");
        try {
            Collections.sort(usageList, new Comparator<AppData>() {
                @Override
                public int compare(AppData t1, AppData t2) {
                    return Integer.compare(t2.mCount, t1.mCount);
                }
            });

            AppData firstObject = (AppData) usageList.get(0);
            highestNumberOfLanchesArcProgress.setBottomText(firstObject.mName);
            highestNumberOfLanchesArcProgress.setCentralText(firstObject.mCount+" Times");
            highestNumberOfLanchesArcProgress.setProgress((int) (firstObject.mCount * 100.0 / totalLaunches));
        }catch (Exception ignored){}

    }

    @Override
    public void onResume() {
        super.onResume();
        TinyDB tinyDB = new TinyDB(getActivity());
        ArrayList usageList = tinyDB.getListObject("usageArray", AppData.class);

        updateData(usageList);
        mAdapter.updateData(usageList);
    }

}