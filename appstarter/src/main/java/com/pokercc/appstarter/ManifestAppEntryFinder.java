package com.pokercc.appstarter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
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
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            Bundle metaData = applicationInfo.metaData;
            if (metaData != null) {
                for (String name : metaData.keySet()) {
                    if (name.startsWith(AppStarter.SCHEME)) {
                        Uri uri = Uri.parse(name);
                        String className = uri.getHost();
                        if (TextUtils.isEmpty(className)) {
                            throw new RuntimeException("className not find in " + name);
                        }
                        String value = metaData.getString(name);
                        appEntries.add(new AppEntry(className, parseArgs(value)));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            //ignore
        }
        return appEntries;
    }


    private String[] parseArgs(String argsStr) {
        if (TextUtils.isEmpty(argsStr)) {
            return null;
        }
        final List<String> args = new ArrayList<>();
        for (String s : argsStr.split(" ")) {
            if (!TextUtils.isEmpty(s)) {
                args.add(s);
            }
        }
        return args.toArray(new String[]{});
    }
}
