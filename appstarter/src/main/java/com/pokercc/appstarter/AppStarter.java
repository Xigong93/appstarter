package com.pokercc.appstarter;

import android.app.Application;
import android.os.Looper;
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

    private static final Set<String> APP_INJECTOR_SET = new HashSet<>();

    private final List<AppEntryWrapper> appEntryWrappers = new ArrayList<>();


    private AppStarter(Builder builder) {
        this.appEntryWrappers.addAll(builder.appEntryWrapperList);
    }


    /**
     * dispatch app onCreate event,all appInjector's onAppCreate method will be perform
     *
     * @param app
     */
    public void dispatchAppCreate(Application app) {
        checkAppNotNull(app);
        checkThread();
        for (AppEntryWrapper appInjector : appEntryWrappers) {
            if (APP_INJECTOR_SET.contains(appInjector.getName())) {
                throw new RuntimeException("duplicate register " + appInjector.getName());
            } else {
                appInjector.onAppCreate(app);
                APP_INJECTOR_SET.add(appInjector.getName());
            }
        }

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

        public Builder(Application application) {
            checkAppNotNull(application);
            this.app = application;

        }


        /**
         * add your appInjectors class name ,reminds your appInjectors cant't be proguard
         *
         * @param onAppCreateMethods
         * @return
         */
        public Builder addAppInjectorList(List<OnAppCreateMethod> onAppCreateMethods) {
            return addAppInjectorFinder(new ClassNameAppEntryFinder(onAppCreateMethods));
        }

        /**
         * add appInjectorFinder to find your AppInjectors
         *
         * @param appInjectorFinder
         * @return
         */
        public Builder addAppInjectorFinder(IAppInjectorFinder appInjectorFinder) {
            List<OnAppCreateMethod> onAppCreateMethods = appInjectorFinder.getAppInjectors(app);
            if (onAppCreateMethods != null) {
                for (OnAppCreateMethod onAppCreateMethod : onAppCreateMethods) {
                    appEntryWrapperList.add(new AppEntryWrapper(onAppCreateMethod));
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
            return addAppInjectorFinder(new ManifestAppEntryFinder());
        }


        public AppStarter build() {
            checkThread();
            return new AppStarter(this);
        }

    }

}
