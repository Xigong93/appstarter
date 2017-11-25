package com.pokercc.appstarter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Cisco on 2017/11/21.
 */

public class ManifestAppEntryFinder implements IAppInjectorFinder {

    @Override
    public List<OnAppCreateMethod> getAppInjectors(Context context) {

        List<OnAppCreateMethod> onAppCreateMethods = new ArrayList<>();

//        Set<OnAppCreateMethod> onAppCreateMethodSet = new LinkedHashSet<>();

        try {
            Bundle metaData = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData;
            if (metaData != null) {
                for (String name : metaData.keySet()) {
                    if (name.startsWith(AppStarter.SCHEME)) {
                        URI uri = URI.create(name);
                        String className = uri.getRawAuthority();
                        if (TextUtils.isEmpty(className)) {
                            throw new RuntimeException("className not find in " + name);
                        }
                        String value = metaData.getString(name);
                        String[] args = TextUtils.isEmpty(value) ? null : value.split(" ");
                        OnAppCreateMethod onAppCreateMethod = new OnAppCreateMethod(className, args);


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
