package com.pokercc.appstarter;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by cisco on 2017/11/20.
 */

public final class AppStarter {
    public static final String LIB_NAME = "AppStarter";
    public static final String SCHEME = "appStarter";

    private static final Set<String> APP_INJECTOR_SET = new HashSet<>();

    private final List<AppEntryWrapper> appEntryWrappers = new ArrayList<>();


    private final boolean supportSubProcess;


    private AppStarter(Builder builder) {
        this.appEntryWrappers.addAll(builder.appEntryWrapperList);
        this.supportSubProcess = builder.supportSubProcess;
    }


    /**
     * dispatch app onCreate event,all appInjector's onAppCreate method will be perform
     *
     * @param app
     */
    public void dispatchAppCreate(Application app) {
        checkAppNotNull(app);
        checkThread();

        // skip other process
        if (!supportSubProcess && !isAppMainProcess(app)) {
            Log.i(LIB_NAME, "skip subProcess application create" + getCurrentProcessName(app));
            return;
        }
        for (AppEntryWrapper appInjector : appEntryWrappers) {
            if (APP_INJECTOR_SET.contains(appInjector.getName())) {
                throw new RuntimeException("duplicate register " + appInjector.getName());
            }
            appInjector.onAppCreate(app);
            APP_INJECTOR_SET.add(appInjector.getName());
        }

    }


    /**
     * 是否是主进程
     *
     * @return
     */
    private static boolean isAppMainProcess(Application app) {
        return TextUtils.equals(getCurrentProcessName(app), app.getApplicationInfo().processName);
    }

    /**
     * 获取当前运行的进程名称
     *
     * @return
     */
    private static String getCurrentProcessName(Application app) {
        ActivityManager manager = (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == android.os.Process.myPid()) {
                    return process.processName;
                }
            }
        }
        return null;

    }

    private static void checkAppNotNull(Application app) {
        if (app == null) {
            throw new NullPointerException("app can't be null");
        }
    }

    private static void checkThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("must run on main thread!");
        }
    }


    public static Builder newBuilder(Application application) {
        return new Builder(application);

    }

    public static final class Builder {
        private final Application app;
        private final List<AppEntryWrapper> appEntryWrapperList = new ArrayList<>();
        private static final AtomicBoolean SUPPORT_ANDROID_MANIFEST = new AtomicBoolean();
        private boolean supportSubProcess;

        public Builder(Application application) {
            checkAppNotNull(application);
            this.app = application;

        }


        /**
         * add your appInjectors class name ,reminds your appInjectors cant't be proguard
         *
         * @param appEntries
         * @return
         */
        public Builder addAppEntryList(List<AppEntry> appEntries) {
            return addAppEntryFinder(new ClassNameAppEntryFinder(appEntries));
        }

        /**
         * add appInjectorFinder to find your AppInjectors
         *
         * @param appInjectorFinder
         * @return
         */
        public Builder addAppEntryFinder(IAppEntryFinder appInjectorFinder) {
            List<AppEntry> appEntries = appInjectorFinder.getAppEntries(app);
            if (appEntries != null) {
                for (AppEntry appEntry : appEntries) {
                    appEntryWrapperList.add(new AppEntryWrapper(appEntry));
                }
            }
            return this;
        }


        /**
         * enable android manifest config
         * such as:
         * <p>
         * <meta-data android:name="appinject.{ppInjectorClassName}" android:value="{appInjectorClassName}/>"/>
         * only first AppStarter ,enable this
         *
         * @return
         */
        public Builder supportAndroidManifest() {
            if (SUPPORT_ANDROID_MANIFEST.get()) {
                Log.i(LIB_NAME, "ignore supportAndroidManifest,because this only need set once in an app");
                return this;
            }
            SUPPORT_ANDROID_MANIFEST.compareAndSet(false, true);
            return addAppEntryFinder(new ManifestAppEntryFinder());
        }

        /**
         * enable subProcess Application create
         * default is false
         *
         * @return
         */
        public Builder supportSubProcess() {
            supportSubProcess = true;
            return this;
        }


        public AppStarter build() {
            checkThread();
            return new AppStarter(this);
        }

    }

}
