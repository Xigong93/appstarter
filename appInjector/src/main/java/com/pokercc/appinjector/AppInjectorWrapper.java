package com.pokercc.appinjector;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cisco on 2017/11/21.
 */

public class AppInjectorWrapper implements IAppInjector {
    public static final String LOG_TAG = "AppInjectorWrapper";
    private final IAppInjector appInjector;
    private final ProfileInfo profileInfo;

    public AppInjectorWrapper(IAppInjector appInjector) {
        this.appInjector = appInjector;
        this.profileInfo = new ProfileInfo(getName());
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


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AppInjectorWrapper) {
            return TextUtils.equals(((AppInjectorWrapper) obj).getName(), this.getName());
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public String getName() {
        return appInjector.getClass().getName();
    }

    public static class ProfileInfo {
        private long startTime, endTime;
        private final String className;

        public ProfileInfo(String className) {
            this.className = className;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ProfileInfo) {
                return TextUtils.equals(((ProfileInfo) obj).className, this.className);
            }
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return className.hashCode();
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

        public long getUsedTime() {
            return getEndTime() - getStartTime();
        }

        @Override
        public String toString() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", this.className)
                        .put("startTime", this.startTime)
                        .put("endTime", this.endTime)
                        .put("usedTime", this.getUsedTime());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }
    }
}
