package com.pokercc.appstarter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * find AppEntry in AndroidManifest.xml
 * like
 * <meta-data android:name="appStarter://com.pokercc.appstarter.ManifestAppEntryFinderTest$NormalAppEntry1" android:value=""/>
 * remember an appEntry config must have set android:value ,if not can't get.
 * Created by Cisco on 2017/11/21.
 */

public class ManifestAppEntryFinder implements IAppEntryFinder {


    @Override
    public List<AppEntry> getAppEntries(Context context) {
        final List<AppEntry> appEntries = new ArrayList<>();
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
                        appEntries.add(new AppEntry(className, args));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            //ignore
        }
        Collections.sort(appEntries);
        return appEntries;
    }
}
