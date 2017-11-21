package com.pokercc.appinjector.Injectorfinder;

import android.content.Context;

import com.pokercc.appinjector.IAppInjector;

import java.util.List;


/**
 * Created by Cisco on 2017/11/21.
 */

public interface IAppInjectorFinder {

    List<IAppInjector> getAppInjectors(Context context);
}
