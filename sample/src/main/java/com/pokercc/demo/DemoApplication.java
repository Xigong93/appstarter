package com.pokercc.demo;

import android.app.Application;
import android.util.Log;

import com.pokercc.appstarter.AppStarter;
import com.pokercc.appstarter.AppEntry;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cisco on 2017/11/20.
 */

public class DemoApplication extends Application {

    public static final String LOG_TAG = "DemoApplication";
    private static final List<AppEntry> ON_APP_CREATE_METHODS = Arrays.asList(
            new AppEntry(ClassNameToastUtil.class)
    );

    private static int startCount = 0;


    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        AppStarter
                .newBuilder(this)
                .supportAndroidManifest()
                .supportSubProcess()
                .addAppEntryList(ON_APP_CREATE_METHODS)
                .build()
                .dispatchAppCreate(this);
        startCount++;
        Log.i(LOG_TAG, "startCount:" + startCount);
        application = this;
    }
}
