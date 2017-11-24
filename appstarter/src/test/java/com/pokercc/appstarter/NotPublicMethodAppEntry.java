package com.pokercc.appstarter;

import android.app.Application;

/**
 * Created by cisco on 2017/11/25.
 */
public class NotPublicMethodAppEntry implements TestAppEntry {
    @OnAppCreate
    private void onAppCreate(Application application, String... arg) {
    }
}
