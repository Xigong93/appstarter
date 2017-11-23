package com.pokercc.demo;

import android.app.Application;

import com.pokercc.appstarter.AppStarter;
import com.pokercc.appstarter.OnAppCreateMethod;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cisco on 2017/11/20.
 */

public class DemoApplication extends Application {

    private static final List<OnAppCreateMethod> ON_APP_CREATE_METHODS = Arrays.asList(
            new OnAppCreateMethod(ClassNameToastUtil.class)
    );

    @Override
    public void onCreate() {
        super.onCreate();
        AppStarter
                .newBuilder(this)
                .supportAndroidManifest()
                .addAppEntryList(ON_APP_CREATE_METHODS)
                .build()
                .dispatchAppCreate(this);
    }
}
