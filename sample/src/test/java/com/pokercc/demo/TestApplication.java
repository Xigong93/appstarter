package com.pokercc.demo;

import android.app.Application;

/**
 * Created by Cisco on 2017/11/25.
 */

public class TestApplication extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

    }
}
