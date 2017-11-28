package com.pokercc.appstarter;

import android.app.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by cisco on 2017/11/27.
 */
@RunWith(RobolectricTestRunner.class)
public class AppEntryWrapperTest {
    @Test
    public void onAppCreate() throws Exception {
        AppEntry appEntry1 = new AppEntry(AppEntry1.class);
        AppEntryWrapper appEntryWrapper = new AppEntryWrapper(appEntry1);
        appEntryWrapper.onAppCreate(null);
        assertThat(AppEntry1.appCreateCount).isNotEqualTo(0);


    }

    public static class AppEntry1 {
        public static int appCreateCount = 0;

        @OnAppCreate
        public static void onAppCreate(Application application) {
            appCreateCount++;
        }
    }

}