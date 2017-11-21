package com.pokercc.appinjector;

import android.app.Application;
import android.util.Log;

/**
 * Created by Cisco on 2017/11/21.
 */

public class AppInjectorWrapper implements IAppInjector {
    public static final String LOG_TAG = "AppInjectorWrapper";
    private final IAppInjector appInjector;
    private final ProfileInfo profileInfo;

    public AppInjectorWrapper(IAppInjector appInjector) {
        this.appInjector = appInjector;
        this.profileInfo = new ProfileInfo(appInjector.getClass().getName());
    }

    @Override
    public void onAppCreate(Application app) {
        profileInfo.setStartTime(System.currentTimeMillis());
        Log.i(LOG_TAG, "inject app context to " + appInjector);
        this.appInjector.onAppCreate(app);
        profileInfo.setEndTime(System.currentTimeMillis());
    }

    public ProfileInfo getProfileInfo() {
        return profileInfo;
    }

    public static class ProfileInfo {
        private long startTime, endTime;
        private String className;

        public ProfileInfo(String className) {
            this.className = className;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
