package com.pokercc.demo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.pokercc.appinjector.compiler.AppInject;
import com.pokercc.appinjector.IAppInjector;

/**
 * Created by cisco on 2017/11/20.
 */
@Keep
public class SpUtil {

    private static Application application;

    private static final String CONFIG_FILE = "app_config";

    @Keep
    @AppInject
    public static class AppInjectorImp implements IAppInjector {

        @Override
        public void onAppCreate(Application app) {
            application = app;
        }
    }


    private SharedPreferences getSp() {
        return application.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
    }

    public void save(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            throw new NullPointerException("key must not be empty");
        }
        getSp().edit().putString(key, value).apply();
    }

    @Nullable
    public String getStr(String key) {
        return getSp().getString(key, null);
    }

}
