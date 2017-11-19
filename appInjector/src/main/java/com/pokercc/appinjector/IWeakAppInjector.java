package com.pokercc.appinjector;

import android.app.Application;

import java.lang.ref.WeakReference;

/**
 * Created by cisco on 2017/11/20.
 */

public interface IWeakAppInjector {
    void init(WeakReference<Application> applicationWeakReference);
}
