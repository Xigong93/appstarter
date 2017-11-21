package com.pokercc.mavenlibsample;

import android.app.AlertDialog;
import android.app.Application;
import android.os.Handler;
import android.view.WindowManager;

import com.pokercc.appinjector.IAppInjector;

/**
 * Created by cisco on 2017/11/21.
 */

public class AppUpdate implements IAppInjector {


    @Override
    public void onAppCreate(final Application app) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog
                        .Builder(app.getApplicationContext())
                        .setTitle("来自app更新库")
                        .setMessage("有新版本的app了")
                        .setPositiveButton(android.R.string.ok, null)
                        .create();
                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alertDialog.show();
            }
        }, 5000);
    }
}
