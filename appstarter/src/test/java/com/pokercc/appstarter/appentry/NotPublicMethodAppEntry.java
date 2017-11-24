package com.pokercc.appstarter.appentry;

import android.app.Application;

import com.pokercc.appstarter.OnAppCreate;

/**
 * Created by cisco on 2017/11/25.
 */
public class NotPublicMethodAppEntry implements TestAppEntry {
    @OnAppCreate
    private void onAppCreate(Application application, String... arg) {
    }
}
