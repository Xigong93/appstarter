package com.pokercc.appinjector.Injectorfinder;

import android.content.Context;

import com.pokercc.appinjector.IAppInjector;
import com.pokercc.appinjector.OnAppCreateMethod;

import java.util.List;


/**
 * Created by Cisco on 2017/11/21.
 */

public interface IAppInjectorFinder {

    List<OnAppCreateMethod> getAppInjectors(Context context);
}
