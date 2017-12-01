package com.pokercc.demo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.pokercc.appstarter.OnAppCreate;

/**
 * Created by cisco on 2017/11/20.
 */

public class AndroidManifestToastUtil {

    private static Application application;


    @OnAppCreate
    public static void onAppCreate(Application app) {
        application = app;
        //testtest
    }

    ;
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void show(@NonNull String message) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("toast must show in main thread");
        }
        if (toast == null) {
            toast = Toast.makeText(application, message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }
}
