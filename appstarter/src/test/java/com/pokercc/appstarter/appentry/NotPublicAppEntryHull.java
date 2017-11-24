package com.pokercc.appstarter.appentry;

import android.app.Application;

import com.pokercc.appstarter.OnAppCreate;

import org.junit.Assert;

/**
 * Created by cisco on 2017/11/25.
 */
public class NotPublicAppEntryHull {
    private static class NotPublicAppEntry implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate(Application application, String... arg) {
            Assert.assertArrayEquals(new String[]{}, arg);
        }
    }
}
