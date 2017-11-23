package com.pokercc.appinjector.Injectorfinder;

import android.content.Context;

import com.pokercc.appinjector.OnAppCreateMethod;

import java.util.List;

/**
 * Created by Cisco on 2017/11/21.
 */

public class ClassNameInjectorFinder extends AbsInjectorFinder {

    private static final String LOG_TAG = "ClassNameInjectorFinder";
    private final List<OnAppCreateMethod> onAppCreateMethods;

    public ClassNameInjectorFinder(List<OnAppCreateMethod> onAppCreateMethods) {
        this.onAppCreateMethods = onAppCreateMethods;
    }

    @Override
    public List<OnAppCreateMethod> getAppInjectors(Context context) {
        return this.onAppCreateMethods;
    }
}
