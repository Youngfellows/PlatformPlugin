package com.pandora.channel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvChannel = (TextView) findViewById(R.id.tv_channel);
        tvChannel.setText(getChannel(this));
    }

    /**
     * 获取渠道号
     *
     * @param context
     * @return
     */
    public String getChannel(Context context) {
        String channel = "";
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            channel = applicationInfo.metaData.getString("CHANNEL");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "忽略找不到包信息的异常...");
            channel = "没有任何渠道包信息...";
        }
        return channel;
    }
}
