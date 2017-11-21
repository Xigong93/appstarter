package com.pokercc.demo;

import android.app.Application;

import com.pokercc.appinjector.AppInjectorHub;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cisco on 2017/11/20.
 */

public class DemoApplication extends Application {

    private static final List<String> APP_INJECTORS = Arrays.asList(
            "com.pokercc.demo.ToastUtil$ToastUtilAppInjector"

    );

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjectorHub
                .Builder
                .create(this)
                .supportAndroidManifest()
                .addAppInjectorList(APP_INJECTORS)
                .build()
                .dispatchAppCreate(this);
    }
}
