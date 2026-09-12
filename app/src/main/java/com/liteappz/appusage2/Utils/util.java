package com.liteappz.appusage2.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.liteappz.appusage2.Services.TimeUsageService;

import org.json.JSONObject;

import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class util {

    public static void startTimeUsageService(Context context) {
        Intent serviceIntent = new Intent(context, TimeUsageService.class);
        ContextCompat.startForegroundService(context, serviceIntent);
    }
    public static void stopTimeUsageService(Context context) {
        Intent serviceIntent = new Intent(context, TimeUsageService.class);
        context.stopService(serviceIntent);
    }

    public static boolean isStoragePermissionGranted(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                                        Manifest.permission.READ_PHONE_STATE}, 1);
                return false;
            }
        }
        else {
//            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public static void saveMap(Context mContext, String key, Map<String,String> inputMap){
        SharedPreferences pSharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove(key).commit();
            editor.putString(key, jsonString);
            editor.commit();
        }
    }

    public static Map<String,Long> loadMap(Context mContext, String key){
        Map<String,Long> outputMap = new HashMap<String,Long>();
        SharedPreferences pSharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        try{
            if (pSharedPref != null){
                String jsonString = pSharedPref.getString(key, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String k = keysItr.next();
                    Long v = Long.valueOf(jsonObject.get(k).toString());
                    outputMap.put(k,v);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }
}
