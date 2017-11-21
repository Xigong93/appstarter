package com.pokercc.appinjector;

import android.app.Application;
import android.util.Log;

import java.text.MessageFormat;

/**
 * Created by Cisco on 2017/11/21.
 */

public class AppInjectorWrapper implements IAppInjector {
    public static final String LOG_TAG = "AppInjectorWrapper";
    private final IAppInjector appInjector;

    public AppInjectorWrapper(IAppInjector appInjector) {
        this.appInjector = appInjector;
    }

    @Override
    public void onAppCreate(Application app) {
        long startTime = System.currentTimeMillis();
        Log.i(LOG_TAG, "inject app context to " + appInjector);
        this.appInjector.onAppCreate(app);
        long endTime = System.currentTimeMillis();
        Log.i(LOG_TAG, MessageFormat.format("inject app context use {} ", String.valueOf(endTime - startTime)));
    }
}
