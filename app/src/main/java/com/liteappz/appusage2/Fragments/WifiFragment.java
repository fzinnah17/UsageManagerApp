package com.liteappz.appusage2.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liteappz.appusage2.Adapters.LaunchedAdapter;
import com.liteappz.appusage2.Adapters.WifiAdapter;
import com.liteappz.appusage2.ArcProgress;
import com.liteappz.appusage2.R;
import com.liteappz.appusage2.Utils.TinyDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.UsageUtils;


public class WifiFragment extends Fragment {
    ArcProgress totalWifiUsedArcProgress, highestWifiUsageArcProgress;
    WifiAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wifi, container, false);
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
        totalWifiUsedArcProgress = v.findViewById(R.id.wifiUsageArcProgress_WifiFrag);
        highestWifiUsageArcProgress = v.findViewById(R.id.highestWifiUsageArcProgress_WifiFrag);

        RecyclerView mRecycler = v.findViewById(R.id.recyclerViewWifiUsage_WifiFrag);
        mAdapter = new WifiAdapter(getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

    }

    public void updateData(ArrayList usageList){
        Long totalBytes = Long.valueOf(0);
        for (Object usageObject: usageList) {
            AppData usage = (AppData) usageObject;
            totalBytes+=usage.mWifi;
        }
        totalWifiUsedArcProgress.setCentralText(UsageUtils.humanReadableByteCount(totalBytes));
        try {
            Collections.sort(usageList, new Comparator<AppData>() {
                @Override
                public int compare(AppData t1, AppData t2) {
                    return Long.compare(t2.mWifi, t1.mWifi);
                }
            });

            AppData firstObject = (AppData) usageList.get(0);
            highestWifiUsageArcProgress.setBottomText(firstObject.mName);
            highestWifiUsageArcProgress.setCentralText(UsageUtils.humanReadableByteCount(firstObject.mWifi));
            highestWifiUsageArcProgress.setProgress((int) (firstObject.mWifi * 100.0 / totalBytes));
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