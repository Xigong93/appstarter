package com.pokercc.appinjector.Injectorfinder;

import android.content.Context;

import java.util.List;

/**
 * Created by Cisco on 2017/11/21.
 */

public class ClassNameInjectorFinder extends AbsInjectorFinder {

    private static final String LOG_TAG = "ClassNameInjectorFinder";
    private final List<String> classNameList;

    public ClassNameInjectorFinder(List<String> classNameList) {
        this.classNameList = classNameList;
    }

    @Override
    protected List<String> getAppInjectorClassNames(Context context) {
        return this.classNameList;

    }
}
