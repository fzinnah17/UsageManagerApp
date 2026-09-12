package com.liteappz.appusage2.Adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.liteappz.appusage2.R;
import com.liteappz.appusage2.Utils.TinyDB;
import com.liteappz.appusage2.Utils.util;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.UsageUtils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    private List<AppData> mData;
    private Context mContext;
    private View mView;

    public TimeAdapter(View view, Context mContext) {
        this.mContext = mContext;
        this.mView = view;
    }

    public void updateData(List<AppData> data) {
        mData = data;
        Collections.sort(mData, new Comparator<AppData>() {
            @Override
            public int compare(AppData t1, AppData t2) {
                return Long.compare(t2.mUsageTime, t1.mUsageTime);
            }
        });
        notifyDataSetChanged();
    }

//    private AppData getUsageByPosition(int position) {
//        if (mData.size() > position) {
//            return mData.get(position);
//        }
//        return null;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_time_spent, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        AppData item = getUsageByPosition(position);
        AppData item = mData.get(position);
        holder.mName.setText(item.mName);

        holder.mUsage.setText(UsageUtils.humanReadableMillis(item.mUsageTime));

//        holder.mTime.setText(String.format(Locale.getDefault(),
//                "%s", "Last Launch " +
//                        new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()).format(new Date(item.mEventTime))));

//        holder.launch_count.setText(item.mCount + " " + "Times Launched");

//        holder.data_used.setText(UsageUtils.humanReadableByteCount(item.mWifi + item.mMobile));

        Glide.with(mContext)
                .load(UsageUtils.parsePackageIcon(item.mPackageName, R.mipmap.ic_launcher)).
                transition(new DrawableTransitionOptions().crossFade())
                .into(holder.mIcon);

        holder.parent.setOnClickListener(v -> {
            TinyDB tinydb = new TinyDB(mContext);
            if (tinydb.getString("modeSelected").equals("Today")) {
//                Toast.makeText(mContext, holder.mName.getText().toString(), Toast.LENGTH_SHORT).show();
                showPopupWindow(item.mName, mView, mContext);


            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else return 0;
    }

    public void showPopupWindow(String appName, View view, Context context) {
        Map limitsMap = util.loadMap(context, "timeLimitsMap");
        // inflate the layout of the popup window

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_limit_selection, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextInputEditText appNameEditText = popupView.findViewById(R.id.appNameTextField_PopupWindow);
        appNameEditText.setText(appName);

        TextInputEditText appTimeLimitEditText = popupView.findViewById(R.id.appTimeLimitTextField_PopupWindow);
        Button setLimitButton = popupView.findViewById(R.id.setLimitButton_PopupWindow);
        try {
            appTimeLimitEditText.setText(String.valueOf((int) (Long.parseLong(limitsMap.get(appName).toString())/60000)));
        }catch (Exception ignored){}

        setLimitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minutes = appTimeLimitEditText.getText().toString().trim();
                if (minutes.equals("")){
                    Toast.makeText(context, "Please enter a valid minutes value from 0 to 1439", Toast.LENGTH_SHORT).show();
                }else {
                    int minutesVal = Integer.parseInt(minutes);
                    Long minutesinMills = (long) (minutesVal * 60 * 1000);
                    limitsMap.put(appName, minutesinMills);
                    util.saveMap(context, "timeLimitsMap", limitsMap);

                    popupWindow.dismiss();

                }
            }
        });


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout parent;
        private TextView mName;
        private TextView mUsage;
        private TextView mTime;
        private ImageView mIcon;
        private TextView launch_count;
        private TextView data_used;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            mName = itemView.findViewById(R.id.app_name);
            mUsage = itemView.findViewById(R.id.app_usage);
//            mTime = itemView.findViewById(R.id.app_time);
            mIcon = itemView.findViewById(R.id.app_image);
//            launch_count = itemView.findViewById(R.id.launch_count);
//            data_used = itemView.findViewById(R.id.data_used);
        }
    }
}