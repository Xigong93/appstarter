package com.pokercc.appinjector;

import android.app.Application;
import android.os.Looper;
import android.util.Log;

import com.pokercc.appinjector.Injectorfinder.AnnotationAppInjectorFinder;
import com.pokercc.appinjector.Injectorfinder.ClassNameInjectorFinder;
import com.pokercc.appinjector.Injectorfinder.IAppInjectorFinder;
import com.pokercc.appinjector.Injectorfinder.ManifestAppInjectorFinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 针筒，可能有多个针筒，要注意，避免重复的问题
 * Created by cisco on 2017/11/20.
 */

public final class AppInjectorHub {
    private static final String LOG_TAG = "pokercc.AppInjectorHub";

    private static final Set<AppInjectorWrapper> APP_INJECTORS = new LinkedHashSet<>();


    private AppInjectorHub(Builder builder) {
        APP_INJECTORS.addAll(builder.appInjectorWrapperList);
    }


    public void dispatchAppCreate(Application app) {
        checkAppNotNull(app);
        checkThread();
        Iterator<AppInjectorWrapper> iterator = APP_INJECTORS.iterator();
        for (AppInjectorWrapper appInjector = iterator.next(); iterator.hasNext(); iterator.remove()) {
            appInjector.onAppCreate(app);
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

    public static final class Builder {
        private final Application app;
        private final List<AppInjectorWrapper> appInjectorWrapperList = new ArrayList<>();

        public static Builder create(Application application) {
            return new Builder(application);

        }

        public Builder(Application application) {
            checkAppNotNull(application);
            this.app = application;
            addAppInjectorFinder(new AnnotationAppInjectorFinder());
        }


        public Builder addAppInjectorList(List<String> appInjectorClassNames) {
            return addAppInjectorFinder(new ClassNameInjectorFinder(appInjectorClassNames));
        }

        public Builder addAppInjectorFinder(IAppInjectorFinder appInjectorFinder) {
            List<IAppInjector> iAppInjectorList = appInjectorFinder.getAppInjectors(app);
            if (iAppInjectorList != null) {
                for (IAppInjector appInjector : iAppInjectorList) {
                    appInjectorWrapperList.add(new AppInjectorWrapper(appInjector));
                }
            }
            return this;
        }

        public Builder supportAndroidManifest() {
            return addAppInjectorFinder(new ManifestAppInjectorFinder());
        }


        public AppInjectorHub build() {
            checkThread();
            return new AppInjectorHub(this);
        }
    }

}
