package com.pokercc.mavenlibsample;

import android.app.AlertDialog;
import android.app.Application;
import android.os.Handler;
import android.view.WindowManager;

import com.pokercc.appstarter.OnAppCreate;

import java.util.Arrays;

/**
 * Created by cisco on 2017/11/21.
 */

public class AppUpdate {


    @OnAppCreate
    public static void onAppCreate(final Application app, final String[] args) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog
                        .Builder(app.getApplicationContext())
                        .setTitle("来自app更新库")
                        .setMessage("有新版本的app了" + "\n" + Arrays.toString(args))
                        .setPositiveButton(android.R.string.ok, null)
                        .create();
                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alertDialog.show();
            }
        }, 5000);
    }
}
