package com.pokercc.appinjector;

import android.app.Application;
import android.util.Log;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by cisco on 2017/11/20.
 */

public final class AppInjectorHub implements IAppInjector {
    private static final String LOG_TAG = "pokercc.AppInjectorHub";
    private final static AppInjectorHub INSTANCE = new AppInjectorHub();
    private final Set<IAppInjector> mIAppInjectorList = new LinkedHashSet<>();

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

    }

    private void addAnnotation() {
//        getClass().getClassLoader().

    }

    @Override
    public void init(Application app) {
        findAppInject();
        for (IAppInjector appInjector : mIAppInjectorList) {
            Log.i(LOG_TAG, "inject application context to " + appInjector);
            appInjector.init(app);
        }
    }

}
