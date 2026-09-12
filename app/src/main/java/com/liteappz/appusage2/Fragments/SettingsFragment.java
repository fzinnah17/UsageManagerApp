package com.liteappz.appusage2.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.liteappz.appusage2.R;
import com.liteappz.appusage2.Utils.TinyDB;
import com.liteappz.appusage2.Utils.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.UsageUtils;

import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;

public class SettingsFragment extends Fragment {
    Button resetAllLimitsButton, exportToCSVButton;
    ArrayList usageList;
    TinyDB tinydb;
    FileWriter fr;
    String FILENAME = "usage_data.csv";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        init(v);

        tinydb = new TinyDB(getActivity());
        usageList = tinydb.getListObject("usageArray", AppData.class);

        exportToCSVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setMessage("Exporting "+ tinydb.getString("modeSelected") + " usage data...");
                pd.show();

                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                File myDir = new File(root);
                myDir.mkdirs();

                File file = new File(myDir, FILENAME);
                if (file.exists())
                    file.delete();
                try {
                    fr = new FileWriter(file, true);
                    fr.flush();

                    String columnNamesList = "App Name,Usage Time,Launch Counts,Wifi Usage";
                    fr.write(columnNamesList);
                    fr.write("\n");
                    for (Object usageObj: usageList){
                        AppData usage = (AppData) usageObj;
                        String name = usage.mName;
                        String usageTime = UsageUtils.humanReadableMillis(usage.mUsageTime);
                        int launch_counts = usage.mCount;
                        String wifi_usage = UsageUtils.humanReadableByteCount(usage.mWifi);

                        fr.write(name+","+usageTime+","+launch_counts+","+wifi_usage);
                        fr.write("\n");
                    }

                    fr.close();
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Successfully saved in "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    pd.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error exporting data as CSV", Toast.LENGTH_LONG).show();

                }
            }
        });

        resetAllLimitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map emptyMap = new HashMap();
                util.saveMap(getActivity(), "timeLimitsMap", emptyMap);

                util.stopTimeUsageService(getActivity());
                Toast.makeText(getActivity(), "All usage limits removed successfully", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void init(View v){
        resetAllLimitsButton = v.findViewById(R.id.resetAllLimits_SettingsFrag);
        exportToCSVButton = v.findViewById(R.id.exportToCSV_SettingsFrag);
    }
}