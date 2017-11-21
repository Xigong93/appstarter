package com.pokercc.appinjector.Injectorfinder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cisco on 2017/11/21.
 */

public class ManifestAppInjectorFinder extends AbsInjectorFinder {
    public static final String APP_INJECTOR_PREFIX = "appinject.";

    @Override
    protected List<String> getAppInjectorClassNames(Context context) {

        List<String> appInjectorClassNames = new ArrayList<>();
        try {
            Bundle metaData = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData;
            if (metaData != null) {
                for (String key : metaData.keySet()) {
                    if (key.toLowerCase().startsWith(APP_INJECTOR_PREFIX)) {
                        appInjectorClassNames.add(metaData.getString(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            //ignore
        }
        return appInjectorClassNames;
    }
}
