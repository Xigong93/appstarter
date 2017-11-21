package com.pokercc.appinjector.Injectorfinder;

import android.content.Context;
import android.util.Log;

import com.pokercc.appinjector.AppInjectorException;
import com.pokercc.appinjector.IAppInjector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cisco on 2017/11/21.
 */

public abstract class AbsInjectorFinder implements IAppInjectorFinder {

    private static final String LOG_TAG = "AbsInjectorFinder";


    @Override
    public final List<IAppInjector> getAppInjectors(Context context) {
        List<IAppInjector> appInjectorList = new ArrayList<>();
        List<String> appInjectorClassNames = this.getAppInjectorClassNames(context);
        for (int i = 0; appInjectorClassNames != null && i < appInjectorClassNames.size(); i++) {
            try {
                Class<?> clazz = Class.forName(appInjectorClassNames.get(i));
                Object injector = clazz.newInstance();
                if (injector instanceof IAppInjector) {
                    appInjectorList.add((IAppInjector) injector);
                    Log.i(LOG_TAG, "add app injector:" + injector);
                } else {
                    throw new RuntimeException("must implements " + IAppInjector.class.getName());
                }
            } catch (ClassNotFoundException e) {
                throw new AppInjectorException(e);
            } catch (IllegalAccessException e) {
                throw new AppInjectorException(e);
            } catch (InstantiationException e) {
                throw new AppInjectorException(e);
            }
        }
        return appInjectorList;
    }

    protected abstract List<String> getAppInjectorClassNames(Context context);
}
