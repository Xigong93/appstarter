package com.pokercc.appinjector.Injectorfinder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.pokercc.appinjector.OnAppCreateMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cisco on 2017/11/21.
 */

public class ManifestAppInjectorFinder extends AbsInjectorFinder {
    public static final String APP_INJECTOR_PREFIX = "appinject://";

    @Override
    public List<OnAppCreateMethod> getAppInjectors(Context context) {

        List<OnAppCreateMethod> onAppCreateMethods = new ArrayList<>();

        try {
            Bundle metaData = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData;
            if (metaData != null) {
                for (String key : metaData.keySet()) {
                    if (key.toLowerCase().startsWith(APP_INJECTOR_PREFIX)) {
                        String value = metaData.getString(key);
                        String className = key.substring(APP_INJECTOR_PREFIX.length());
                        OnAppCreateMethod onAppCreateMethod = new OnAppCreateMethod(className, TextUtils.isEmpty(value) ? null : value.split(" "));
                        onAppCreateMethods.add(onAppCreateMethod);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            //ignore
        }
        return onAppCreateMethods;
    }
}
