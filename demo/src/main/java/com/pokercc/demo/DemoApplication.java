package com.pokercc.demo;

import android.app.Application;

import com.pokercc.appinjector.AppInjectorHub;

/**
 * Created by cisco on 2017/11/20.
 */

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppInjectorHub appInjectorHub = AppInjectorHub.getInstance();
        appInjectorHub.registerAppInjectorsIgnoreError(
                "com.pokercc.demo.ToastUtil$ToastUtilAppInjector"
        );
        appInjectorHub.init(this);
    }
}
