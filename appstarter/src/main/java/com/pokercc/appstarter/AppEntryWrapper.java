package com.pokercc.appstarter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Cisco on 2017/11/21.
 */

public class AppEntryWrapper implements IAppEntry, Comparable<AppEntryWrapper> {

    private final AppEntry appEntry;

    public AppEntryWrapper(AppEntry mIAppEntry) {
        this.appEntry = mIAppEntry;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AppEntryWrapper) {
            return TextUtils.equals(((AppEntryWrapper) obj).getName(), this.getName());
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String getName() {
        return appEntry.getName();
    }

    @Override
    public void onAppCreate(Application app) {
        long beginTime = System.currentTimeMillis();
        this.appEntry.onAppCreate(app);
        long endTime = System.currentTimeMillis();
        Log.i(AppStarter.LIB_NAME, "start " + getName() + " +" + (endTime - beginTime) + "ms");
    }

    @Override
    public int compareTo(AppEntryWrapper other) {
        return this.appEntry.compareTo(other.appEntry);
    }
}