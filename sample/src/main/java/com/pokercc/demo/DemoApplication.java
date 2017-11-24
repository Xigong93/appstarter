package com.pokercc.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.pokercc.appstarter.AppStarter;
import com.pokercc.appstarter.OnAppCreateMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cisco on 2017/11/20.
 */

public class DemoApplication extends Application {
    public static final String LOG_TAG = "DemoApplication";

    private static final List<OnAppCreateMethod> ON_APP_CREATE_METHODS = Arrays.asList(
            new OnAppCreateMethod(ClassNameToastUtil.class)
    );

    public boolean isAppMainProcess(Context context) {
        return TextUtils.equals(getCurrentProcessName(), getApplicationInfo().processName);
    }

    private String getCurrentProcessName() {
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == android.os.Process.myPid()) {
                return process.processName;
            }
        }
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {

            Log.i(LOG_TAG, new JSONObject()
                    .put("processName", getApplicationInfo().processName)
                    .put("currentProcessName", getCurrentProcessName())
                    .put("isMainProcess", isAppMainProcess(this))
                    .toString()
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppStarter
                .newBuilder(this)
                .supportAndroidManifest()
                .addAppEntryList(ON_APP_CREATE_METHODS)
                .build()
                .dispatchAppCreate(this);
    }
}
