package com.liteappz.appusage2.Fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liteappz.appusage2.Adapters.TimeAdapter;
import com.liteappz.appusage2.ArcProgress;
import com.liteappz.appusage2.R;
import com.liteappz.appusage2.Utils.TinyDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.UsageUtils;

public class TimeFragment extends Fragment {
    public TimeAdapter mAdapter;
    ArcProgress totalTimeArcProgress, highestUsageArcProgress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_time, container, false);
        init(v);

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
        totalTimeArcProgress = v.findViewById(R.id.totalScreenTimeArcProgress_TimeFrag);
        highestUsageArcProgress = v.findViewById(R.id.highestAppScreenTimeArcProgress_TimeFrag);


        RecyclerView mRecycler = v.findViewById(R.id.recyclerViewAppTimeUsage_TimeFrag);
        mAdapter = new TimeAdapter(v, getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

    }

    public void updateData (ArrayList usageList){
        Long totalTime = Long.valueOf(0);
        for (Object usageObject: usageList) {
            AppData usage = (AppData) usageObject;
            totalTime+=usage.mUsageTime;
        }
        totalTimeArcProgress.setCentralText(UsageUtils.humanReadableMillis(totalTime));
        Collections.sort(usageList, new Comparator<AppData>() {
            @Override
            public int compare(AppData t1, AppData t2) {
                return Long.compare(t2.mUsageTime, t1.mUsageTime);
            }
        });
        try {
            AppData firstObject = (AppData) usageList.get(0);
            highestUsageArcProgress.setBottomText(firstObject.mName);
            highestUsageArcProgress.setCentralText(UsageUtils.humanReadableMillis(firstObject.mUsageTime));
            highestUsageArcProgress.setProgress((int) (firstObject.mUsageTime * 100.0 / totalTime));
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