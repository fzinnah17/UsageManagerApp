package com.liteappz.appusage2.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.liteappz.appusage2.MainActivity;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.liteappz.appusage2.R;
import com.liteappz.appusage2.Utils.util;
import bot.box.appusage.contract.UsageContracts;
import bot.box.appusage.handler.Monitor;
import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.Duration;
import bot.box.appusage.utils.UsageUtils;

public class TimeUsageService extends Service implements UsageContracts.View{
    String CHANNEL_ID = "my_channel_01";
    String CHANNEL_ID2 = "my_channel_02";
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_LOW);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }

        Timer timerObj = new Timer();
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
//                Toast.makeText(TimeUsageService.this, "In timer task", Toast.LENGTH_SHORT).show();
                if (Monitor.hasUsagePermission()) {
                    Monitor.scan().getAppLists(TimeUsageService.this).fetchFor(Duration.TODAY);
                } else {
                    Monitor.requestUsagePermission();
                }
            }
        };
        timerObj.schedule(timerTaskObj, 0, 4000);
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void getUsageData(List<AppData> usageData, long mTotalUsage, int duration) {
        Map map = util.loadMap(this, "timeLimitsMap");
//        Toast.makeText(this, "Service getUsageData\n"+map.size(), Toast.LENGTH_SHORT).show();
        if (!map.isEmpty()){
            int notification_id = 0;
            for (AppData usageObj: usageData){
                String appName = usageObj.mName;
                if (map.containsKey(appName)){
                    Long usageLimitValue = (Long) map.get(appName);
                    if (usageObj.mUsageTime>=usageLimitValue && usageLimitValue>0){
                        showOverUsageNotification(appName, UsageUtils.humanReadableMillis(usageLimitValue), notification_id, this);
                        Toast.makeText(this, appName+" crossed limit of "+UsageUtils.humanReadableMillis(usageLimitValue), Toast.LENGTH_LONG).show();
                        notification_id = notification_id+1;
                    }
                }
            }
        }
    }

    public void showOverUsageNotification(String appName, String appLimit, int notification_id, Context ctx){
        Intent intent = new Intent(ctx, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT>=26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID2,
                    "Channel title2",
                    NotificationManager.IMPORTANCE_LOW);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            String notification_message = appName + " has crossed your defined usage limit of " + appLimit + " . Please stop using it for today.";
            NotificationCompat.Builder notification = new NotificationCompat.Builder(TimeUsageService.this, CHANNEL_ID2)
                    .setContentTitle("ALERT!")
                    .setContentText(notification_message)
                    .setSmallIcon(R.drawable.logo_circle)
                    .setChannelId(CHANNEL_ID2)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notification_message))
                    .setSound(null)
                    .setContentIntent(contentIntent);


            NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notification_id, notification.build());
        }
    }
}