package com.pokercc.appinjector;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.pokercc.appinjector.Injectorfinder.AnnotationAppInjectorFinder;
import com.pokercc.appinjector.Injectorfinder.ClassNameInjectorFinder;
import com.pokercc.appinjector.Injectorfinder.IAppInjectorFinder;
import com.pokercc.appinjector.Injectorfinder.ManifestAppInjectorFinder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by cisco on 2017/11/20.
 */

public final class AppInjectorHub {
    private static final String LOG_TAG = "AppInjectorHub";

    private static final Map<String, AppInjectorWrapper.ProfileInfo> APP_INJECTORS_PROFILE_MAP = new LinkedHashMap<>();


    private final List<AppInjectorWrapper> appInjectorWrappers = new ArrayList<>();
    private boolean printProfile;

    private ProfileHandler profileHandler;
    private static final AtomicBoolean SUPPORT_ANDROID_MANIFEST = new AtomicBoolean();

    private AppInjectorHub(Builder builder) {
        this.printProfile = builder.printProfile;
        this.appInjectorWrappers.addAll(builder.appInjectorWrapperList);
        SUPPORT_ANDROID_MANIFEST.compareAndSet(false, builder.supportAndroidManifest);
    }


    /**
     * dispatch application onCreate event,all appInjector's onAppCreate method will be perform
     *
     * @param app
     */
    public void dispatchAppCreate(Application app) {
        checkAppNotNull(app);
        checkThread();
        for (AppInjectorWrapper appInjector : appInjectorWrappers) {
            AppInjectorWrapper.ProfileInfo profileInfo = APP_INJECTORS_PROFILE_MAP.get(appInjector.getName());
            if (profileInfo != null) {
                throw new AppInjectorException("duplicate " + appInjector.getName());
            } else {
                appInjector.onAppCreate(app);
                APP_INJECTORS_PROFILE_MAP.put(appInjector.getName(), appInjector.getProfileInfo());
            }
        }

        if (this.printProfile) {
            if (profileHandler == null) {
                profileHandler = new ProfileHandler();
            }
            profileHandler.printProfile();
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

    private static class ProfileHandler extends Handler {
        private ProfileHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(LOG_TAG, "<< ======================================================== >>");
            for (Map.Entry<String, AppInjectorWrapper.ProfileInfo> entry : APP_INJECTORS_PROFILE_MAP.entrySet()) {
                AppInjectorWrapper.ProfileInfo profileInfo = entry.getValue();
                Log.i(LOG_TAG, profileInfo.toString());
            }
            Log.i(LOG_TAG, "<< ======================================================== >>");

        }

        private void printProfile() {
            // avoid android library and application both use app inject  print duplication report,so use handler to delay ;
            removeCallbacksAndMessages(null);
            sendEmptyMessageDelayed(0, 5000);
        }
    }
    public static Builder newBuilder(Application application) {
        return new Builder(application);

    }
    public static final class Builder {
        private final Application app;
        private final List<AppInjectorWrapper> appInjectorWrapperList = new ArrayList<>();
        private boolean printProfile;
        private boolean supportAndroidManifest;


        public Builder(Application application) {
            checkAppNotNull(application);
            this.app = application;
            addAppInjectorFinder(new AnnotationAppInjectorFinder());
        }


        /**
         * add your appInjectors class name ,reminds your appInjectors cant't be proguard
         *
         * @param appInjectorClassNames
         * @return
         */
        public Builder addAppInjectorList(List<String> appInjectorClassNames) {
            return addAppInjectorFinder(new ClassNameInjectorFinder(appInjectorClassNames));
        }

        /**
         * add appInjectorFinder to find your AppInjectors
         *
         * @param appInjectorFinder
         * @return
         */
        public Builder addAppInjectorFinder(IAppInjectorFinder appInjectorFinder) {
            List<IAppInjector> iAppInjectorList = appInjectorFinder.getAppInjectors(app);
            if (iAppInjectorList != null) {
                for (IAppInjector appInjector : iAppInjectorList) {
                    appInjectorWrapperList.add(new AppInjectorWrapper(appInjector));
                }
            }
            return this;
        }

        /**
         * set true to print profile about appInjects used time
         *
         * @return
         */
        public Builder printProfile(boolean printProfile) {
            this.printProfile = printProfile;
            return this;
        }


        /**
         * enable android manifest config
         * such as:
         * <p>
         * <meta-data android:name="appinject.{ppInjectorClassName}" android:value="{appInjectorClassName}/>"/>
         * only first AppInjectorHub ,enable this
         *
         * @return
         */
        public Builder supportAndroidManifest() {
            if (SUPPORT_ANDROID_MANIFEST.get()) {
                Log.i(LOG_TAG, "ignore supportAndroidManifest,because this only need set once in an application");
                return this;
            }
            supportAndroidManifest = true;
            return addAppInjectorFinder(new ManifestAppInjectorFinder());
        }


        public AppInjectorHub build() {
            checkThread();
            return new AppInjectorHub(this);
        }

    }

}
