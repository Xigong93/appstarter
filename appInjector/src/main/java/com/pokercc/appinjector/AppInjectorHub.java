package com.pokercc.appinjector;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 针筒，可能有多个针筒，要注意，避免重复的问题
 * Created by cisco on 2017/11/20.
 */

public final class AppInjectorHub implements IAppInjector {
    private static final String LOG_TAG = "pokercc.AppInjectorHub";
    private final static AppInjectorHub INSTANCE = new AppInjectorHub();
    private final Set<IAppInjector> mIAppInjectorList = new LinkedHashSet<>();
    private final Set<IWeakAppInjector> mIWeakAppInjectorList = new LinkedHashSet<>();

    private WeakReference<Application> mApplicationWeakReference;

    private AppInjectorHub() {

    }

    public static AppInjectorHub getInstance() {
        return INSTANCE;
    }


    /**
     * 通过代码的方式注册AppInjector
     *
     * @param classNames AppInjector的类名数组
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void registerAppInjectors(String... classNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (int i = 0; classNames != null && i < classNames.length; i++) {
            Class<?> clazz = Class.forName(classNames[i]);
            Object injector = clazz.newInstance();
            if (!(injector instanceof IAppInjector)) {
                throw new RuntimeException("must implements " + IAppInjector.class.getName());
            }
            Log.i(LOG_TAG, "add app injector:" + injector);
            mIAppInjectorList.add((IAppInjector) injector);
        }
    }

    public void registerAppInjectorsIgnoreError(String... classNames) {
        try {
            registerAppInjectors(classNames);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void findAppInject() {

        parseAndroidManifest();
    }

    private void parseAndroidManifest() {
        Application context = mApplicationWeakReference.get();
        getMetaValue(context, context.getString(R.string.AppInjector));

    }


    public static String getMetaValue(Context context, String metaKey) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            if (applicationInfo.metaData != null) {
                return applicationInfo.metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {
            //ignore
        }
        return null;
    }

    @Override
    public void init(Application app) {
        mApplicationWeakReference = new WeakReference<>(app);
        findAppInject();
        for (IAppInjector appInjector : mIAppInjectorList) {
            Log.i(LOG_TAG, "inject application context to " + appInjector);
            appInjector.init(app);
        }
        for (IWeakAppInjector weakAppInjector : mIWeakAppInjectorList) {
            Log.i(LOG_TAG, "inject application context to " + weakAppInjector);
            weakAppInjector.init(mApplicationWeakReference);
        }
    }


    /**
     * 支持注解的packageName,不能全部设置，不然可能会有权限问题，
     *
     * @param packageName
     */
    public void enablePackage(String... packageName) {

    }

}
