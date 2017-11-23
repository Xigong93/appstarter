package com.pokercc.appstarter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cisco on 2017/11/21.
 */

public class ManifestAppEntryFinder implements IAppInjectorFinder {

    @Override
    public List<OnAppCreateMethod> getAppInjectors(Context context) {

        List<OnAppCreateMethod> onAppCreateMethods = new ArrayList<>();

        try {
            Bundle metaData = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData;
            if (metaData != null) {
                for (String key : metaData.keySet()) {
                    if (key.startsWith(AppStarter.SCHEME)) {
                        URI uri = URI.create(key);
                        String value = metaData.getString(key);
                        String[] args = TextUtils.isEmpty(value) ? null : value.split(" ");
                        OnAppCreateMethod onAppCreateMethod = new OnAppCreateMethod(uri.getHost(), args);
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
