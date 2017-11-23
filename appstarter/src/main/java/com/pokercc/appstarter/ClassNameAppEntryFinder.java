package com.pokercc.appstarter;

import android.content.Context;

import java.util.List;

/**
 * Created by Cisco on 2017/11/21.
 */

public class ClassNameAppEntryFinder implements IAppInjectorFinder  {

    private final List<OnAppCreateMethod> onAppCreateMethods;

    public ClassNameAppEntryFinder(List<OnAppCreateMethod> onAppCreateMethods) {
        this.onAppCreateMethods = onAppCreateMethods;
    }

    @Override
    public List<OnAppCreateMethod> getAppInjectors(Context context) {
        return this.onAppCreateMethods;
    }
}
